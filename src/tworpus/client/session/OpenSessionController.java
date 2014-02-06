/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.session;

import com.sun.deploy.util.ArrayUtil;
import com.thoughtworks.xstream.XStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Markus
 */
public class OpenSessionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testSessionXml();
    }    

    private void testSessionXml() {
        Session session = new Session();
        Language l1 = new Language();
        Language l2 = new Language();
        l1.setName("de");
        l2.setName("en");
        Language[] langs = new Language[] {l1, l2};
        ArrayList<Language> langList = new ArrayList<Language>(Arrays.asList(langs));
        session.setLanguages(langList);
        session.setTitle("Testkorpus 1");
        session.setWords(50);
        session.setCharacters(220);
        session.setXmlCorpusName("corpus1");
        
        XStream xstream = session.getXStream();        
        String xml = xstream.toXML(session);
        System.out.println(xml);
    }
    
}
