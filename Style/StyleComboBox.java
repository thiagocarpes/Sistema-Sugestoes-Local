package Style;

import view.*;
import Conection.*;
import Controler.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import java.awt.event.*;
import java.util.*;

public class StyleComboBox extends JComboBox {

   public StyleComboBox(DefaultComboBoxModel values, int SelectedIndex, JPanel p, GridBagConstraints c) {   

      new JComboBox(); 
      setModel(values);
      setSelectedIndex(SelectedIndex);
      setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#ffffff")), 
        BorderFactory.createEmptyBorder(0, 0, 0, 0)));    
      setForeground(Color.decode("#999999"));
      setBackground(Color.decode("#ffffff"));
      setOpaque(false);
      setFocusable(false);
      setCursor(new Cursor(Cursor.HAND_CURSOR));
        
      p.add(this, c);
  
   }
   
}