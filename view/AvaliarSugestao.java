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
import java.math.*; 

public class AvaliarSugestao {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel coluns, sidebar1, sidebar2, line;
   private String loadFeedback; 

   public AvaliarSugestao(int idSugestao){
      
      //padrões de posicionamento do formulario
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
      //sidebar1.setBackground(Color.decode("#f1f1f1")); 
      sidebar1.setOpaque(false);
      sidebar1.setPreferredSize(new Dimension(510, 470));
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(0, 0, 0, 0);
      coluns.add(sidebar1, c);      
      
      ShowListSugestoes(sidebar1, idSugestao);            
      
      //coluna 2
      sidebar2 = new JPanel();
      sidebar2.setLayout(new GridBagLayout()); 
      sidebar2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#cccccc") ) );
      sidebar2.setPreferredSize(new Dimension(300, 370));           
      sidebar2.setOpaque(false);
      c.gridx = 1;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_END;
      c.insets = new Insets(30, 0, 0, 20);
      coluns.add(sidebar2, c);
      
      //Ações
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(0, 10, 0, 0);      
      new StyleLabels("Ações", "#444444", 18, line, c); 
      sidebar2.add(line, c);
      
      //botão aprovar
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(15, 10, 0, 0);      
      StyleButtons aprovar = new StyleButtons("APROVAR", 12, "Green", line, "Cheio", c);
      aprovar.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               Sugestao s = new Sugestao();
               try {
                  conn = bd.obtemConexao();
                  conn.setAutoCommit(false);
                  s.AlterarStatus(conn, "ativo", idSugestao); 
                  conn.commit();
                  new ListaAvaliaSugestoes();
               }catch(Exception exc){
                  exc.printStackTrace();
               }
            }
         }               
      ); 
      sidebar2.add(line, c);
      
      //botão inativar
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(35, 10, 0, 0);      
      StyleButtons inativar = new StyleButtons("INATIVAR", 12, "Grey", line, "Cheio", c);
      inativar.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               Sugestao s = new Sugestao();
               try {
                  conn = bd.obtemConexao();
                  conn.setAutoCommit(false);
                  s.AlterarStatus(conn, "inativo", idSugestao); 
                  conn.commit();
                  new ListaAvaliaSugestoes();
               }catch(Exception exc){
                  exc.printStackTrace();
               }
            }
         }               
      ); 
      sidebar2.add(line, c);
      

      //Texto Recusar
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(75, 10, 0, 0);      
      new StyleLabels("Recusar", "#444444", 18, line, c); 
      sidebar2.add(line, c);      
      
      //campo de texto do feedback
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(90, 10, 0, 0);
      String defaultText = (loadFeedback != null) ? loadFeedback : "Deixe um feedback para o colaborador";      
      StyleTextArea feedbackField = new StyleTextArea(defaultText, 100, 100, line, c);
      sidebar2.add(line, c);
      
      //botão recusado
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(140, 10, 0, 0);      
      StyleButtons reprovar = new StyleButtons("RECUSAR", 12, "Red", line, "Cheio", c);
      reprovar.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               Sugestao s = new Sugestao();
               try {
                  conn = bd.obtemConexao();
                  conn.setAutoCommit(false);
                  s.setFeedback(feedbackField.getText());
                  s.AlterarStatus(conn, "recusado", idSugestao); 
                  conn.commit();
                  new ListaAvaliaSugestoes();
               }catch(Exception exc){
                  exc.printStackTrace();
               }
            }
         }               
      ); 
      sidebar2.add(line, c);
      
      TelaMain.setContent("Avaliando", "Avaliando", coluns, f);    
   }
   
   public void ShowListSugestoes(JPanel panel, int id){
      
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;
   
      try {
                     
        conn = bd.obtemConexao();
        conn.setAutoCommit(false);         
               
        String query = "SELECT s.*, e.nomeEspecialidade FROM sugestao s, especialidade e WHERE s.Especialidade=e.idEspecialidade AND s.idSugestao = ?";   
        PreparedStatement stm = null;
        ResultSet rst = null;
         
        try {
            stm = conn.prepareStatement(query);
            stm.setInt(1, id);
            rst = stm.executeQuery();            
        }catch(Exception e){
            e.printStackTrace();
        }         
        rst.next();
        
        loadFeedback = rst.getString("FeedBack");
        
        //listando as sugestões
        line = new JPanel();
        int larguraDaDescricao = 470;
        line.setLayout(new GridBagLayout()); 
        line.setBackground(Color.decode("#ffffff"));
        line.setPreferredSize(new Dimension(larguraDaDescricao, 300));
        c.anchor = GridBagConstraints.FIRST_LINE_START;           
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        panel.add(line, c);
        
        c.insets = new Insets(20, 0, 0, 0);
        new StyleLabels(rst.getString("titulo"), "#444444", 18, line, c);
        
        c.insets = new Insets(43, 0, 8, 0);
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        new StyleLabels(d.format(rst.getDate("Data")) + " | " +rst.getString("nomeEspecialidade"), "#bbbbbb", 12, line, c);        

        c.insets = new Insets(75, 0, 0, 0);
        StyleLabels desc = new StyleLabels("", "#444444", 12, line, c);
        desc.setText("<html><p style=\"text-align:justify;width:"+(larguraDaDescricao - 75)+"px\">"+rst.getString("sugestao")+"</p></html>");        
        
      }catch(Exception e){
         e.printStackTrace();
      }
      
   }

}