/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.mainwindow;

import com.google.gson.Gson;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.JSONException;
import org.json.XML;
import tworpus.client.Tweet;
import tworpus.client.corpus.CreateCorpusController;
import tworpus.client.session.OpenSessionController;
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
    private URL createCorpusFXML;
    private URL openSessionFXML;
    
    
    @FXML
    private BorderPane borderPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createCorpusFXML = CreateCorpusController.class.getResource("CreateCorpus.fxml");
        visualizationsFXML = VisualizationsController.class.getResource("Visualizations.fxml");
        openSessionFXML = OpenSessionController.class.getResource("OpenSession.fxml");
        
        showOpenSession();
    }   
    
    @FXML
    public void showOpenSession() {
        try {
            FXMLLoader loader = new FXMLLoader(openSessionFXML);
            Pane pane = (Pane)loader.load();
            maincontent = pane;
            borderPane.centerProperty().set(maincontent);
            
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showCorpusForm() {
        try {
            FXMLLoader loader = new FXMLLoader(createCorpusFXML);
            Pane pane = (Pane)loader.load();
            
            // @TODO: may be deleted. Just for demonstation how to get controller
            CreateCorpusController controller = loader.getController();
            
            maincontent = pane;
            borderPane.centerProperty().set(maincontent);
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void showVisualizations() {
        System.out.println("show visualizations");
        //initWebView();
        //test();
        
        try {    
            Pane pane = FXMLLoader.load(visualizationsFXML);
            maincontent = pane;
            borderPane.centerProperty().set(maincontent);
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
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
