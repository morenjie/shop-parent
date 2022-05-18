package com.qf.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "cart-service")
public interface CartFeign {
}
