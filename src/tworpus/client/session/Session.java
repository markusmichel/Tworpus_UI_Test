/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.session;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.util.List;

public class Session {
    
    private String title;
    private File xmlCorpusFile;
    private String xmlCorpusName;
    private int words;
    private int characters;
    private List<Language> languages;
    
    public XStream getXStream() {
        XStream xstream = new XStream();
        xstream.alias("session", Session.class);
        xstream.alias("language", Language.class);
        
        return xstream;
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

    /**
     * @return the xmlCorpusFile
     */
    public File getXmlCorpusFile() {
        return xmlCorpusFile;
    }

    /**
     * @param xmlCorpusFile the xmlCorpusFile to set
     */
    public void setXmlCorpusFile(File xmlCorpusFile) {
        this.xmlCorpusFile = xmlCorpusFile;
    }

    /**
     * @return the xmlCorpusName
     */
    public String getXmlCorpusName() {
        return xmlCorpusName;
    }

    /**
     * @param xmlCorpusName the xmlCorpusName to set
     */
    public void setXmlCorpusName(String xmlCorpusName) {
        this.xmlCorpusName = xmlCorpusName;
    }

    /**
     * @return the words
     */
    public int getWords() {
        return words;
    }

    /**
     * @param words the words to set
     */
    public void setWords(int words) {
        this.words = words;
    }

    /**
     * @return the characters
     */
    public int getCharacters() {
        return characters;
    }

    /**
     * @param characters the characters to set
     */
    public void setCharacters(int characters) {
        this.characters = characters;
    }

    /**
     * @return the languages
     */
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     * @param languages the languages to set
     */
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
    
}
