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

public class StylePasswordFields extends JPasswordField {

   public StylePasswordFields(String name, JPanel p, GridBagConstraints c) {
      new JPasswordField();
      setEchoChar((char) 0);     
      setText(name);
      setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#cccccc")), 
        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
      p.add(this, c);
      p.add(Box.createRigidArea(new Dimension(0,10)));
      setForeground(Color.decode("#999999"));
            
      addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent e) {
           if(String.valueOf(getPassword()).equals(name)){
               setText("");
               setEchoChar('•');
               setForeground(Color.decode("#444444"));
           }
         }
   
         public void focusLost(FocusEvent e) {
           if(String.valueOf(getPassword()).equals("")){
               setEchoChar((char) 0);
               setText(name);
               setForeground(Color.decode("#999999"));
           }
         }
      });
            
   }


}