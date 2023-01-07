package com.sand.resttestservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sand.resttestservice.model.OperationStatus;
import com.sand.resttestservice.model.OrderOperationRequest;
import com.sand.resttestservice.model.OrderOperationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/orders")
public class OrdersController {

  @PostMapping("/{orderId}/accept")
  public ResponseEntity<OrderOperationResponse> acceptOrder(@PathVariable String orderId, @RequestBody
      OrderOperationRequest request) {
    return ResponseEntity.ok(new OrderOperationResponse(orderId, OperationStatus.SUCCESS));
  }

  @PostMapping("/{orderId}/reject")
  public ResponseEntity<OrderOperationResponse> rejectOrder(@PathVariable String orderId, @RequestBody
  OrderOperationRequest request) {
    return ResponseEntity.ok(new OrderOperationResponse(orderId, OperationStatus.SUCCESS));
  }


}
