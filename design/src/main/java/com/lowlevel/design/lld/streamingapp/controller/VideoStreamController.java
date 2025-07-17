package com.lowlevel.design.lld.streamingapp.controller;




import com.lowlevel.design.lld.streamingapp.service.VideoStreamService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;


@RestController
@RequestMapping("/video")
public class VideoStreamController {

    private final VideoStreamService videoStreamService;


    public VideoStreamController(VideoStreamService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> stream(@PathVariable String fileName,
                                           @RequestHeader HttpHeaders headers) {
        return videoStreamService.prepareContent(fileName, headers);
    }

    @GetMapping(value = "/flux/stream/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Flux<DataBuffer> streamVideo(@PathVariable String fileName) {
        return videoStreamService.streamFile(fileName);
    }

    @GetMapping(value="/emit/stream/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<ResponseBodyEmitter> streamWithEmitter(@PathVariable String fileName) {
        File videoFile = new File("/your/video/path", fileName);
        if (!videoFile.exists()) return ResponseEntity.notFound().build();

        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        Executors.newSingleThreadExecutor().submit(() -> {
            try (InputStream inputStream = new FileInputStream(videoFile)) {
                byte[] buffer = new byte[64 * 1024];
                while (inputStream.read(buffer) != -1) {
                    emitter.send(buffer);
                }
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });

        return ResponseEntity.ok()
                .header("Content-Type", "video/mp4")
                .body(emitter);
    }
}