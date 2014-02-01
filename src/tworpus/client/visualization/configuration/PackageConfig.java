/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.visualization.configuration;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a single package configuration.
 */
public class PackageConfig {
    
    // @TODO: Check if html file really exists / config is valid and remove invalid entries
    public static PackageConfig createFromXml(File configFile) {
        
        XStream xstream = new XStream();
        xstream.alias("packageconfig", PackageConfig.class);
        xstream.alias("visualization", VisualizationConfig.class);
        PackageConfig configObj = (PackageConfig)xstream.fromXML(configFile);
        
        String absolutePath = configFile.getAbsolutePath();
        String folderPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
        for(VisualizationConfig cfg : configObj.getVisualizations()) {
            
            String htmlFilePath = folderPath + File.separator + cfg.getFilename();
            
            File htmlFile = new File(htmlFilePath);
            cfg.setHtmlFile(htmlFile);
        }
        
        return configObj;
    }
    
    private List<VisualizationConfig> visualizations = new ArrayList<VisualizationConfig>();
    
    public PackageConfig() {
        
    }

    /**
     * @return the visualizations
     */
    public List<VisualizationConfig> getVisualizations() {
        return visualizations;
    }

    /**
     * @param visualizations the visualizations to set
     */
    public void setVisualizations(List<VisualizationConfig> visualizations) {
        this.visualizations = visualizations;
    }
}