/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tworpus.client.visualization;

import com.thoughtworks.xstream.XStream;
import tworpus.client.visualization.configuration.VisualizationConfig;
import tworpus.client.visualization.configuration.PackageConfig;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tweet.Message;
import tweet.Tweet;
import tweet.TweetContainer;
import tweet.User;
import tworpus.client.xml.TweetXmlConverter;

/**
 * FXML Controller class
 *
 * @author Markus
 */
public class VisualizationsController implements Initializable {

    private FileFilter directoryFilter;
    private FileFilter packageConfigFilter;

    private EventHandler<ActionEvent> onButtonClickListener = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            VisualizationButton button = (VisualizationButton) t.getSource();
            showVisualization(button.getConfig());
        }
    };

    private EventHandler<ActionEvent> onVisualizationMenuItemClickListener = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            VisualizationMenuItem item = (VisualizationMenuItem) t.getSource();
            showVisualization(item.getConfig());
        }
    };

    @FXML
    private VBox pane;
    @FXML
    private Menu menu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createFilters();
        showVisualizationList();

        PackageConfig[] packageConfigs = getPackageConfigObjects();
        for (PackageConfig config : packageConfigs) {
            for (VisualizationConfig cfg : config.getVisualizations()) {
                MenuItem item = new VisualizationMenuItem(cfg);
                menu.getItems().add(item);
                item.setOnAction(onVisualizationMenuItemClickListener);
            }
        }
    }

    /**
     * Shows the list of available visualizations on the pane. Visualizations
     * are represented as buttons. Every button implements an onAction event.
     */
    private void showVisualizationList() {
        pane.getChildren().clear();

        PackageConfig[] packageConfigs = getPackageConfigObjects();

        for (PackageConfig packageConfig : packageConfigs) {
            for (VisualizationConfig vcfg : packageConfig.getVisualizations()) {
                Button button = new VisualizationButton(vcfg);
                pane.getChildren().add(button);

                button.setOnAction(onButtonClickListener);
            }
        }
    }

    /**
     * Displays a single visualization on the main panel. The visualization is
     * based on a VisualizationConfig object, which is usually retrieved by a
     * VisualizationButton or VisualizationMenuItem.
     * VisualizationConfig is usually generated from a config.xml file
     * in a visualization package folder.
     *
     * @param config Visualization to display
     */
    private void showVisualization(VisualizationConfig config) {
        pane.getChildren().clear();
        File htmlFile = config.getHtmlFile();
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        
        // TESTCODE
        String tweets = "";
        String tweetsXmlFileName = "tweets/test/tweets.xml";
        File tweetsXmlFile = new File(tweetsXmlFileName);
        
        
        User user  = new User();
        user.setFullName("Vorname Nachname");
        user.setScreenName("screenname");
        user.setUserId("sdfe6486");
        
        Message message = new Message();
        message.setChars(10);
        message.setLanguage("de");
        message.setText("TEXT 1234");
        message.setWords(20);
        
        Tweet tweet1 = new Tweet();
        tweet1.setDate("DATUM 1");
        tweet1.setFavoured(10);
        tweet1.setId("13254");
        tweet1.setRetweets(23);
        tweet1.setUser(user);
        tweet1.setMessage(message);
        
        Tweet tweet2 = new Tweet();
        tweet2.setDate("DATUM 1");
        tweet2.setFavoured(10);
        tweet2.setId("13254");
        tweet2.setRetweets(23);
        tweet2.setMessage(message);
        tweet2.setUser(user);
        
        Tweet[] tweetsArr = new Tweet[]{tweet1, tweet2};
        String xml = TweetXmlConverter.tweetsToXml(tweetsArr);
        System.out.println(xml); 
        
        XStream stream = Tweet.getXStream();
        Object foo = stream.fromXML(
                "<tweets>" +
                "  <tweet id=\"13254\">" +
                "    <user id=\"sdfe6486\">" +
                "      <screenName>screenname</screenName>" +
                "      <fullName>Vorname Nachname</fullName>" +
                "    </user>" +
                "    <date>DATUM 1</date>" +
                "    <retweets>23</retweets>" +
                "    <favoured>10</favoured>" +
                "    <text chars=\"10\" words=\"20\" lang=\"de\">TEXT 1234</text>" +
                "  </tweet></tweets>");
        
        // END TESTCODE

        // @TODO: Get tweets
        // @TODO: Converter middleware
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            String testJson = "{\"test\":{\"content\":\"Turn this to JSON\",\"attrib\":\"moretest\"}}";
                            VisualizationBrowser.bootstrap(webEngine, testJson);
                        }
                    }
                });
        try {
            webEngine.load(htmlFile.toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
            Logger.getLogger(VisualizationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pane.getChildren().add(browser);
    }

    /**
     * Returns als package folders in visualization folder.
     */
    private File[] getPackages() {
        return new File("visualizations").listFiles(directoryFilter);
    }

    /**
     * Returns als config files in visualization folder.
     *
     * @return
     */
    private File[] getConfigFiles() {
        LinkedList<File> configFiles = new LinkedList<File>();
        File[] packages = getPackages();
        for (File file : packages) {
            File[] foundConfigFiles = file.listFiles(packageConfigFilter);
            if (foundConfigFiles.length > 0) {
                configFiles.add(foundConfigFiles[0]);
            }
        }
        return configFiles.toArray(new File[configFiles.size()]);
    }

    /**
     * Gets all package config files as PackageConfig objects. This function has
     * to read and filter files from the filesystem. It also reads XMLFiles and
     * converts them to Java Objects. Thus, it consumes CPU power on one side,
     * on the other side it always returns correct results in realtime.
     *
     * @return An array of all present PackageConfig objects
     */
    public PackageConfig[] getPackageConfigObjects() {
        File[] configFiles = getConfigFiles();
        PackageConfig[] configObjects = new PackageConfig[configFiles.length];
        int i = 0;
        for (File file : configFiles) {
            configObjects[i] = PackageConfig.createFromXml(configFiles[i++]);
        }

        return configObjects;
    }

    /**
     * Creates filter variables user for finding package folders and
     * configuration files.
     */
    private void createFilters() {
        directoryFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };

        packageConfigFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().equals("config.xml");
            }
        };
    }

}
