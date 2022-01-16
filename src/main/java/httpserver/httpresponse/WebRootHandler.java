package httpserver.httpresponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebRootHandler {
    private static WebRootHandler myWebRootHandler;
    private static String indexPageContent;
    private static String formPageContent;
    private static String fileNotFound;

    private  WebRootHandler(){

    }

    /**
     *
     * Check if there exists an instance of WebRoot handler and create an instance if non exists
     *
     */

    public static WebRootHandler getInstance(){
        if(myWebRootHandler == null){
            myWebRootHandler = new WebRootHandler();
        }
        return myWebRootHandler;
    }

    public static String getIndexPageContent() {
        return indexPageContent;
    }

    public static String getFormPageContent() {
        return formPageContent;
    }

    public static String getFileNotFound() {
        return fileNotFound;
    }

    public static void setIndexPageContent(String indexPageContent) {
        WebRootHandler.indexPageContent = indexPageContent;
    }

    public static void setFormPageContent(String formPageContent) {
        WebRootHandler.formPageContent = formPageContent;
    }

    public static void setFileNotFound(String fileNotFound) {
        WebRootHandler.fileNotFound = fileNotFound;
    }

    public void loadIndexFile(String filePath){
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
                stringBuffer.append((char) i);
                //System.out.print((char) i);
            }
            setIndexPageContent(stringBuffer.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadFormFile(String filePath){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath + "/form.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                stringBuffer.append((char) i);
                //System.out.print((char) i);
            }
            setFormPageContent(stringBuffer.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadFileNotFound(String filePath){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath + "/404.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                stringBuffer.append((char) i);
                //System.out.print((char) i);
            }
            setFileNotFound(stringBuffer.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
