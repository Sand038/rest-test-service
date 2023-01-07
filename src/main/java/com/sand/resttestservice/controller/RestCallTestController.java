package com.sand.resttestservice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.sand.resttestservice.model.OrderOperationResponse;
import com.sand.resttestservice.model.TestRequest;
import com.sand.resttestservice.model.TestResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-call-test")
public class RestCallTestController {

  private final RetryTemplate retryTemplate;

  private final RestTemplate restTemplate;

  @PostMapping("/test")
  public ResponseEntity<TestResponse> testRequest(@RequestBody TestRequest testRequest) {

    OrderOperationResponse response = acceptOrder(testRequest);
    return ResponseEntity.ok(new TestResponse(response.getOrderId(), testRequest.getRequester(), response.getStatus().name()));
  }

  private OrderOperationResponse acceptOrder(TestRequest testRequest) {

    UriComponents uriComponents =
        UriComponentsBuilder.fromUriString("http://localhost:8080")
            .path("/v1.0/orders/{orderId}/accept")
            .buildAndExpand(testRequest.getId());

    final ResponseEntity<OrderOperationResponse> response =
        retryTemplate.execute(
            arg ->
                restTemplate.exchange(
                    uriComponents.toUri(),
                    HttpMethod.POST,
                    new HttpEntity<>(testRequest),
                    OrderOperationResponse.class));


    return response.getBody();
  }


}
