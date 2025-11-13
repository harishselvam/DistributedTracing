package com.distributed.tracing.DistributedTracing.controller;

import com.distributed.tracing.DistributedTracing.service.CoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/otelpoc")
@Slf4j
@RequiredArgsConstructor
public class CoreController {

    private final CoreService service;

    @GetMapping(path = "/getName")
    public ResponseEntity<String> getName() {
        log.info("Request from service A reached here, in controller method");
        String response = service.returnName();
        log.info("Response is returned from service layer : {}", response);
        return ResponseEntity.ok(response);
    }
}
