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


public class EditarAvaliador {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel form1;

   public EditarAvaliador(int idCola){
   
      
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
      //form1.setBackground(Color.decode("#f9f9f9"));           
      form1.setOpaque(false);
      form1.setPreferredSize(new Dimension(400, 320));      
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 20, 0, 0);
   
      TelaMain.setContent("Editando Avaliador", "Editando Avaliador", form1, c);
      
      //inserindo campo Nome
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lNomeColaborador = new StyleLabels("Nome:", "#000000", 14, form1, f);
      StyleFields NomeColaborador = new StyleFields("Nome", form1, f);
      
      //inserindo combobox das categorias
      Categoria cat = new Categoria();      
      try { conn = bd.obtemConexao(); }
      catch(Exception e){ System.out.print(e.getStackTrace()); }           
      StyleLabels lcategoria = new StyleLabels("Categoria:", "#000000", 14, form1, f);
      StyleComboBox categoria = new StyleComboBox(cat.getCategoriasModel(conn), 2, form1, f);
      
      //inserindo campo CPF
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lCpfColaborador = new StyleLabels("CPF:", "#000000", 14, form1, f);
      StyleFields CpfColaborador = new StyleFields("CPF", form1, f);
      
      //inserindo campo Email
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lEmailColaborador = new StyleLabels("Email:", "#000000", 14, form1, f);
      StyleFields EmailColaborador = new StyleFields("Email", form1, f);
      
      //inserindo campo Senha
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lSenhaColaborador = new StyleLabels("Senha:", "#000000", 14, form1, f);
      StylePasswordFields SenhaColaborador = new StylePasswordFields("", form1, f);
      
      //inserindo botão salvar
      f.insets = new Insets(0, 0, 0, 0);
      StyleButtons salvar = new StyleButtons("SALVAR" , 14, "Green", form1, "Cheio", f);
      
      salvar.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){            
               try{
               
                  String pass = String.valueOf(SenhaColaborador.getPassword());
                  Usuario u = new Usuario(NomeColaborador.getText(), CpfColaborador.getText(), EmailColaborador.getText(), pass, "", categoria.getSelectedIndex());           
                     
                  conn = bd.obtemConexao();
                  conn.setAutoCommit(false);
                  u.AtualizarNome(conn);
                  u.AtualizarCPF(conn);
                  u.AtualizarEmail(conn);
                  u.AtualizarEspecialidade(conn);
                  if(!pass.equals("")){ u.AtualizarSenha(conn); }
                  conn.commit();
                  JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");
                    
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
         });
   
      
      
      //pegando os dados do banco
      String sqlSelect = "SELECT * FROM usuarios WHERE idUsuario = ?";
      PreparedStatement stm = null;
      ResultSet rs = null;
      Usuario us = new Usuario();
      try{
      
         conn = bd.obtemConexao();
         stm = conn.prepareStatement(sqlSelect);
         
         stm.setInt(1, idCola);
         rs = stm.executeQuery();
         
         if(rs.next()){
            us.setIdUsuario(rs.getInt(1));
            NomeColaborador.setText(rs.getString(2));
            CpfColaborador.setText(rs.getString(6));
            EmailColaborador.setText(rs.getString(5));
            categoria.setSelectedIndex(rs.getInt("idEspecialidade"));
            //Senha nunca se exibe no campo, mas se precisasse ta aqui em baixo
            //SenhaColaborador.setText(rs.getString(4));
                        
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