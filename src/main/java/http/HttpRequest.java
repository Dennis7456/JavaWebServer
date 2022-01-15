package http;

public class HttpRequest extends HttpMessage{

    /**
     *
     * Request line
     *
     */
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    HttpRequest() {
    }
        public HttpMethod getMethod() {
            return method;

    }

     void setMethod(HttpMethod method) {
        this.method = method;
    }
}
