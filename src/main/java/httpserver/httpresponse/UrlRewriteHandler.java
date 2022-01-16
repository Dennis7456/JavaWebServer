package httpserver.httpresponse;


import http.HttpParser;

public class UrlRewriteHandler {
    private static UrlRewriteHandler urlRewriteHandler;
    private static String user;
    private static String password;

    private UrlRewriteHandler(){

    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setUser(String user) {
        UrlRewriteHandler.user = user;
    }

    public static void setPassword(String password) {
        UrlRewriteHandler.password = password;
    }

    public static UrlRewriteHandler getInstance() {
        if(urlRewriteHandler == null){
            urlRewriteHandler = new UrlRewriteHandler();
        }
        return urlRewriteHandler;
    }

    public static void myFunction(){
        HttpParser httpRequest = new HttpParser();
    }

}

