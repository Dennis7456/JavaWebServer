package http;

import httpserver.config.HttpConfigurationException;

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

     void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public HttpMethod getMethod() {
            return method;

    }

     void setMethod(String methodName) throws HttpParsingException {
        for(HttpMethod method : HttpMethod.values()){
            if(methodName.equals(method.name())){
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }
}
