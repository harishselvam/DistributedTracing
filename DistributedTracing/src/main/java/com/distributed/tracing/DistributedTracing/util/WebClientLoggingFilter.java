package com.distributed.tracing.DistributedTracing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;


public class WebClientLoggingFilter {

    private static final Logger log = LoggerFactory.getLogger(WebClientLoggingFilter.class);

    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            log.info("WebClient Request: {} {}", request.method(), request.url());
            request.headers().forEach((name, values) ->
                    values.forEach(value -> log.debug("Request Header: {}={}", name, value))
            );
            return Mono.just(request);
        });
    }

    public static ExchangeFilterFunction logResponseBody() {
        return ExchangeFilterFunction.ofResponseProcessor(response ->
                response.bodyToMono(String.class)
                        .defaultIfEmpty("")
                        .flatMap(body -> {
                            log.debug("WebClient Response Body: {}", body);
                            // Recreate new ClientResponse since body was consumed
                            return Mono.just(ClientResponse.create(response.statusCode())
                                    .headers(headers -> headers.addAll(response.headers().asHttpHeaders()))
                                    .body(body)
                                    .build());
                        })
        );
    }
}
