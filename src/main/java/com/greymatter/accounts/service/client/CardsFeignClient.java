package com.greymatter.accounts.service.client;

import com.greymatter.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {
    @GetMapping(value = "/api/fetch",consumes = "application/json")
    ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("greymatter-correlation-id")
                                              String correlationId, @RequestParam String mobileNumber);
}
