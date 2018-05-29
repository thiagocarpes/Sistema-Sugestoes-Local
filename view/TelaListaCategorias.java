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


public class TelaListaCategorias {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel form1, line,sidebar1, coluns;
   String aux = "";
   public TelaListaCategorias(){
   
      
      //padrões de posicionamento do formulario de enclusão de categoria
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;      
      
      //bloco com as duas colunas
      coluns = new JPanel();
      coluns.setLayout(new GridBagLayout());
      
      //padroes de posicionamento para os campos dentro do form1
      GridBagConstraints f = new GridBagConstraints();
      f.fill = GridBagConstraints.HORIZONTAL;
      f.gridx = GridBagConstraints.REMAINDER;
      f.weighty = 0.1;
      f.weightx = 0.1;
            
      //bloco do formulario de alteração
      form1 = new JPanel();
      form1.setLayout(new GridBagLayout()); 
      form1.setBackground(Color.decode("#f9f9f9"));           
      //form1.setOpaque(false);
      form1.setPreferredSize(new Dimension(400, 320));      
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 20, 0, 0);
      TelaMain.setContent("Categorias", "Categorias", coluns, c);
      
      
      
      
      //inserindo título do formulario
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels txt1 = new StyleLabels("Lista de Categorias.", "#444444", 18, coluns, f);
      
      //coluna 1
      sidebar1 = new JPanel();
      sidebar1.setLayout(new GridBagLayout()); 
      //sidebar1.setBackground(Color.decode("#000000"));           
      sidebar1.setOpaque(false);
      sidebar1.setPreferredSize(new Dimension(610, 470));
      c.gridx = 0;
      c.gridy = 1;
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
            line.setLayout(new GridBagLayout()); 
            line.setBackground(Color.decode("#ffffff"));
            line.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.decode("#FF6600") ) );          
           //line.setOpaque(false);
            line.setPreferredSize(new Dimension(0, 75));
            c.anchor = GridBagConstraints.FIRST_LINE_START;           
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = countRow;
            c.insets = new Insets(0, 0, 0, 0);
            sidebar1.add(line, c);
           
            c.gridx = 0;
            c.gridy = 0;
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.insets = new Insets(10, 20, 0, 0);
            StyleLabels titulo = new StyleLabels(rst.getString("nomeEspecialidade"), "#444444", 15, line, c);
            titulo.setCursor(new Cursor(Cursor.HAND_CURSOR));
                     
            c.anchor = GridBagConstraints.LAST_LINE_END;
            c.insets = new Insets(0, 0, 0, 0);
            StyleButtons btnEditarCategoria = new StyleButtons("Editar Categoria", 12, "Green", line, "Vazio", c);
            int idCat = rst.getInt("idEspecialidade");
                      
            btnEditarCategoria.addActionListener(
            new ActionListener(){
               public void actionPerformed(ActionEvent e){
                  Categoria.EditId = idCat;
                  new EditaCategoria();        
               }
            });
         
            countRow++;
           
         }
      }
      catch(Exception e){
         e.getStackTrace();
      }      
      c.fill = GridBagConstraints.NONE; 
      
         
      //inserindo botão salvar
      f.insets = new Insets(0, 0, 0, 0);
      StyleButtons salvar = new StyleButtons("NOVA CATEGORIA" , 14, "Green", coluns, "Cheio", f);
      salvar.addActionListener(
            new ActionListener(){
               public void actionPerformed(ActionEvent e){
                  new TelaCategoria();       
               }
            });
   
   
   
   }
   
   public ResultSet getSugestoes(Connection conn){
   
      String query = "SELECT * FROM especialidade";   
      
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