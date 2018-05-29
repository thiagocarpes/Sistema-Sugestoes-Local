package Style;

import view.*;
import Conection.*;
import Controler.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import java.awt.event.*;
import java.util.EventObject;

public class StyleTextArea extends JTextArea {

   public StyleTextArea(String name, int width, int height, JPanel p, GridBagConstraints c) {
      new JTextArea();      
      setText(name);
      setLineWrap(true);
      setWrapStyleWord(true);
      setPreferredSize(new Dimension(width, height));
      setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#cccccc")), 
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
      p.add(this, c);
      p.add(Box.createRigidArea(new Dimension(0,10)));
      setForeground(Color.decode("#999999"));
      
      addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent e) {
           if(getText().equals(name)){
               setText("");
               setForeground(Color.decode("#444444"));
           }
         }
   
         public void focusLost(FocusEvent e) {
           if(getText().equals("")){
               setText(name);
               setForeground(Color.decode("#999999"));
           }
         }
      });
            
   }


}