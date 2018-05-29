package Conection;

import view.*;
import Style.*;
import Controler.*;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

/**

* Classe responsável pela conexão com banco de dados MySQL

*/



public class AcessoBD

{

// -----------------------------------------------------------

// Carrega driver JDBC

//

   static
   
   {
   
      try
      
      {
      
         Class.forName("com.mysql.jdbc.Driver");
      
      }
      
      catch (ClassNotFoundException e)
      
      {
      
         throw new RuntimeException(e);
      
      }
   
   }
// -----------------------------------------------------------

// Obtém conexão com o banco de dados

   public Connection obtemConexao() throws SQLException
   
   {
   
      return DriverManager.getConnection
         
         (
         
         "jdbc:mysql://localhost/sugestoes?user=root&password=manager   "
         
         );
   
   }

}