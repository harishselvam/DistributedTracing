package com.distributed.tracing.DistributedTracing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoreService {

    private final WebClient consumerClient;

    public String returnName() {
        log.info("Request reached to core service layer");
        return consumerClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response1 -> Mono.error(new Throwable()))
                .bodyToMono(String.class)

                .block();

    }
}
