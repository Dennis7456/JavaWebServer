package httpserver.httpresponse;

public class HttpServerResponseException extends Exception{
    public HttpServerResponseException() {
    }

    public HttpServerResponseException(String message) {
        super(message);
    }

    public HttpServerResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpServerResponseException(Throwable cause) {
        super(cause);
    }
}
