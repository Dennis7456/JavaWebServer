package http;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; //32 Space
    private static final int CR = 0x0D; //13 Carriage return
    private static final int LF = 0x0A; //10 Line feed

    public HttpRequest parseHttpRequest(InputStream inputStream) throws IOException {
        //Input stream reader to bridge from byte stream to char stream
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();

        /**
         *
         * Three methods to parse the http request
         *
         */
        //Start line
        parseRequestLine(reader, request);
        //Header
        parseHeaders(reader,request);
        //Message body
        parserBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException {
        int _byte;
        while ((_byte = reader.read()) >= 0){
            if(_byte == CR){
                _byte = reader.read();
                if(_byte == LF){
                    return;
                }
            }
        }
    }
    private void parseHeaders(InputStreamReader reader, HttpRequest request) {
    }
    private void parserBody(InputStreamReader reader, HttpRequest request) {
    }
}
