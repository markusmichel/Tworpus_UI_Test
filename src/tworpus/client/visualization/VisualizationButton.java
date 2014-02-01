/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.visualization;

import tworpus.client.visualization.configuration.VisualizationConfig;
import javafx.scene.control.Button;

/**
 *
 * @author Markus
 */
public class VisualizationButton extends Button {
    
    private VisualizationConfig config;
    
    public VisualizationButton(VisualizationConfig cfg) {
        config = cfg;
        setText(cfg.getTitle());
    }
    
    public VisualizationConfig getConfig() {
        return config;
    }
    
}
