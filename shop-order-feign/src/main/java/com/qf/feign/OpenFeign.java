package com.qf.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("order-service")
public interface OpenFeign {

}
