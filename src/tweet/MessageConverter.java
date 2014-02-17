/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweet;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MessageConverter implements Converter {

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Message message = (Message) source;
        writer.addAttribute("chars", "" + message.getChars());
        writer.addAttribute("words", "" + message.getWords());
        writer.addAttribute("lang", "" +  message.getLanguage());
        writer.setValue(message.getText());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        // @TODO: Test method
        System.out.println("unmarshal");
        Message message = new Message();
        
        message.setLanguage(reader.getAttribute("lang"));
        message.setChars(Integer.parseInt(reader.getAttribute("chars")));
        message.setWords(Integer.parseInt(reader.getAttribute("words")));
        message.setText(reader.getValue());

        return message;
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(Message.class);
    }
}
