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
    void parseHttpRequest() throws IOException {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateValidGET());
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertNotNull(request);
        assertEquals(request.getRequestTarget(), "/");
        assertEquals(request.getOriginalHttpVersion(), "HTTP/1.1");
        assertEquals(request.getBestCompatibleHttpVersion(), HttpVersion.HTTP_1_1);
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

    @Test
    void parseHttpVersionBadHttpVersion(){
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadHttpVersionRequest());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }

    @Test
    void parseHttpUnsupportedVersion(){
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateUnsupportedHttpVersion());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }

    @Test
    void parseSupportedHttpVersion(){
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateSupportedHttpVersion());
           assertNotNull(request);
           assertEquals(request.getBestCompatibleHttpVersion(), HttpVersion.HTTP_1_1);
           assertEquals(request.getOriginalHttpVersion(), "HTTP/1.2");
        } catch (HttpParsingException e) {
            fail();
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

    private InputStream generateBadHttpVersionRequest(){
        String rawData = "GET / HTP/1.1\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

    private InputStream generateUnsupportedHttpVersion(){
        String rawData = "GET / HTP/2.1\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

    private InputStream generateSupportedHttpVersion(){
        String rawData = "GET / HTTP/1.2\r\n" +
                "Host: localhost:9000\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
        return inputStream;
    }

}