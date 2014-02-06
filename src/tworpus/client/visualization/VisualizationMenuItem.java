/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.visualization;

import javafx.scene.control.MenuItem;
import tworpus.client.visualization.configuration.VisualizationConfig;

/**
 *
 * @author Markus
 */
public class VisualizationMenuItem extends MenuItem {
    
    private VisualizationConfig config;
    
    public VisualizationMenuItem(VisualizationConfig cfg) {
        config = cfg;
        setText(cfg.getTitle());
    }
    
    public VisualizationConfig getConfig() {
        return config;
    }
}
