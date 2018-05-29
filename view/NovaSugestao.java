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


public class NovaSugestao {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel form1;
   StyleComboBox cbCategoria;
   StyleTextArea taSugestao;
   String aux;
   int idCola;

   public NovaSugestao(int idCola){
   
   
      //padrões de posicionamento do formulario de edição do colaborador
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;      
      
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
      form1.setPreferredSize(new Dimension(800, 500));      
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 20, 0, 0);
      
      TelaMain.setContent("Nova Sugestão", "Nova Sugestão", form1, c);
      
      //inserindo título do formulario
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels txt1 = new StyleLabels("Qual a sua nova sugestão?", "#444444", 16, form1, f);
      
      //inserindo campo Categoria
      f.insets = new Insets(0, 0, 0, 0);
      
      //inserindo combobox das categorias
      Categoria cat = new Categoria();      
      try { conn = bd.obtemConexao(); }catch(Exception e){ System.out.print(e.getStackTrace()); }           
      StyleComboBox categoria = new StyleComboBox(cat.getCategoriasModel(conn), 0, form1, f);             
            
      //inserindo campo Título
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lTitulo = new StyleLabels("Título", "#000000", 14, form1, f);
      StyleFields fTitulo = new StyleFields("Título", form1, f);
      
      //inserindo campo Sugestão
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lSugestao = new StyleLabels("Sugestão:", "#000000", 14, form1, f);
      taSugestao = new StyleTextArea  ("Qual a sua Sugestão? \nSeja objetivo, escreva tudo em 600 caracteres.", 300, 200, form1, f);
      String teste = taSugestao.getText();
      int caracter = teste.length();
      
      int limite = 600;
      
      if(caracter > limite){
         JOptionPane.showInputDialog(null, "Erro: Sugestão maior do que 600 caracteres");
      }
      
      //int digitados = taSugestao.length();
      
      //inserindo botão salvar
      f.insets = new Insets(0, 0, 0, 0);
      StyleButtons salvar = new StyleButtons("SALVAR" , 14, "Green", form1, "Cheio", f);
      
      //pegando o colaborador para salvar no banco
      String sqlSelect = "SELECT * FROM usuario WHERE idUsuario = ?";
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try{
         conn = bd.obtemConexao();
         stm = conn.prepareStatement(sqlSelect);
         
         stm.setInt(1, idCola);
         rs = stm.executeQuery();
      }
      catch(Exception e){
         System.out.print(e.getStackTrace());
      }
      
      
      //ações do botão salvar
      salvar.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent event){
               if(event.getSource() == salvar){
                  try{
                     Sugestao s = new Sugestao(idCola, fTitulo.getText(), taSugestao.getText(), categoria.getSelectedIndex());
                     conn = bd.obtemConexao();
                     conn.setAutoCommit(false);
                     s.Incluir(conn);
                     conn.commit();
                     //JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
                  }
                  catch(Exception e){
                     e.printStackTrace();
                        
                        if(conn != null){
                           try{
                              conn.rollback();
                           }
                           catch(SQLException e1){
                              System.out.print(e1.getStackTrace());
                           }
                        }
                  }
               }
            }
         }      
      );
                    
   }


}