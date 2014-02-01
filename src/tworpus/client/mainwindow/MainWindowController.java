/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.mainwindow;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONException;
import org.json.XML;
import tworpus.client.Tweet;
import tworpus.client.visualization.VisualizationsController;

/**
 *
 * @author Markus
 */
public class MainWindowController implements Initializable {
    
    @FXML
    private Pane maincontent;
    @FXML
    private Button buttonVisualization;
    @FXML
    private VBox sidebarLeft;
    
    
    private URL visualizationsFXML;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visualizationsFXML = VisualizationsController.class.getResource("Visualizations.fxml");
    }   
    
    @FXML
    public void showVisualizations() {
        System.out.println("show visualizations");
        //initWebView();
        //test();
        
        try {    
            Pane pane = FXMLLoader.load(visualizationsFXML);
            //maincontent.getChildren().add(pane);
            maincontent = pane;
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    private void initWebView() {
        URL file = getClass().getResource("test.html");
        WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();   
        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) {
                    System.out.println("execute script");
                    if (newState == Worker.State.SUCCEEDED) 
                        webEngine.executeScript("document.init({\"test\":{\"content\":\"Turn this to JSON\",\"attrib\":\"moretest\"}});");
                }
            });
        webEngine.load(file.toExternalForm());
        maincontent.getChildren().add(browser);
    }

    private void test() {
        int count = 10;
        Tweet[] tweets = new Tweet[count];
        
        for(int i = 0; i < count; i++) {
            Tweet tweet = new Tweet();
            tweet.setText("Text 123");
            tweet.setAuthor("autor");
            tweets[i] = tweet;
        }

        Gson gson = new Gson();
        String json = gson.toJson(tweets); 
        
        PrintWriter out;
        try {
            out = new PrintWriter("filename.txt");
            out.println(json);
            out.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        String xml = "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>";
        try {
            String o = XML.toJSONObject(xml).toString();
            System.out.println(o);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
