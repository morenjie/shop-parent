package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("file")
public class UploadController {
    @Autowired
    FastFileStorageClient storageClient;
    //定义上传图片类型 jgp gif png
    public static final List<String> CONTENT_TYPE = Arrays.asList("image/jpeg", "image/git", "image/png");

    @RequestMapping("upload")
    public Result upload(MultipartFile file) throws Exception {
        //获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPE.contains(contentType)) {
            return Result.error("文件后缀不合法");
        }
        //读取文件内容
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage == null) {
            return Result.error("文件内容不合法");
        }
        //文件内容合法，进行文件上传
        String ext = StringUtils.substringAfterLast(originalFilename, ".");
        StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
        //回显图片缩略图
        return Result.ok("http://image.qf.com/" + storePath.getFullPath());
    }
}
