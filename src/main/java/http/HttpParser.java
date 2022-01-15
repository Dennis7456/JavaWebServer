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

    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        //Input stream reader to bridge from byte stream to char stream
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();

        /**
         *
         * Three methods to parse the http request
         *
         */
        //Start line
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Header
        parseHeaders(reader,request);
        //Message body
        parserBody(reader, request);

        return request;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while ((_byte = reader.read()) >= 0){
            if(_byte == CR){
                _byte = reader.read();
                if(_byte == LF){
                    //At the end of the request line we return
                    LOGGER.debug("Request Line HTTP version to Process : {}" , processingDataBuffer.toString());
                    //Important to have return statement
                    if(!methodParsed || !requestTargetParsed){
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                }

            }
            if(_byte == SP){
                //check for space character and skip spaces
                if(!methodParsed){
                    LOGGER.debug("Request Line METHOD to Process : {}" , processingDataBuffer);
                    methodParsed = true;
                    request.setMethod(processingDataBuffer.toString());
                    //Important to use else if here
                } else if(!requestTargetParsed) {
                    LOGGER.debug("Request Line Request Target to Process : {}" , processingDataBuffer);
                    requestTargetParsed =true;
                    request.setRequestTarget(processingDataBuffer.toString());
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else {
                //if no space character build string
                processingDataBuffer.append((char) _byte);
                if(!methodParsed){
                    if(processingDataBuffer.length() > HttpMethod.MAX_LENGTH){
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }
    private void parseHeaders(InputStreamReader reader, HttpRequest request) {
    }
    private void parserBody(InputStreamReader reader, HttpRequest request) {
    }
}
