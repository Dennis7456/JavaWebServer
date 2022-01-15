package http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {
    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass(){
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpGetRequest() throws IOException {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateValidGET());
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertEquals(request.getMethod(), HttpMethod.GET);
    }

    @Test
    void parseHttpGetRequestBadMethod() throws IOException {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidGET());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }

    }

    @Test
    void parseHttpGetRequestMethodTooLong() throws IOException {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidMethodLength());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }

    }

    @Test
    void parseHttpGetRequestRequestLineInNumOfItems() throws IOException {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateInvalidRequestLineLength());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    }
    @Test
    void parseHttpRequestLineEmpty() throws IOException {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateEmptyRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    }

    @Test
    void parseHttpRequestLineNoLineFeed() throws IOException {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateRequestLineCarriageReturnNoLineFeed());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

    }

    private InputStream generateValidGET(){
        String rawData = "GET / HTTP/1.1\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

    private InputStream generateInvalidGET(){
        String rawData = "GeT / HTTP/1.1\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

    private InputStream generateInvalidMethodLength(){
        String rawData = "GEEEEET / HTTP/1.1\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

    private InputStream generateInvalidRequestLineLength(){
        String rawData = "GET / AAAA HTTP/1.1\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

    private InputStream generateEmptyRequestLine(){
        String rawData = "\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

    private InputStream generateRequestLineCarriageReturnNoLineFeed(){
        String rawData = "GET / HTTP/1.1 \r"; //<---- no LF

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

}