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


public class EditaCategoria {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel form1;
   String aux = "";
   public EditaCategoria(){
      
      //padrões de posicionamento do formulario de enclusão de categoria
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
      form1.setPreferredSize(new Dimension(400, 220));      
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 20, 0, 0);
      TelaMain.setContent("Editando Categoria", "Editando Categoria", form1, c);
      
      
      //inserindo título do formulario
      f.insets = new Insets(0, 0, 20, 0);
      StyleLabels txt1 = new StyleLabels("Editando Categoria", "#444444", 16, form1, f);
      
      //inserindo campo Categoria
      f.insets = new Insets(0, 0, 0, 0);
      new StyleLabels("Nome da Especialidade", "#444444", 12, form1, f);      
      f.insets = new Insets(0, 0, 0, 0);
      StyleFields categoria = new StyleFields("", form1, f);
      
      //inserindo campo cor da categoria
      f.insets = new Insets(0, 0, 0, 0);
      new StyleLabels("Cor da Especialidade", "#444444", 12, form1, f);      
      f.insets = new Insets(0, 0, 0, 0);
      StyleFields corCategoria = new StyleFields("", form1, f);         
              
      try {
      
         String sqlSelect = "SELECT * FROM especialidade WHERE idEspecialidade = ?";
         PreparedStatement stm = null;
         ResultSet rs = null;
                
         conn = bd.obtemConexao();
         stm = conn.prepareStatement(sqlSelect);         
         stm.setInt(1, Categoria.EditId);
         rs = stm.executeQuery();
         if(rs.next()){
            categoria.setText(rs.getString("nomeEspecialidade"));
            corCategoria.setText(rs.getString("corEspecialidade"));
         }

      } catch(Exception e){
         e.printStackTrace();
      }
      
      //inserindo botão salvar
      f.insets = new Insets(0, 0, 0, 0);
      StyleButtons salvar = new StyleButtons("SALVAR" , 14, "Green", form1, "Cheio", f);
      salvar.addActionListener(
      new ActionListener(){
         public void actionPerformed(ActionEvent e){
            try{
               Categoria c = new Categoria(categoria.getText(), corCategoria.getText());
               conn = bd.obtemConexao();
               conn.setAutoCommit(false);
               c.Atualizar(conn, Categoria.EditId);
               conn.commit();
               JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");
            }catch(Exception ex){
               ex.printStackTrace();
                  
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

            
   }
}