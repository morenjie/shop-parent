package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.qf.mapper.SearchItemMapper;
import com.qf.pojo.SearchItem;
import com.qf.service.SearchItemService;
import com.qf.utils.JsonUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@SuppressWarnings("all")
public class SearchServiceImpl implements SearchItemService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    SearchItemMapper searchItemMapper;
    @Value("${ES_INDEX_NAME}")
    String ES_INDEX_NAME;
    @Value("${ES_TYPE_NAME}")
    String ES_TYPE_NAME;

    @Override
    public boolean importAll() throws Exception {
        try {
            //首先需要判断索引库是否存在？
            if (!indexIsExist()) {
                //如果索引库不存在，就要先创建索引库
                createIndex();
            }
            //默认导入第一页的数据
            int page = 1;
            while (true) {
                //如果索引库已经创建了，就需要将数据库查询出来的商品数据导入到索引库中
                PageHelper.startPage(page, 1000);
                //将数据库中的商品查询出来(第page页的数据)
                List<SearchItem> searchItemList = searchItemMapper.getSearchItem();
                //如果page页里面没有数据了，就要跳出循环
                if (searchItemList == null || searchItemList.size() == 0) {
                    break;
                }
                //说明第page页还有数据，就执行导入索引库的操作
                BulkRequest bulkRequest = new BulkRequest();
                for (int i = 0; i < searchItemList.size(); i++) {
                    IndexRequest indexRequest = new IndexRequest(ES_INDEX_NAME, ES_TYPE_NAME);
                    indexRequest.source(JsonUtils.objectToJson(searchItemList.get(i)), XContentType.JSON);
                    bulkRequest.add(indexRequest);
                }
                //执行批量导入
                restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                //执行导入之后
                page++;
            }
            //如果索引库已经创建了，就需要将数据库查询出来的商品导入到索引库中
            return true;
        } catch (Exception e) {
            //导入异常
            return false;
        }
    }

    @Override
    public List<SearchItem> list(String q, Integer page, Integer pageSize) throws Exception {
        //构建搜索对象
        SearchRequest searchRequest = new SearchRequest(ES_INDEX_NAME);
        searchRequest.types(ES_TYPE_NAME);
        //构建搜索源对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置多field查询
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(q, new String[]{"item_title", "item_desc", "item_sell_point", "item_category_name"}));
        //设置分页查询
        Integer from = (page - 1) * pageSize;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(pageSize);
        //设置高亮查询
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color = 'red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("item_title");
        searchSourceBuilder.highlighter(highlightBuilder);
        //将搜索源对象设置request里面去
        searchRequest.source(searchSourceBuilder);
        //执行查询操作
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析searchResponse对象
        SearchHit[] hits = searchResponse.getHits().getHits();
        //定义返回的数据结果
        List<SearchItem> searchItemList = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            SearchHit hit = hits[i];
            String sourceAsString = hit.getSourceAsString();
            SearchItem searchItem = JsonUtils.jsonToPojo(sourceAsString, SearchItem.class);
            //将高亮部分查询出来
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields != null) {
                HighlightField highlightField = highlightFields.get("item_title");
                Text[] fragments = highlightField.getFragments();
                searchItem.setItem_title(fragments.toString());
            }
            searchItemList.add(searchItem);
        }
        return searchItemList;
    }

    //判断索引库是否存在的方法
    public boolean indexIsExist() throws Exception {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(ES_INDEX_NAME);
        //判断指定的索引库是否存在
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    //创建索引库的方法
    public boolean createIndex() throws Exception {
        //创建索引请求对象，并设置索引名称
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(ES_INDEX_NAME);
        //设置索引参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards", 2)
                .put("number_of_replicas", 1));
        createIndexRequest.mapping(ES_TYPE_NAME, "{\n" +
                "  \"_source\": {\n" +
                "    \"excludes\": [\n" +
                "      \"item_desc\"\n" +
                "    ]\n" +
                "  },\n" +
                "  \"properties\": {\n" +
                "    \"item_title\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"analyzer\": \"ik_max_word\",\n" +
                "      \"search_analyzer\": \"ik_smart\"\n" +
                "    },\n" +
                "    \"item_sell_point\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"analyzer\": \"ik_max_word\",\n" +
                "      \"search_analyzer\": \"ik_smart\"\n" +
                "    },\n" +
                "    \"item_price\": {\n" +
                "      \"type\": \"float\"\n" +
                "    },\n" +
                "    \"item_image\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"index\": false\n" +
                "    },\n" +
                "    \"item_category_name\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"item_desc\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"analyzer\": \"ik_max_word\",\n" +
                "      \"search_analyzer\": \"ik_smart\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        //创建索引操作客户端
        IndicesClient indices = restHighLevelClient.indices();

        //创建响应对象
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
        //得到响应结果
        return createIndexResponse.isAcknowledged();
    }
}
