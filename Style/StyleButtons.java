package Style;

import view.*;
import Conection.*;
import Controler.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StyleButtons extends JButton {

      String backColor, borderColor, backHoverColor;
     
      public StyleButtons(String nameButton, int fontSize, String color, JPanel p, String type, GridBagConstraints c){
         
         new JButton();
         setText(nameButton);
         
         if(type.equals("Vazio")){
         
               if(color.equals("Red")){
                  backColor   = "#d9534f";
                  backHoverColor = "#bd4945";
               }else if(color.equals("Green")){
                  backColor   = "#339999";
                  backHoverColor = "#4b944b";
               }else if(color.equals("Orange")){
                  backColor   = "#f0ad4e";
                  backHoverColor = "#d2861b";
               }else if(color.equals("Blue")){
                  backColor   = "#337ab7";
                  backHoverColor = "#23527b";
               }else if(color.equals("Grey")){
                  backColor   = "#888888";
                  backHoverColor = "#000000";
               }else if(color.equals("LightGrey")){
                  backColor   = "#f1f1f1";
                  backHoverColor = "#fbfbfb";
               }
                        
               
               setFocusPainted(false);
               setBorder(null);
               setHorizontalTextPosition(JButton.CENTER);
               setVerticalAlignment(SwingConstants.TOP);
               setForeground(Color.decode(backColor));
               setFont(new Font("Arial", Font.PLAIN, fontSize));
               setCursor(new Cursor(Cursor.HAND_CURSOR));
               setEnabled(true);
               setOpaque(false);
               setContentAreaFilled(false);
               
               addMouseListener(new MouseAdapter(){
                  public void mouseEntered(MouseEvent evt) {
                     setForeground(Color.decode(backHoverColor));
                  }
                  
                  public void mouseExited(MouseEvent evt) {
                     setForeground(Color.decode(backColor));
                  }
               });               
               
               
        }else if(type.equals("Cheio")){   
               
               if(color.equals("Red")){
                  backColor   = "#d9534f";
                  backHoverColor = "#bd4945";
                  borderColor = "#d43f3a";
               }else if(color.equals("Green")){
                  backColor   = "#5cb85c";
                  backHoverColor = "#4b944b";
                  borderColor = "#4cae4c";
               }else if(color.equals("Orange")){
                  backColor   = "#f0ad4e";
                  backHoverColor = "#d2861b";
                  borderColor = "#eea236";
               }else if(color.equals("Blue")){
                  backColor   = "#337ab7";
                  backHoverColor = "#23527b";
                  borderColor = "#2e6da4";
               }else if(color.equals("Grey")){
                  backColor   = "#999999";
                  backHoverColor = "#bbbbbb";
                  borderColor = "#666666";
               }else if(color.equals("LightGrey")){
                  backColor   = "#f9f9f9";
                  backHoverColor = "#fbfbfb";
                  borderColor = "#f9f9f9";
               }
                       
               
               setFocusPainted(false);
               setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.decode(borderColor), 1),
                                BorderFactory.createLineBorder(Color.decode(backColor), 8) ));
               setCursor(new Cursor(Cursor.HAND_CURSOR));
               setHorizontalTextPosition(JButton.CENTER);
               setVerticalAlignment(SwingConstants.TOP);
               
               if(color.equals("LightGrey")){
                  setForeground(Color.decode("#444444"));
               }else{
                  setForeground(Color.decode("#ffffff"));
               }
                              
               setBackground(Color.decode(backColor));
               setFont(new Font("Arial", Font.PLAIN, fontSize));
               setEnabled(true);
               //button.setOpaque(false);
               
               addMouseListener(new MouseAdapter(){
                  public void mouseEntered(MouseEvent evt) {
                     setBackground(Color.decode(backHoverColor));
                     setBorder(BorderFactory.createCompoundBorder(
                                      BorderFactory.createLineBorder(Color.decode(borderColor), 1),
                                      BorderFactory.createLineBorder(Color.decode(backHoverColor), 8) ));
                  }
                  
                  public void mouseExited(MouseEvent evt) {
                     setBackground(Color.decode(backColor));
                     setBorder(BorderFactory.createCompoundBorder(
                                      BorderFactory.createLineBorder(Color.decode(borderColor), 1),
                                      BorderFactory.createLineBorder(Color.decode(backColor), 8) ));
                  }
               });
         
         }
         
         p.add(this, c);
         
      
      }
      
      
}