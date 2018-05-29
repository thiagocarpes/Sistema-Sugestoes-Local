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


public class EditarColaborador {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel form1;

   public EditarColaborador(int idCola){
   
      
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
   
      TelaMain.setContent("Editando dados", "Editando Dados", form1, c);
      
      //inserindo título do formulario
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels txt1 = new StyleLabels("Editando os seus dados", "#444444", 16, form1, f);
      
      //inserindo campo Nome
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lNomeColaborador = new StyleLabels("Nome:", "#000000", 14, form1, f);
      StyleFields NomeColaborador = new StyleFields("Nome", form1, f);
      
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
                  Usuario u = new Usuario(NomeColaborador.getText(), CpfColaborador.getText(), EmailColaborador.getText(), pass, "", 0);
                     
                  conn = bd.obtemConexao();
                  conn.setAutoCommit(false);
                  u.AtualizarNome(conn);
                  u.AtualizarCPF(conn);
                  u.AtualizarEmail(conn);

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
      String sqlSelect = "SELECT * FROM usuarios WHERE idusuario = ?";
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try{
      
         conn = bd.obtemConexao();
         stm = conn.prepareStatement(sqlSelect);
         
         stm.setInt(1, idCola);
         rs = stm.executeQuery();
         
         if(rs.next()){
         
            NomeColaborador.setText(rs.getString(2));
            CpfColaborador.setText(rs.getString(6));
            EmailColaborador.setText(rs.getString(5));
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