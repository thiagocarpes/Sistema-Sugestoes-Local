package view;

import Controler.*;
import Conection.*;
import Style.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import javax.swing.plaf.basic.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


public class NovoAvaliador {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel form1;
   String aux = "";
   
   public NovoAvaliador(){
      
      
        
      //padrões de posicionamento do formulario de edição do avaliador
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
      form1.setPreferredSize(new Dimension(400, 320));      
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 20, 0, 0);
      TelaMain.setContent("Editando Avaliador", "Editando Avaliador", form1, c);
      
            
      //inserindo título do formulario
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels txt1 = new StyleLabels("Cadastro de Avaliadores.", "#444444", 16, form1, f);
      
      //inserindo campo Nome
      StyleFields nome = new StyleFields("Nome", form1, f);    

      //inserindo combobox das categorias
      Categoria cat = new Categoria();      
      try { conn = bd.obtemConexao(); }catch(Exception e){ System.out.print(e.getStackTrace()); }           
      StyleComboBox categoria = new StyleComboBox(cat.getCategoriasModel(conn), 0, form1, f);
            
      //inserindo campo Email
      StyleFields email = new StyleFields("Email", form1, f);
      
      //inserindo campo Senha
      StylePasswordFields senha = new StylePasswordFields("Senha", form1, f);
      
      //inserindo campo cpf
      StyleFields cpf = new StyleFields("Cpf", form1, f);
      
      //inserindo botão salvar
      StyleButtons salvar = new StyleButtons("SALVAR" , 14, "Green", form1, "Cheio", f);
      salvar.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               if(e.getSource()==salvar){
                  if(nome.getText().equals("") || categoria.getSelectedItem().toString().equals("") ||
                  email.getText().equals("") || String.valueOf(senha.getPassword()).equals("") || cpf.getText().equals("")){
                     JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
                  }
                  else{
                     Usuario u = new Usuario(nome.getText(), cpf.getText(), email.getText(), String.valueOf(senha.getPassword()), "", categoria.getSelectedIndex());                  
                     u.IncluirAvaliador(conn);                  
                     JOptionPane.showMessageDialog(null, "Cadastro efetuado.");
                  }
               
               }
            }
         }
      
      );
      
      
      //pegando os dados do banco
      String sqlSelect = "SELECT * FROM especialidade";
      PreparedStatement stm = null;
      ResultSet rs = null;

      try{

         conn = bd.obtemConexao();
         stm = conn.prepareStatement(sqlSelect);

         rs = stm.executeQuery();

         if(rs.next()){
            rs.getString(2);           
         }
      }      
      catch(Exception e){
         e.printStackTrace();
         try{
            conn.rollback();
         }
         catch(SQLException e1){
            System.out.print(e1.getStackTrace());
         }

      }
   
                    
  }
  


}