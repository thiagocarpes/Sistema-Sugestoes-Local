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
import java.text.SimpleDateFormat;
import java.util.Date;


public class VerSugestao {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   
   JPanel form1, line,sidebar1, sidebar2, coluns;
   String aux = "";
   StyleTextArea taComentario;
   private int idSugestao;
   
   public VerSugestao(int id){
      
      idSugestao = id;
      
      //padrões de posicionamento do formulario de enclusão de categoria
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;      
      
      //bloco com as duas colunas
      coluns = new JPanel();
      coluns.setLayout(new GridBagLayout());
      coluns.setOpaque(false);
      coluns.setPreferredSize(new Dimension(1000, 520));
      
      //padroes de posicionamento para os campos dentro do form1
      GridBagConstraints f = new GridBagConstraints();
      f.fill = GridBagConstraints.HORIZONTAL;
      f.gridx = GridBagConstraints.REMAINDER;
      f.weighty = 0.1;
      f.weightx = 0.1;
            
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 20, 0, 0);
      TelaMain.setContent("Sugestão", "Sugestão", coluns, c);
      
     
      //coluna 1
      sidebar1 = new JPanel();
      sidebar1.setLayout(new GridBagLayout()); 
      sidebar1.setOpaque(false);
      sidebar1.setPreferredSize(new Dimension(650, 520));
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(0, 0, 0, 0);
      coluns.add(sidebar1, c);
      
    
      
      try {
                     
         conn = bd.obtemConexao();
         conn.setAutoCommit(false);
         ResultSet rst = getSugestoes(conn);         
         int countRow = 0;
      
      
         while(rst.next()){
         
            //listando as sugestões
            line = new JPanel();
            int larguraDaDescricao = 580;
            line.setLayout(new GridBagLayout()); 
            line.setOpaque(false);
            //line.setBackground(Color.decode("#f1f1f1"));
            line.setPreferredSize(new Dimension(larguraDaDescricao, 480));
            c.anchor = GridBagConstraints.FIRST_LINE_START;           
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 0, 0);
            sidebar1.add(line, c);
              
            c.insets = new Insets(20, 0, 0, 0);
            new StyleLabels(rst.getString("titulo"), "#444444", 18, line, c);
              
            c.insets = new Insets(43, 0, 8, 0);
            SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
            new StyleLabels(d.format(rst.getDate("Data")) + " | " +rst.getString("nomeEspecialidade"), "#bbbbbb", 12, line, c);        
         
            c.insets = new Insets(75, 0, 0, 0);
            StyleLabels desc = new StyleLabels("", "#444444", 12, line, c);
            desc.setText("<html><p style=\"text-align:justify;width:"+(larguraDaDescricao - 75)+"px\">"+rst.getString("sugestao")+"</p></html>");        
            if(Usuario.getTipoUsuario() == 1){
               
            
               c.insets = new Insets(0, 0, 110, 0);
               c.anchor = GridBagConstraints.LAST_LINE_START;
               StyleLabels comentario = new StyleLabels("Comentário", "#444444", 12, line, c);
            
               c.insets = new Insets(0, 0, 80, 0);
               c.anchor = GridBagConstraints.LAST_LINE_START;
               taComentario = new StyleTextArea("Faça seu comentário com até 600 caracteres.", 0, 20, line, c);
            
               c.insets = new Insets(0, 0, 0, 0);
               c.anchor = GridBagConstraints.LAST_LINE_START;
               StyleButtons salvar = new StyleButtons("SALVAR COMENTÁRIO" , 14, "Green", line, "Cheio", c);
               salvar.addActionListener(
                  new ActionListener(){
                     public void actionPerformed(ActionEvent e){
                        Usuario us = new Usuario();
                        Comentario com = new Comentario(taComentario.getText());
                        try {
                           conn = bd.obtemConexao();
                           conn.setAutoCommit(false);
                           com.IncluirComentario(conn, idSugestao); 
                           conn.commit();
                           new VerSugestao(idSugestao);
                        }
                        catch(Exception exc){
                           exc.printStackTrace();
                        }
                     }
                  }
                  
                  );
            }
            countRow++;
           
         }
         
      }
      catch(Exception e){
         System.out.print(e.getStackTrace());
      }      
      c.fill = GridBagConstraints.NONE;    
         
      //coluna 2
      sidebar2 = new JPanel();
      sidebar2.setLayout(new GridBagLayout()); 
      sidebar2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#cccccc") ) );
      sidebar2.setPreferredSize(new Dimension(300, 480));           
      sidebar2.setOpaque(false);
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_END;
      c.insets = new Insets(30, 0, 0, 20);
      coluns.add(sidebar2, c);
      
      try {
                     
         conn = bd.obtemConexao();
         conn.setAutoCommit(false);
         Comentario coment = new Comentario("");
         ResultSet rst = coment.ShowComentario(conn, id);         
         int countRow = 0;
      
      
         while(rst.next()){
         
              //listando as sugestões
            line = new JPanel();
            int AlturadasLinhas = 75;//importante nessa posição
            int larguraDaDescricao = 300;
            line.setLayout(new GridBagLayout()); 
            line.setOpaque(false);
            line.setPreferredSize(new Dimension(larguraDaDescricao, 340));
            c.anchor = GridBagConstraints.FIRST_LINE_START;           
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets((countRow * AlturadasLinhas), 0, 0, 0);
            sidebar2.add(line, c);
              
            c.insets = new Insets(0, 10, 0, 0);
            new StyleLabels(rst.getString("nome"), "#444444", 18, line, c);
              
            c.insets = new Insets(20, 10, 0, 0);
            StyleLabels desc = new StyleLabels("", "#444444", 12, line, c);
            desc.setText("<html><p style=\"text-align:justify;width:"+(larguraDaDescricao - 75)+"px\">"+rst.getString("comentario")+"</p></html>");        
                  
            countRow++;
           
         }
      
      
      
      
      }
      catch(Exception e){
         System.out.print(e.getStackTrace());
      }      
      c.fill = GridBagConstraints.NONE;    
   
   
   }
   
   public ResultSet getSugestoes(Connection conn){   
      
      String query = "SELECT s.*, e.nomeEspecialidade FROM sugestao s, especialidade e WHERE s.Especialidade=e.idEspecialidade AND s.idSugestao = ?";   
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try {
         stm = conn.prepareStatement(query);
         stm.setInt(1, idSugestao);
         rs = stm.executeQuery();
         
      }    
      catch(Exception e){
         e.printStackTrace();
      }
      
      return rs;
      
   }
   
   
   
}