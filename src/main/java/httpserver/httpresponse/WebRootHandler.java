package httpserver.httpresponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import sun.nio.cs.US_ASCII;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * Singleton WebRootHandler class to get content from web root
 *
 */
public class WebRootHandler {

    private static WebRootHandler myWebRootHandler;
    private static String indexHtmlContent;
    private static String aboutHtmlContent;
    private static String formHtmlContent;
    private static String notFileNotFoundHtmlContent;

    static void setIndexHtmlContent(String indexHtmlContent) {
        WebRootHandler.indexHtmlContent = indexHtmlContent;
    }

    public static void setAboutHtmlContent(String aboutHtmlContent) {
        WebRootHandler.aboutHtmlContent = aboutHtmlContent;
    }

    public static void setFormHtmlContent(String formHtmlContent) {
        WebRootHandler.formHtmlContent = formHtmlContent;
    }

    public static void setNotFileNotFoundHtmlContent(String notFileNotFoundHtmlContent) {
        WebRootHandler.notFileNotFoundHtmlContent = notFileNotFoundHtmlContent;
    }

    public static String getIndexHtmlContent() {
        return indexHtmlContent;
    }

    public static String getFormHtmlContent() {
        return formHtmlContent;
    }

    public static String getNotFileNotFoundHtmlContent() {
        return notFileNotFoundHtmlContent;
    }

    private WebRootHandler(){

    }

    /**
     *
     * Check if there exists an instance of WebRoot handler and create an instance in non exists
     *
     */

    public static WebRootHandler getInstance(){
        if(myWebRootHandler == null){
            myWebRootHandler = new WebRootHandler();
        }
        return myWebRootHandler;
    }

    public void loadHtmlFiles(String filePath){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath + "/index.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                System.out.print((char) i);
                stringBuffer.append((char) i);
            }
            System.out.print(stringBuffer.toString());
           // setIndexHtmlContent(stringBuffer.toString());
            setIndexHtmlContent("Index Page");
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
