package http;

/**
 *
 * Exception class for all http errors mapped from status codes
 *
 */
public class HttpParsingException extends Exception{

    private HttpStatusCode errorCode;

    public HttpParsingException(HttpStatusCode errorCode) {
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }

    public HttpStatusCode getErrorCode() {
        return errorCode;
    }
}
