package view;

import Controler.*;
import Conection.*;
import Style.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListaParColaborador{

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel coluns, sidebar1, sidebar2, line;
   
   public ListaParColaborador(){
   
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;
      
      coluns = new JPanel();
      coluns.setLayout(new GridBagLayout()); 
      coluns.setPreferredSize(new Dimension(1000, 550));           
      coluns.setOpaque(false);
      GridBagConstraints f = new GridBagConstraints();
      f.weighty = 0.1;
      f.weightx = 0.1;
      f.gridx = 0;
      f.gridy = 0;
      f.insets = new Insets(10, 20, 0, 0);
      f.anchor = GridBagConstraints.PAGE_START;
      
           
      sidebar1 = new JPanel();
      sidebar1.setLayout(new GridBagLayout());            
      sidebar1.setOpaque(false);
      sidebar1.setPreferredSize(new Dimension(480, 470));
      c.gridx = 0;
      c.gridy = 1;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(35, 0, 0, 0);
      coluns.add(sidebar1, c);
      
           line = new JPanel();
           line.setLayout(new GridBagLayout()); 
           line.setBackground(Color.decode("#ffffff"));
           line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#F1F1F1") ) );          
           line.setPreferredSize(new Dimension(0, 30));
           c.anchor = GridBagConstraints.FIRST_LINE_START;           
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 0;
           c.gridy = 0;
           c.insets = new Insets(0, 0, 0, 0);
           sidebar1.add(line, c);
           
           c.gridx = 0;
           c.gridy = 0;
           c.fill = GridBagConstraints.NONE;
           c.anchor = GridBagConstraints.FIRST_LINE_START;
           c.insets = new Insets(0, 20, 0, 0);
           new StyleLabels("Colaborador", "#000000", 16, line, c);
          
           c.anchor = GridBagConstraints.FIRST_LINE_END;
           c.insets = new Insets(0, 0, 0, 20);
           new StyleLabels("Sugestões postadas", "#000000", 16, line, c);       
         
       try {
                     
         conn = bd.obtemConexao();
         conn.setAutoCommit(false);
         ResultSet rst = getRelatorios(conn);         
         int countRow = 1;
         int t = 45;
         
         while(rst.next()){
         
           line = new JPanel();
           line.setLayout(new GridBagLayout()); 
           line.setBackground(Color.decode("#ffffff"));
           line.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#F1F1F1") ) );          
           line.setPreferredSize(new Dimension(0, 30));
           c.anchor = GridBagConstraints.FIRST_LINE_START;           
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 0;
           c.gridy = 0;
           c.insets = new Insets((countRow * t), 0, 0, 0);
           sidebar1.add(line, c);
           
           c.gridx = 0;
           c.gridy = 0;
           c.fill = GridBagConstraints.NONE;
           c.anchor = GridBagConstraints.FIRST_LINE_START;
           c.insets = new Insets(0, 20, 0, 0);
           StyleLabels titulo = new StyleLabels(rst.getString("nome"), "#444444", 12, line, c);
          
           c.anchor = GridBagConstraints.FIRST_LINE_END;
           c.insets = new Insets(0, 0, 0, 20);
           new StyleLabels(""+rst.getInt("quantidade"), "#444444", 12, line, c);
                  
           countRow++;
           
         }
                  
      }catch(Exception e){
         System.out.print(e.getStackTrace());
      }      
      c.fill = GridBagConstraints.NONE; 
            
      TelaMain.setContent("Participação", "Relatório de Participação", coluns, f); 
   }
   
   public ResultSet getRelatorios(Connection conn){
   
      String query = "SELECT u.nome, count(s.Colaborador) AS quantidade FROM sugestao s, usuarios u  WHERE s.Colaborador=u.idusuario GROUP BY s.Colaborador";   
      System.out.println(query);
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try {
         stm = conn.prepareStatement(query);
         rs = stm.executeQuery();
      }    
      catch(Exception e){
          e.printStackTrace();
      }
      
      return rs;
      
   }
   
   public String getNomeColab(Connection conn, int id){
      PreparedStatement stm = null;  
      ResultSet rst = null; 
      String nome = "";
      String obtNome = "SELECT nome FROM usuarios WHERE idusuarios= " + id;
      
      try {
         stm = conn.prepareStatement(obtNome);
         rst = stm.executeQuery();
         
         nome = rst.getString(1);
      }    
      catch(Exception e){
          e.printStackTrace();
      }
      return nome;
   }   
}