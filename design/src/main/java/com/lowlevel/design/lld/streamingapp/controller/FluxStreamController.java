package com.lowlevel.design.lld.streamingapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxStreamController {

    @GetMapping(value = "/stream/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(i -> "data: Flux message " + i);
    }
}