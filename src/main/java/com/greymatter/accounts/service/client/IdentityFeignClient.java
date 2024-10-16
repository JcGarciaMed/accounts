package com.greymatter.accounts.service.client;

import com.greymatter.accounts.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("identity")
public interface IdentityFeignClient {

    @GetMapping(value = "/reniec/v1/api/{doi}")
    ResponseEntity<PersonDto> findById(@PathVariable("doi") String doi);
}
