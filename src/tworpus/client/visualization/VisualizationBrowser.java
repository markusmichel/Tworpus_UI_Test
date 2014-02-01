/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tworpus.client.visualization;

import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Markus
 */
public class VisualizationBrowser extends Region {
    
    final WebView webview = new WebView();
    final WebEngine webEngine = webview.getEngine();
    
    public static void appendScript(WebEngine engine, String scriptLocation) {
        engine.executeScript(
            "var script = document.createElement(\"script\");"
            + "script.type = \"text/javascript\";"
            + "script.async = false;"
            + "script.src = \"" + scriptLocation + "\";"

            + "document.documentElement.childNodes[0].appendChild(script);"
        );
    }
    
    public static void bootstrap(WebEngine engine, String tweets) {
        engine.executeScript(
            "var tweets = " + tweets + ";" +
            "run();"
        );
    }
}
