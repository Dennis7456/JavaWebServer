package httpserver;

import httpserver.config.Configuration;
import httpserver.config.ConfigurationManager;

/**
 *
 * Http Server Driver Class
 *
 */
public class HttpServer {

    public static void main(String[] args){
        System.out.println("Starting server...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using Port: " + configuration.getPort());
        System.out.println("Using Webroot: " + configuration.getWebroot());
    }
}
