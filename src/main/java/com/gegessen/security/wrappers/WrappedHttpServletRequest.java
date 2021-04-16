package com.gegessen.security.wrappers;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class WrappedHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] bytes;

    public WrappedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        // Read the request parameters in the input stream and save them in bytes
        bytes = IOUtils.toByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new BufferedServletInputStream(this.bytes);
    }

    static class BufferedServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream inputStream;
        public BufferedServletInputStream(byte[] buffer) {
            //This is enabling, you can see the constructor of ByteArray InputStream in detail;
            this.inputStream = new ByteArrayInputStream( buffer );
        }
        @Override
        public int available() throws IOException {
            return inputStream.available();
        }
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return inputStream.read( b, off, len );
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

}