package Controler;

import view.*;
import Conection.*;
import Style.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.*;

public class Categoria{
   
   public String nome, corEspecialidade;
   public static int EditId;
   public ArrayList <String> categorias = new ArrayList<String>();
   
   public Categoria(){
      nome = "";
      corEspecialidade = "";
   }
   
   public Categoria(String nome, String cor){
      setNome(nome);
      setCorEspecialidade(cor);
   }
   
   public void setNome(String nome){
      this.nome = nome;
   }
   
   public String getNome(){
      return nome;
   }
   
   public void setCorEspecialidade(String s){
      corEspecialidade = s;
   }
   
   public String getCorEspecialidade(){
      return corEspecialidade;
   }
   
   public String getCategoria(){
      String teste = "";
      for(int i = 0; i < categorias.size(); i++){
          teste = categorias.get(i);
      }
      return teste;
   }
   
   
   public void Incluir(Connection conn){
      String sqlInsert= "INSERT INTO especialidade(nomeEspecialidade, corEspecialidade) VALUES (?, ?)";
      
      PreparedStatement stm = null;
      
      try{
           stm = conn.prepareStatement(sqlInsert);
            stm.setString(1, getNome());
            stm.setString(2, getCorEspecialidade());
            stm.execute();
      }
      catch(Exception e){
         e.printStackTrace();
         
         try{
            conn.rollback();
         }
         catch(Exception e1){
            System.out.print(e1.getStackTrace());
         }
      }
      finally{
         if(stm!=null){
            try{
               stm.close();
            }
            catch(Exception e1){
               System.out.print(e1.getStackTrace());
            }
         }
      }
   
   }
   public void Excluir(Connection conn, String nome){
      String sqlDelete = "DELETE FROM especialidade WHERE nomeEspecialidade = " + nome;
      
      PreparedStatement stm = null;
      
      try {
         stm = conn.prepareStatement(sqlDelete);
         stm.execute();
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
      finally { 
         if(stm!=null){
            try{
               stm.close();
            }
            catch(SQLException e1){
               System.out.print(e1.getStackTrace());
            }
         }
      }
   }
   
   public void Atualizar(Connection conn, int id){
      String sqlDelete = "UPDATE especialidade SET corEspecialidade = '"+ getCorEspecialidade() +"', nomeEspecialidade = '"+ getNome() +"' WHERE idEspecialidade = " + id;
      
      PreparedStatement stm = null;
      
      try {
         stm = conn.prepareStatement(sqlDelete);
         stm.execute();
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
      finally { 
         if(stm!=null){
            try{
               stm.close();
            }
            catch(SQLException e1){
               System.out.print(e1.getStackTrace());
            }
         }
      }
   }
   
   public ResultSet Carregar(Connection conn){
      String sqlSelect = "SELECT * FROM especialidade ORDER BY idEspecialidade ASC";      
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try {
         stm = conn.prepareStatement(sqlSelect);
         rs = stm.executeQuery();
      }catch(Exception e){
         e.printStackTrace();
      }   
      return rs;
   }
   
   public DefaultComboBoxModel getCategoriasModel(Connection conn){
   
      DefaultComboBoxModel model = new DefaultComboBoxModel();
      model.addElement( new Item(0, "Selecione o departamento" ) );
      
      try {
         conn.setAutoCommit(false);
         ResultSet tst = this.Carregar(conn);
         
         while(tst.next()){
            String especialidade   = tst.getString("nomeEspecialidade");
            int    idEspecialidade = tst.getInt("idEspecialidade");
            model.addElement( new Item(idEspecialidade, especialidade) );
            
         }
      
      }
      catch(Exception e){
         e.printStackTrace();
      }      
            
      return model;
   
   }
}