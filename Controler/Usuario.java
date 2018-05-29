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

public class Usuario{

   private String nome;
   private String cpf;
   private String email;
   private String senha;
   private String curso;
   private int id;
   private int tipo;
   
   public static int tipoUsuario, idEspecialidade, idUsuario;
   public static String publicNome;

   public Usuario(int idUsuario){
         this.idUsuario = idUsuario;
   }
   
   public Usuario(){
      nome = "";
      cpf = "";
      email= "";
      senha= "";
      curso= "";
      id=0;   
   }

   public Usuario(String n, String cp, String e, String s, String c, int esp){
      setNome(n);
      publicNome = n;
      setCpf(cp);
      setEmail(e);
      setSenha(s);
      setCurso(c);
      setIdEspecialidade(esp);
   }
   
   public Usuario(int id, String n, String cp, String e, String s, String c){      
      setIdUsuario(id);
      setNome(n);
      setCpf(cp);
      setEmail(e);
      setSenha(s);
      setCurso(c);
      
   }

   public void setIdUsuario(int i){
      idUsuario = i;
   }
   public int getidUsuario(){
      return idUsuario;
   }
   
   public static void setTipoUsuario(int i){
      tipoUsuario = i;
   }
   public static int getTipoUsuario(){
      return tipoUsuario;
   }
   
   public static void setPublicNome(String s){
      publicNome = s;
   }
   
   public static String getPublicNome(){
      return publicNome;
   }
   
   public void setNome(String n){
      nome = n;
   }
   public String getNome(){
      return nome;
   }
   
   public void setCpf(String cp){
      cpf = cp.replaceAll("[^0123456789]", "");
   }
   public String getCpf(){
      return cpf;
   }

   public void setEmail(String e){
      email = e;
   }
   public String getEmail(){
      return email;
   }

   public void setSenha(String s){
      senha = MD5.MD5(s);
   }
   public String getSenha(){
      return senha;
   }
   public void setCurso(String c){
      curso = c;
   }
   public String getCurso(){
      return curso;
   }
   public void setTipo(int t){
      tipo = t;
   }
   public int getTipo(){
      return tipo;
   }
   public void setIdEspecialidade(int i){
      idEspecialidade = i;
   }
   public static int getIdEspecialidade(){
      return idEspecialidade;
   }
  
      
   public void IncluirColaborador(Connection conn){
      String sqlInsertColaborador= "INSERT INTO usuarios(nome, cpf, senha, email, tipo) VALUES (?, ?, ?, ?, 1)";      
      PreparedStatement stm = null;
      
      try{
         stm = conn.prepareStatement(sqlInsertColaborador);
                  
         stm.setString(1, getNome());
         stm.setString(2, getCpf());         
         stm.setString(3, getSenha());
         stm.setString(4, getEmail());         
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
   
   public void IncluirAvaliador(Connection conn){
      String sqlInsertAvaliador= "INSERT INTO usuarios(nome, cpf, senha, email, tipo, idEspecialidade) VALUES (?, ?, ?, ?, 2, ?)";
      
      PreparedStatement stm = null;
      
      try{
         stm = conn.prepareStatement(sqlInsertAvaliador);
         
         stm.setString(1, getNome());
         stm.setString(2, getCpf());         
         stm.setString(3, getSenha());
         stm.setString(4, getEmail());
         stm.setInt(5, getIdEspecialidade());
         
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
   
   public void ExcluirColaborador(Connection conn){
      String sqlDelete = "DELETE FROM usuarios WHERE id = ? and tipo = 1";
      
      PreparedStatement stm = null;
      
      try{
         stm = conn.prepareStatement(sqlDelete);
         //stm.setInt(1, getIdColab());
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
   
   public void Login(Connection conn, String em, String s, JFrame t){
      String sqlLogin = "SELECT * FROM usuarios WHERE email=? and senha=?";
   
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try {
         stm = conn.prepareStatement(sqlLogin);
         
         String passwordHash = MD5.MD5(s);

         stm.setString(1, em);
         stm.setString(2, passwordHash);
         rs = stm.executeQuery();
         
         if(rs.next()){
            this.setEmail(rs.getString("email"));
            this.setSenha(rs.getString("senha")); 
                    
            setIdUsuario(rs.getInt(1));
            setIdEspecialidade(rs.getInt("idEspecialidade"));
            setTipoUsuario(rs.getInt("tipo"));
            setPublicNome(rs.getString("nome"));
            JOptionPane.showMessageDialog(null, "Bem vindo "+rs.getString(2)+"!"+"\n Login Realizado");
            DoLogin(t);            
         }else{
            JOptionPane.showMessageDialog(null, "Email e/ou Senha estão incorretos.");
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
   
   public static boolean CheckUser(Connection conn, String cpf){
      String sqlLogin = "SELECT * FROM usuarios WHERE cpf=?";
      boolean check = true;
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try{
         stm = conn.prepareStatement(sqlLogin);        
         stm.setString(1, cpf.replaceAll("[^0123456789]", ""));
         rs = stm.executeQuery();
         
         if(rs.next()){ check = true; }else{ check = false; }
         
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
      
      return check;
   }
   
   public void AtualizarNome(Connection conn){
      String sqlAtualizarNome = "Update usuarios set nome = ? where idusuario = ?";
      
      PreparedStatement stm = null;
      
      try{
         stm = conn. prepareStatement(sqlAtualizarNome);
         
         stm.setString(1, getNome());
         stm.setInt(2, getidUsuario());
         
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
   
   }
   public void AtualizarCPF(Connection conn){
      String sqlAtualizarCPF = "Update usuarios set cpf = ? where idusuario = ?";
      
      PreparedStatement stm = null;
      
      try{
         stm = conn. prepareStatement(sqlAtualizarCPF);
         
         stm.setString(1, getCpf());
         stm.setInt(2, getidUsuario());
         
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
   
   }
   public void AtualizarEmail(Connection conn){
      String sqlAtualizarEmail = "Update usuarios set email = ? where idusuario = ?";
      
      PreparedStatement stm = null;
      
      try{
         stm = conn. prepareStatement(sqlAtualizarEmail);
         
         stm.setString(1, getEmail());
         stm.setInt(2, getidUsuario());
         
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
   
   }
   public void AtualizarSenha(Connection conn){
      String sqlAtualizarSenha = "Update usuarios set senha = ? where idusuario = ?";
      
      PreparedStatement stm = null;
      
      try{
         stm = conn. prepareStatement(sqlAtualizarSenha);
         
         stm.setString(1, getSenha());
         stm.setInt(2, getidUsuario());
         
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
   
   }
   public void AtualizarEspecialidade(Connection conn){
      String sqlAtualizarCurso = "Update usuarios set idEspecialidade = ? WHERE idusuario = ?";
      
      PreparedStatement stm = null;
      
      try{
         stm = conn. prepareStatement(sqlAtualizarCurso);
         
         stm.setInt(1, getIdEspecialidade());
         stm.setInt(2, getidUsuario());
         
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
   
   }
   
   public void DoLogin(JFrame t){
   
      TelaMain home = new TelaMain();
      new ListaSugestoes();
      home.setVisible(true);
      t.setVisible(false);
   
   }
}

