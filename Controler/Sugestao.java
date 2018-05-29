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

public class Sugestao{

   private int idSugestao;
   private int colaborador;
   private String titulo;
   private String sugestao;
   private String avaliador, feedback;
   private int especialidade;
   private int positivo;
   private int negativo;
   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   
   public static int idColaborador;

   public Sugestao(){
      //int idSugestao = 0;
      colaborador = 0;
      titulo = "";
      sugestao= "";
      avaliador= "";
      especialidade= 0;
      int positivo = 0;
      int negativo = 0;   
   }

   public Sugestao(int c, String t, String s, int e){      
      setColaborador(c);
      setTitulo(t);
      setSugestao(s);
      //setAvaliador(a);
      setEspecialidade(e);
   }

   public int getIdSugestao(){
      return idSugestao;
   }

   public void setIdSugestao(int i){
      idSugestao = i;
   }
   
   public void setColaborador(int c){
      colaborador = c;
   }
   public int getColaborador(){
      return colaborador;
   }
   
   public void setTitulo(String t){
      titulo = t;
   }
   public String getTitulo(){
      return titulo;
   }

   public void setSugestao(String s){
      sugestao = s;
   }
   public String getSugestao(){
      return sugestao;
   }

   public void setAvaliador(String a){
      avaliador = a;
   }
   public String getAvaliador(){
      return avaliador;
   }
   public void setEspecialidade(int e){
      especialidade = e;
   }
   public int getEspecialidade(){
      return especialidade;
   }
   public void setPositivo(int p){
      positivo = p;
   }
   public void setNegativo(int n){
      negativo = n;
   }
  
   public void setFeedback(String f){
      feedback = f;
   }
   
   public String getFeedback(){
      return feedback;
   }
      
   public void Incluir(Connection conn){
      NovaSugestao ns = new NovaSugestao(colaborador);
      String sqlInsert= "INSERT INTO sugestao(colaborador, titulo, sugestao, especialidade, status, Data) VALUES (?, ?, ?, ?, 'inativo', NOW())";
      PreparedStatement stm = null;
      try{
         stm = conn.prepareStatement(sqlInsert);
         
         if(getSugestao().length()>600){
            JOptionPane.showMessageDialog(null, "Teste");
         }
         else{
         stm.setInt(1, colaborador);
         stm.setString(2, getTitulo());
         stm.setString(3, getSugestao());         
         stm.setInt(4, getEspecialidade());
         
         stm.execute();
         JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
         }
      }
      catch(Exception e){
         e.printStackTrace();
         
         try{
            conn.rollback();
         }
         catch(Exception e1){
            e1.printStackTrace();
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
   
   public void AlterarStatus(Connection conn, String status, int idSugestao){

      String where = "";      
      if(status.equals("recusado")){
         where = ", FeedBack = ? ";
      }

      String query = "UPDATE sugestao SET status = ? "+where+" WHERE idSugestao = "+idSugestao;
      PreparedStatement stm = null;
      
      try{
         stm = conn.prepareStatement(query);
         stm.setString(1, status);         
         if(status.equals("recusado")){ stm.setString(2, getFeedback()); }         
         stm.execute();
         JOptionPane.showMessageDialog(null, "Status alterado com sucesso");
      }catch(Exception e){
         e.printStackTrace();         
         try{
            conn.rollback();
         }catch(Exception e1){
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
   
   public void Excluir(Connection conn){
      String sqlDelete = "DELETE FROM sugestao WHERE id = ?";
      
      PreparedStatement stm = null;
      
      try{
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
      finally{
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
   
   public void Carregar(Connection conn, String c){
      String sqlSelect = "SELECT * FROM sugetao WHERE colaborador = ?";
   
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try{
         stm = conn.prepareStatement(sqlSelect);
         
         stm.setString(1, c);
         rs = stm.executeQuery();
         
         if(rs.next()){
            this.setColaborador(rs.getInt("colaborador"));

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
      finally{
         if(stm != null){
            try{
               stm.close();
            }
            catch(SQLException e1){
               System.out.print(e1.getStackTrace());
            }
         }
      }
   }
}

