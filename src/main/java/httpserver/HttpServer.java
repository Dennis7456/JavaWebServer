package http;

import http.HttpRequest;
import httpserver.config.Configuration;
import httpserver.config.ConfigurationManager;
import httpserver.core.ServerListenerThread;
import httpserver.httpresponse.WebRootHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Http Server Driver Class
 *
 */
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args){
        LOGGER.info("Starting server...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
        WebRootHandler.getInstance().loadHtmlFiles(configuration.getWebroot());

        LOGGER.info("Using Port: " + configuration.getPort());
        LOGGER.info("Using Webroot: " + configuration.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
