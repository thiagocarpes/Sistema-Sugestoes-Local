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

public class ListaAprovadosCatg {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel coluns, sidebar1, sidebar2, line;
   
   public ListaAprovadosCatg(){
      
      //padrões de posicionamento do formulario de inclusão de categoria
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;      
            
      //bloco com as duas colunas
      coluns = new JPanel();
      coluns.setLayout(new GridBagLayout()); 
      //coluns.setBackground(Color.decode("#f9f9f9"));
      coluns.setPreferredSize(new Dimension(1000, 550));           
      coluns.setOpaque(false);
      GridBagConstraints f = new GridBagConstraints();
      f.weighty = 0.1;
      f.weightx = 0.1;
      f.gridx = 0;
      f.gridy = 0;
      f.insets = new Insets(10, 20, 0, 0);
      f.anchor = GridBagConstraints.PAGE_START;
        
      //coluna 1
      sidebar1 = new JPanel();
      sidebar1.setLayout(new GridBagLayout()); 
      sidebar1.setOpaque(false);
      sidebar1.setPreferredSize(new Dimension(489, 470));
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
           new StyleLabels("Nome", "#000000", 16, line, c);
          
           c.anchor = GridBagConstraints.FIRST_LINE_END;
           c.insets = new Insets(0, 0, 0, 20);
           new StyleLabels("Sugestões", "#000000", 16, line, c); 
                      
      
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
           StyleLabels titulo = new StyleLabels(rst.getString("nomeEspecialidade"), "#444444", 12, line, c);
          
           c.anchor = GridBagConstraints.FIRST_LINE_END;
           c.insets = new Insets(0, 0, 0, 20);
           new StyleLabels(""+rst.getInt("quantidade"), "#444444", 12, line, c);
        
           countRow++;
           
         }
                  
      }catch(Exception e){
         System.out.print(e.getStackTrace());
      }      
     
      TelaMain.setContent("Sugestões", "Sugestões Aprovadas", coluns, f);    
   }
   
   
   public ResultSet getRelatorios(Connection conn){
     
      String query = "SELECT e.nomeEspecialidade, count(s.idSugestao) AS quantidade FROM sugestao s, especialidade e  WHERE s.Especialidade=e.idEspecialidade and s.`status` = 'ativo' GROUP BY e.nomeEspecialidade ORDER BY s.idSugestao";   
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
   
   
}