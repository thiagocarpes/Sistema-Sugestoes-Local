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

public class ListaRelatoriosMaster {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel coluns, sidebar1, sidebar2, line;
   
   public ListaRelatoriosMaster(){
      
      //padrões de posicionamento do formulario de inclusão de categoria
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;      
      
      //bloco com as duas colunas
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
            
      
      //coluna 1
      sidebar1 = new JPanel();
      sidebar1.setLayout(new GridBagLayout()); 
      sidebar1.setOpaque(false);
      sidebar1.setPreferredSize(new Dimension(710, 470));
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
           new StyleLabels("Especialidade", "#000000", 16, line, c);
           
           c.anchor = GridBagConstraints.PAGE_START;
           c.insets = new Insets(0, 0, 0, 150);
           new StyleLabels("Avaliadas", "#000000", 16, line, c);
           
           c.anchor = GridBagConstraints.PAGE_START;
           c.insets = new Insets(0, 150, 0, 0);
           new StyleLabels("Sugestões", "#000000", 16, line, c);           
          
           c.anchor = GridBagConstraints.FIRST_LINE_END;
           c.insets = new Insets(0, 0, 0, 20);
           new StyleLabels("Índice de devolutivas", "#000000", 16, line, c);            
      
      try {
                     
         conn = bd.obtemConexao();
         conn.setAutoCommit(false);
         ResultSet rst = getRelatorios(conn);         
         int countRow = 1;
         int p = 0;
         int t = 45;
         
         while(rst.next()){
         
           //listando as sugestões
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
           
           c.anchor = GridBagConstraints.PAGE_START;
           c.insets = new Insets(0, 150, 0, 0);
           new StyleLabels(""+rst.getInt("enviadas"), "#444444", 12, line, c);
           
            String queryAval = "SELECT count(idSugestao) AS avaliadas FROM sugestao WHERE `status` <> 'inativo' AND idSugestao="+rst.getInt("idSugestao");   
            PreparedStatement stm2 = null;
            ResultSet rsAval = null;
            
            try {
               stm2 = conn.prepareStatement(queryAval);
               rsAval = stm2.executeQuery();
            }    
            catch(Exception e2){
                e2.printStackTrace();
            }
            rsAval.next();          

           c.anchor = GridBagConstraints.PAGE_START;
           c.insets = new Insets(0, 0, 0, 150);
           new StyleLabels("" + rsAval.getInt("avaliadas"), "#444444", 12, line, c);      

           p = (rsAval.getInt("avaliadas") * 100) / rst.getInt("enviadas");           
           c.anchor = GridBagConstraints.FIRST_LINE_END;
           c.insets = new Insets(0, 0, 0, 20);
           new StyleLabels(""+p+"%", "#444444", 12, line, c);           
            
           countRow++;
           
         }
                  
      }catch(Exception e){
         System.out.print(e.getStackTrace());
      }      
      
      TelaMain.setContent("Sugestões", "Índice Geral", coluns, f);    
   }
   
   
   public ResultSet getRelatorios(Connection conn){
    
      String query = "SELECT e.idEspecialidade, s.idSugestao, e.nomeEspecialidade, count(s.idSugestao) AS enviadas FROM sugestao s INNER JOIN especialidade e ON s.Especialidade = e.idEspecialidade group by e.nomeEspecialidade;";   
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