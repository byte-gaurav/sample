package com.lowlevel.design.lld.streamingapp.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream extends FilterInputStream {
    private long left;

    public LimitedInputStream(InputStream in, long limit) {
        super(in);
        this.left = limit;
    }

    @Override
    public int read() throws IOException {
        if (left-- <= 0) return -1;
        return super.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (left <= 0) return -1;
        len = (int) Math.min(len, left);
        int read = super.read(b, off, len);
        if (read != -1) left -= read;
        return read;
    }
}