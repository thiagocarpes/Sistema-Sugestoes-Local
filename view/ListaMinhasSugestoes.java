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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListaMinhasSugestoes {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel coluns, sidebar1, sidebar2, line;
   private int registrosPorPagina = 5, pagina = 1;
   int countRow, filterCategorie = 0;

   public ListaMinhasSugestoes(){
      
      //padrões de posicionamento do formulario
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;      
            
      //bloco com as duas colunas
      coluns = new JPanel();
      coluns.setLayout(new GridBagLayout()); 
      coluns.setPreferredSize(new Dimension(1000, 550));           
      coluns.setOpaque(false);
      GridBagConstraints f = new GridBagConstraints();
      f.weighty = 0.1;
      f.weightx = 0.1;
      f.gridx = 0;
      f.gridy = 0;
      f.insets = new Insets(10, 20, 0, 0);
      f.anchor = GridBagConstraints.PAGE_START;
            
      //Categorias
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(0, 0, 0, 0);
      //inserindo combobox das categorias
      Categoria cat = new Categoria();      
      try { conn = bd.obtemConexao(); }catch(Exception e){ e.printStackTrace(); }           
      StyleComboBox categoria = new StyleComboBox(cat.getCategoriasModel(conn), 0, coluns, c);
      categoria.setPreferredSize(new Dimension(200, 33));
      
                  
      //Botão nova Sugestão
      c.anchor = GridBagConstraints.FIRST_LINE_END;
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(0, 0, 0, 10);
      StyleButtons btnNovaSugestao = new StyleButtons("+ NOVA SUGESTÃO", 14, "Green", coluns, "Cheio", c);
      btnNovaSugestao.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){
            new NovaSugestao(Usuario.idUsuario);
         }   
      });
      
      //coluna 1
      sidebar1 = new JPanel();
      sidebar1.setLayout(new GridBagLayout()); 
      sidebar1.setOpaque(false);
      sidebar1.setPreferredSize(new Dimension(990, 470));
      c.gridx = 0;
      c.gridy = 1;
      c.gridwidth = 2; //2 columns wide
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(0, 0, 0, 0);
      coluns.add(sidebar1, c);      
      
      ShowListSugestoes(sidebar1);
      
      TelaMain.setContent("Sugestões", "Sugestões", coluns, f);    
   }
   
   public void ShowListSugestoes(JPanel panel){
      
      //System.out.println("Pagina:" + getPagina());
      
      GridBagConstraints cc = new GridBagConstraints();
      cc.weighty = 0.1;
      cc.weightx = 0.1;
   
      try {
                     
         conn = bd.obtemConexao();
         conn.setAutoCommit(false);
         ResultSet rst = getSugestoes(conn, "");         
         setCountRow(0);
         
         if(getAllRegs(conn) > 0){
         
            while(rst.next()){
            
              //listando as sugestões
              line = new JPanel();
              int AlturadasLinhas = 70;//importante nessa posição
              line.setLayout(new GridBagLayout()); 
              line.setBackground(Color.decode("#fafafa"));
              String borderColor = (rst.getString("corEspecialidade") != null && rst.getString("corEspecialidade") != "") ? rst.getString("corEspecialidade") : "#f1f1f1";
              line.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, Color.decode(borderColor) ) );          
              //line.setOpaque(false);
              line.setPreferredSize(new Dimension(0, AlturadasLinhas));
              cc.anchor = GridBagConstraints.FIRST_LINE_START;           
              cc.fill = GridBagConstraints.HORIZONTAL;
              cc.gridx = 0;
              cc.gridy = 0;
              cc.insets = new Insets((getCountRow() * (AlturadasLinhas + 17)), 0, 0, 0);
              panel.add(line, cc);
              
              cc.gridx = 0;
              cc.gridy = 0;
              cc.fill = GridBagConstraints.NONE;
              cc.anchor = GridBagConstraints.FIRST_LINE_START;
              cc.insets = new Insets(10, 20, 0, 0);
              StyleLabels titulo = new StyleLabels(rst.getString("titulo"), "#444444", 18, line, cc);
              
              cc.anchor = GridBagConstraints.LINE_START;
              cc.insets = new Insets(5, 20, 0, 120);
              new StyleLabels(rst.getString("sugestao"), "#444444", 12, line, cc);
              
              cc.anchor = GridBagConstraints.LAST_LINE_START;
              cc.insets = new Insets(0, 20, 8, 0);
              SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
              new StyleLabels("- " + d.format(rst.getDate("Data")) + " | " + rst.getString("nomeEspecialidade"), "#bbbbbb", 12, line, cc);
              
              cc.anchor = GridBagConstraints.FIRST_LINE_END;
              cc.insets = new Insets(7, 0, 0, 10);
              String txtShowStatus = "";
              if(rst.getString("status").equals("ativo")){ borderColor = "#5cb85c"; txtShowStatus = "Publicada"; }
              if(rst.getString("status").equals("inativo")){ borderColor = "#999999"; txtShowStatus = "Aguardando Avaliação"; }
              if(rst.getString("status").equals("recusado")){ borderColor = "#d9534f"; txtShowStatus = "Reprovada"; }
              if(rst.getString("status").equals("recusado")){
               cc.insets = new Insets(7, 0, 0, 10);
               StyleButtons verFeedback = new StyleButtons(" |  Ver Feedback", 12, "Grey", line, "Vazio", cc);
               String getfeedbackshow = rst.getString("FeedBack");
               verFeedback.addActionListener(
                  new ActionListener(){
                     public void actionPerformed(ActionEvent e){
                        JOptionPane.showMessageDialog(null, getfeedbackshow); 
                     }
                  }      
               );
               cc.insets = new Insets(7, 0, 0, 100);
              } 
              StyleLabels status = new StyleLabels(txtShowStatus, borderColor, 12, line, cc);
              
              cc.anchor = GridBagConstraints.LAST_LINE_END;
              cc.insets = new Insets(0, 0, 7, 10);
              StyleButtons btnVerSugestão = new StyleButtons("Ver sugestão completa", 12, "Green", line, "Vazio", cc);
              int idSugestao = rst.getInt("idSugestao");          
              btnVerSugestão.addActionListener(
                  new ActionListener(){
                     public void actionPerformed(ActionEvent e){
                        VerSugestao VS = new VerSugestao(idSugestao); 
                     }
                  }      
              );            
              setCountRow(getCountRow() + 1);
            }
            
        } else {
        
           //listando as sugestões
           line = new JPanel();
           line.setLayout(new GridBagLayout()); 
           line.setBackground(Color.decode("#ffffff"));
           line.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.decode("#cccccc") ) );          
           line.setPreferredSize(new Dimension(0, 75));
           cc.anchor = GridBagConstraints.FIRST_LINE_START;           
           cc.fill = GridBagConstraints.HORIZONTAL;
           cc.gridx = 0;
           cc.gridy = 0;
           cc.insets = new Insets(0, 0, 0, 0);
           panel.add(line, cc);
           
           cc.gridx = 0;
           cc.gridy = 0;
           cc.fill = GridBagConstraints.NONE;
           cc.anchor = GridBagConstraints.FIRST_LINE_START;
           cc.insets = new Insets(10, 20, 0, 0);
           StyleLabels titulo = new StyleLabels("Ops! Nenhuma sugestão para sua pesquisa", "#999999", 24, line, cc);
           titulo.setCursor(new Cursor(Cursor.HAND_CURSOR));
           
           cc.anchor = GridBagConstraints.LINE_START;
           cc.insets = new Insets(20, 20, 0, 0);
           new StyleLabels("Não encontramos nenhum resultado, tente novamente mais tarde", "#cccccc", 14, line, cc);
        
        }
                  
      }catch(Exception e){
         e.printStackTrace();
      }
      
      //System.out.println("Row:" + getCountRow());
      
      //paginção
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setBackground(Color.decode("#ffffff"));
      line.setPreferredSize(new Dimension(0, 45));
      cc.anchor = GridBagConstraints.PAGE_END;
      cc.fill = GridBagConstraints.HORIZONTAL;           
      cc.gridx = 0;
      cc.gridy = getCountRow();
      cc.insets = new Insets(0, 0, 0, 0);
      panel.add(line, cc);

      //botão pagina anterior
      cc.anchor = GridBagConstraints.CENTER;
      cc.fill = GridBagConstraints.NONE;      
      cc.gridx = 0;
      cc.gridy = 0;
      cc.insets = new Insets(0, 0, 0, 125);
      StyleButtons anteriorPagina = new StyleButtons("", 16, "Grey", line, "Vazio", cc);
      if(getPagina() != 1){ anteriorPagina.setText("<<"); }
      
      anteriorPagina.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               setPagina(getPagina() - 1);
               panel.repaint();
               panel.removeAll();
               if(getPagina() != 1){ anteriorPagina.setText("<<"); }
               System.out.println(getPagina());
               cc.gridx = 0;
               cc.gridy = 1;
               cc.anchor = GridBagConstraints.FIRST_LINE_START;
               cc.insets = new Insets(0, 0, 0, 0);
               ShowListSugestoes(panel);
               panel.revalidate(); 
            }
         }
      );
      
      //infos de paginas
      cc.anchor = GridBagConstraints.CENTER;
      cc.gridx = 0;
      cc.gridy = 0;
      cc.insets = new Insets(0, 0, 0, 0);
      new StyleLabels("Página " + getPagina()+" de "+getNumberPages(conn), "#444444", 14, line, cc);

      //botão proxima pagina
      cc.gridx = 0;
      cc.gridy = 0;
      cc.insets = new Insets(0, 125, 0, 0);
      StyleButtons proximaPagina = new StyleButtons("", 16, "Grey", line, "Vazio", cc);
      if(getPagina() < getNumberPages(conn)){ proximaPagina.setText(">>"); }
      
      proximaPagina.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               setPagina(getPagina() + 1);
               panel.repaint();
               panel.removeAll();
               if(getPagina() > getNumberPages(conn)){ proximaPagina.setText(""); }
               System.out.println(getPagina());
               cc.gridx = 0;
               cc.gridy = 1;
               cc.anchor = GridBagConstraints.FIRST_LINE_START;
               cc.insets = new Insets(0, 0, 0, 0);
               ShowListSugestoes(panel);
               panel.revalidate(); 
            }
         }
      );
      
   }
   
   public ResultSet getSugestoes(Connection conn, String w){      
     
      int registrosInicio = (getPagina() == 1) ? 0 : ((getPagina() - 1) * registrosPorPagina);
      String where = (getFilterCategorie() != 0) ? " AND s.Especialidade = "+getFilterCategorie() : "";
      
      String query = "SELECT s.*, e.corEspecialidade, e.nomeEspecialidade FROM sugestao s INNER JOIN especialidade e ON s.Especialidade=e.idEspecialidade WHERE s.Colaborador="+Usuario.idUsuario+" "+where+w+" Limit " + registrosInicio  + "," + registrosPorPagina;   
      System.out.println(query);
      PreparedStatement stm = null;
      ResultSet rs = null;
      
      try {
         stm = conn.prepareStatement(query);
         rs = stm.executeQuery();
      }    
      catch(Exception e){
          e.printStackTrace();
      }
      
      return rs;
      
   }
      
   public int getNumberPages(Connection conn){
      int NumberPages = 1;
      try {
         String where = (getFilterCategorie() != 0) ? " AND Especialidade = "+getFilterCategorie() : "";
         String query = "select count(*) from sugestao WHERE Colaborador="+Usuario.idUsuario+" "+where;
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
           NumberPages = ( rs.getInt(1) + (registrosPorPagina - 1) ) / registrosPorPagina;
         }      
      }catch(Exception e){
         e.printStackTrace();
      }finally {
         return NumberPages;
      }
      
   }
   
   public int getAllRegs(Connection conn){
      int NumberRegs = 0;
      try {
         String where = (getFilterCategorie() != 0) ? " AND Especialidade = "+getFilterCategorie() : "";
         String query = "select count(*) from sugestao WHERE Colaborador="+Usuario.idUsuario+" "+where;
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
           NumberRegs = rs.getInt(1);
         }      
      }catch(Exception e){
         e.printStackTrace();
      }finally {
         return NumberRegs;
      }
      
   }
   
   public void setPagina(int p){
      pagina = p;
   }
   
   public int getPagina(){
      return pagina;
   }
      
   public void setCountRow(int p){
      countRow = p;
   }
   
   public int getCountRow(){
      return countRow;
   }
   
   public void setFilterCategorie(int p){
      filterCategorie = p;
   }
   
   public int getFilterCategorie(){
      return filterCategorie;
   }
      
}