package com.sand.resttestservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TestResponse {
  private String id;
  private String requester;
  private String status;
}
