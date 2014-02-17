/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweet;

import com.thoughtworks.xstream.converters.Converter;

/**
 * Content and metainformation of a tweet message.
 */
public class Message {

    private String text;
    private int chars;
    private int words;
    private String language;

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the chars
     */
    public int getChars() {
        return chars;
    }

    /**
     * @param chars the chars to set
     */
    public void setChars(int chars) {
        this.chars = chars;
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
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }
}
