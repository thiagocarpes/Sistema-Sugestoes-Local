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
import java.awt.*;
import java.security.*;

public class Comentario {

   private String comentario;
   private int id;
   private int colaborador;
   private int idSugestao;

   public Comentario(String comentario){
      this.comentario = comentario;
   }
   
   public void setComentario(String comentario){
      this.comentario = comentario;
   }
   public String getComentario(){
      return comentario;
   }
   public void setId(int id){
      this.id = id;
   }
   public int getId(){
      return id;
   }
   public void setIdSugestao(int idSugestao){
      this.idSugestao = idSugestao;
   }
   public int getIdSugestao(){
      return idSugestao;
   }
   public void setColaborador(int colaborador){
      this.colaborador = colaborador;
   }
   public int getColaborador(){
      return colaborador;
   }
   
   public void IncluirComentario(Connection conn, int idSugestao){
      
      String query = "INSERT INTO comentarios (idUsuario, idSugestao, comentario) VALUES (?, ?, ?);";      
      PreparedStatement pre = null;
      
      try {

        pre = conn.prepareStatement(query);                  
        pre.setInt(1, Usuario.idUsuario);
        pre.setInt(2, idSugestao);        
        pre.setString(3, getComentario());
        pre.execute();
        System.out.println("Comentário inserido");
        
                 
      }catch(Exception exc){
         exc.printStackTrace();         
      }
   
   }
   
   public ResultSet ShowComentario(Connection conn, int idSugestao){
      
      String query = "SELECT c.*, u.* FROM comentarios c, usuarios u WHERE u.idusuario=c.idusuario AND c.idsugestao = ?;";      
      PreparedStatement pre = null;
      ResultSet rs = null;
      
      try {

        pre = conn.prepareStatement(query);                  
        pre.setInt(1, idSugestao);        
        rs = pre.executeQuery();
               
      }catch(Exception exc){
         exc.printStackTrace();         
      }
      
      return rs;
   
   }
}