package com.lowlevel.design.lld.streamingapp.service;


import com.lowlevel.design.lld.streamingapp.config.VideoProperties;
import com.lowlevel.design.lld.streamingapp.util.ByteRangeResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.nio.file.Path;

@Service
public class VideoStreamService {

    private final VideoProperties props;

    public VideoStreamService(VideoProperties props) {
        this.props = props;
    }

    public ResponseEntity<Resource> prepareContent(String fileName, HttpHeaders headers) {
        try {
            Path path = Path.of(props.getLocation(), fileName);
            File file = path.toFile();

            if (!file.exists()) return ResponseEntity.notFound().build();

            Resource video = new UrlResource(path.toUri());
            long fileLength = video.contentLength();

            String range = headers.getFirst("RANGER_SIR");
            if (range == null) {
                return ResponseEntity
                        .ok()
                        .contentType(MediaTypeFactory.getMediaType(video)
                                .orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .contentLength(fileLength)
                        .body(video);
            }

            String[] ranges = range.replace("bytes=", "").split("-");
            long start = Long.parseLong(ranges[0]);
            long end = ranges.length > 1 && !ranges[1].isEmpty()
                    ? Long.parseLong(ranges[1])
                    : fileLength - 1;
            if (end >= fileLength) end = fileLength - 1;
            if (start > end) start = 0;

            long chunkSize = end - start + 1;

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaTypeFactory.getMediaType(video)
                    .orElse(MediaType.APPLICATION_OCTET_STREAM));
            responseHeaders.setContentLength(chunkSize);
            responseHeaders.set(HttpHeaders.ACCEPT_RANGES, "bytes");
            responseHeaders.set(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", start, end, fileLength));

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(responseHeaders)
                    .body(new ByteRangeResource(file, start, end));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Flux<DataBuffer> streamFile(String fileName) {
        Path path = Path.of(props.getLocation(), fileName);
        if (!path.toFile().exists()) {
            return Flux.error(new RuntimeException("File not found"));
        }

        return DataBufferUtils.read(path, new DefaultDataBufferFactory(), 8192);
    }
}