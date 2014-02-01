/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.visualization.configuration;

import java.io.File;

/**
 *
 * @author Markus
 */
public class VisualizationConfig {
    private String title;
    private String filename;
    private File htmlFile;

    /**
     * @return the htmlFile
     */
    public File getHtmlFile() {
        return htmlFile;
    }

    /**
     * @param htmlFile the htmlFile to set
     */
    public void setHtmlFile(File htmlFile) {
        this.htmlFile = htmlFile;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
}
