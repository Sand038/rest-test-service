package com.sand.resttestservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sand.resttestservice.model.Test;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-call-test")
public class RestCallTestController {

  @PostMapping("/test")
  public ResponseEntity<Test> cleanOrderFromCache() {
    return ResponseEntity.ok(new Test("123", "name1", "ACTIVE"));
  }


}
