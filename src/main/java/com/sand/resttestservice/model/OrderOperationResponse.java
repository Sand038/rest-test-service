package com.sand.resttestservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderOperationResponse {
  private String orderId;
  private OperationStatus status;
}
