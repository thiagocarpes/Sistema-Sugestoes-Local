package Style;

import view.*;
import Conection.*;
import Controler.*;

import javax.swing.*;
import java.awt.*;

public class StyleLabels extends JLabel {

  
   public StyleLabels(String l, String color, int size, JPanel p, GridBagConstraints c) {

      new JLabel();
      setText(l);
      setFont(new Font("Arial", Font.PLAIN, size));
      setForeground(Color.decode(color));
      setEnabled(true);      
      p.add(this, c);
      
   }
   
}