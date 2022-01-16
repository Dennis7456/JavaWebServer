package httpserver.core;

import http.HttpParser;
import http.HttpParsingException;
import http.HttpRequest;
import httpserver.httpresponse.WebRootHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String html ="Default";
        String user = "";
        String password = "";

        try {
            //Input stream for reading
            inputStream = socket.getInputStream();

            //Output stream, for writing
            outputStream = socket.getOutputStream();

            HttpRequest httpRequest = null;

            //Calling the HTTP parser on input stream to parse request
            try {
                httpRequest = new HttpParser().parseHttpRequest(inputStream);

            } catch (HttpParsingException e) {
                e.printStackTrace();
            }
            //LOGGER.info("Out" + httpRequest.getRequestTarget());

            //Logic to write bytes to output stream
            if(httpRequest.getRequestTarget().equals("/")){
               // html = "Home Page";
                html = WebRootHandler.getIndexPageContent();
            } else if(httpRequest.getRequestTarget().equals("/form")){
                //html = "Form Page";
                html = WebRootHandler.getFormPageContent();
            } else if (httpRequest.getRequestTarget().equals("/favicon.ico")){
                html = "Dennis's Smiley Favicon Goes Here ;)";
            } else if(httpRequest.getRequestTarget().contains("user")){

                //Process user data for URL rewrite
                String temp = httpRequest.getRequestTarget();
                String string1 = temp.substring(6, temp.length());
                String[] stringarr1 = string1.split("&", 2);
                String string2 = stringarr1[0];
                String string3 = stringarr1[1];


                String[] stringarr2 = string2.split("=", 2);
                user = stringarr2[1];
               // System.out.print("USER: " + user);

                String[] stringarr3 = string3.split("=", 2);
                password = stringarr3[1];
               // System.out.print("PASSWORD: " + password);

                html = "<html><head><title>User name and password</title></head><body><h1>" + "Hello: "+ user + " Your Password Is: " + password + ";)" + "<a href=\"/\">Home</a>" + "</h1></body></html>";
            }
            else {
                //html = "404 not found";
                html = WebRootHandler.getFileNotFound();
            }

            final String CRLF = "\r\n"; // 13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line  :   HTTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + "Content-Type: " + "text/html" + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());



            LOGGER.info(" * Connection Processing Finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if (inputStream!= null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if (socket!= null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
