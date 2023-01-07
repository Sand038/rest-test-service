package com.sand.resttestservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TestRequest {
  private String id;
  private String requester;
}
