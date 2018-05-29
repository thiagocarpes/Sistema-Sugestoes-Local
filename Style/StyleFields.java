package Style;

import view.*;
import Conection.*;
import java.sql.*;
import Controler.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;
import java.awt.event.*;
import java.util.EventObject;

public class StyleFields extends JTextField {

   Connection conn = null;
   AcessoBD bd = new AcessoBD();

   public StyleFields(String name, JPanel p, GridBagConstraints c) {
      new JTextField();      
      setText(name);
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
           
           if(name.equals("CPF") || name.equals("Cpf")){               
               
               try { conn = bd.obtemConexao(); }catch(Exception exc){ exc.printStackTrace(); }

               String newCPF = getText().replaceAll("[^0123456789]", "");
               //System.out.print(newCPF);
               
               if(!CNP.isValidCPF(newCPF) && !newCPF.equals(name)){
                  setText(name);                  
                  JOptionPane.showMessageDialog(null, "CPF inválido");
               }
               
               if(Usuario.CheckUser(conn, newCPF) && name.equals("Cpf")){
                  setText(name);
                  JOptionPane.showMessageDialog(null, "CPF já cadastrado");
               }              
           }
           
           if(name == "Email"){
           
            if(!EmailValidator.isEmailValid(getText()) && !getText().equals(name)){
               setText(name);
               JOptionPane.showMessageDialog(null, "Email inválido");
            }
           
           }
                      
         }
      });
            
   }


}