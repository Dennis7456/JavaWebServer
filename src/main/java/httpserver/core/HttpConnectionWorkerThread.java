package httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            //Output stream, for writing
            outputStream = socket.getOutputStream();

            //Read the connection coming from the client (browser)
            int _byte;
            while ((_byte = inputStream.read()) >= 0){
                System.out.print((char) _byte);
            }

            String html = "<html><head><title>JavaWebServer</title></head><body><h1>JavaWebServer</h1></body></html>";

            final String CRLF = "\n\r";

            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length:" + html.getBytes().length + CRLF +//Header
                            CRLF +
                            html +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());

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
