package com.lowlevel.design.lld.streamingapp.util;

import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.core.io.AbstractResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class ByteRangeResource extends AbstractResource {
    private final File file;
    private final long start;
    private final long end;

    public ByteRangeResource(File file, long start, long end) {
        this.file = file;
        this.start = start;
        this.end = end;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(start);
        return new LimitedInputStream(new FileInputStream(raf.getFD()), end - start + 1) {
            @Override
            protected void raiseError(long l, long l1) throws IOException {
                System.out.println("Error reading byte range: " + l + " to " + l1);
            }
        };
    }

    @Override
    public String getDescription() {
        return "ByteRangeResource: " + file.getName() + " from " + start + " to " + end;
    }

    @Override
    public long contentLength() {
        return end - start + 1;
    }
}