package httpserver.core;

import http.HttpParser;
import http.HttpParsingException;
import http.HttpRequest;
import httpserver.httpresponse.WebRootHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpConnectionWorkerThread extends Thread{

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    private Socket socket;
    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //Input stream for reading
            inputStream = socket.getInputStream();
            HttpRequest httpRequest = null;
            try {
                httpRequest = new HttpParser().parseHttpRequest(inputStream);
                LOGGER.info("The request target is:" + httpRequest.getRequestTarget());
            } catch (HttpParsingException e) {
                e.printStackTrace();
            }


            //Output stream, for writing
            //TODO use to read server responses
            outputStream = socket.getOutputStream();

            //Logic to write bytes to output stream
            if(httpRequest.getRequestTarget() == "/"){
                //TODO return index page
                //html = WebRootHandler.getIndexHtmlContent();
                //System.out.print(html);
               // html = "Text";
            } else if(httpRequest.getRequestTarget() == "/form"){
                //TODO return form
                html = WebRootHandler.getFormHtmlContent();
            } else {
                //TODO return 404 not found page
                html = WebRootHandler.getNotFileNotFoundHtmlContent();
            }

            String html = "Text"
            final String CRLF = "\n\r";

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length:" + html.getBytes().length + CRLF + "Content-Type:" + "text/html"+ CRLF +//Header
                            CRLF +
                            html +
                            CRLF + CRLF;
//            outputStream.write(response.getBytes());
            outputStream.write(response.getBytes(StandardCharsets.US_ASCII));

            LOGGER.info("Connection processing finished");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
