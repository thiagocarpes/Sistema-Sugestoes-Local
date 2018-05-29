package view;

import Conection.*;
import Style.*;
import Controler.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

import java.io.File;
import javax.imageio.ImageIO;

import javax.swing.border.*;

public class TelaLogin extends JFrame {

   JFrame TelaAtual = this;
   private JPanel barraTop, contentForms, form1, form2, line;
   Container container;
   private int MainWidth = 800;
   static Point mouseDownCompCoords;
   StyleButtons sair;
   StyleLabels titulo1, titulo2, tituloRodape1, tituloRodape2, txtCadastreSe, txtCadastreSeDesc, txtLogin, txtLoginDesc;
   StyleFields nomeCompleto, email, cpf;
   StylePasswordFields verificaSenha, senha, confirmeSenha;
   Connection conn = null;
   AcessoBD bd = new AcessoBD();

   public TelaLogin(){
   
      setTitle("Tela Padrão"); 
      setLayout(new GridBagLayout());
      setSize(MainWidth, 600);
      setContentPane(new JLabel(new ImageIcon("view/images/bg.png")));
      setLocationRelativeTo(null);
      setResizable(false);
      setUndecorated(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#f1f1f1")));
      ImageIcon img = new ImageIcon("view/images/logo.png");
      setIconImage(img.getImage());
      
      //limitações para os os blocos da tela (titulos, formularios, botão dair etc..)
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;
      
      //limitações para os campos do formulario de login e cadastro
      GridBagConstraints f = new GridBagConstraints();
      f.fill = GridBagConstraints.HORIZONTAL;
      f.gridx = GridBagConstraints.REMAINDER;
      
      barraTop = new JPanel();
      barraTop.setLayout(new GridBagLayout());
      barraTop.setSize(MainWidth, 600);
      barraTop.setOpaque(false);
      //barraTop.setBackground(Color.decode("#444444"));
      add(barraTop);      
                  
      mouseDownCompCoords = null;
      barraTop.addMouseListener(new MouseListener(){
         public void mouseReleased(MouseEvent e) {
             mouseDownCompCoords = null;
         }
         public void mousePressed(MouseEvent e) {
             mouseDownCompCoords = e.getPoint();
         }
         public void mouseExited(MouseEvent e) {
         }
         public void mouseEntered(MouseEvent e) {
         }
         public void mouseClicked(MouseEvent e) {
         }
      });

      barraTop.addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
      });
      
      //posicionamento botao sair
      c.anchor = GridBagConstraints.FIRST_LINE_END;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(5, 0, 0, 15);
      sair = new StyleButtons("SAIR" , 11, "Red", barraTop, "Vazio", c);
      //ação do botão sair
      sair.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){
            System.exit(0);
         }   
      });
               
      //posicionamento do titulo1
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 30, 0, 0);
      titulo1 = new StyleLabels("Faça sua sugestão", "#444444", 36, barraTop, c);      
      
      //posicionamento do titulo2
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(63, 32, 0, 0);
      titulo2 = new StyleLabels("Nos ajude a melhorar, sua opinião é muito importante para nós", "#444444", 14, barraTop, c);
 
 
      
            
      //bloco do formulario de cadastro
      form1 = new JPanel();
      form1.setLayout(new GridBagLayout());
      form1.setOpaque(false);
      form1.setPreferredSize(new Dimension(320, 300));
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 1;
      c.insets = new Insets(85, 32, 30, 0);
      barraTop.add(form1, c);
      
      
      new StyleLabels("Cadastre-se", "#444444", 22, form1, f);
      f.insets = new Insets(0, 0, 20, 0);
      new StyleLabels("Preencha os campos abaixo para fazer sua sugestão", "#444444", 12, form1, f);      
    
      f.insets = new Insets(0, 0, 10, 0);
      nomeCompleto = new StyleFields("Nome Completo", form1, f);
      email = new StyleFields("Email", form1, f);
      cpf = new StyleFields("Cpf", form1, f);
      senha = new StylePasswordFields("Senha", form1, f);
      confirmeSenha = new StylePasswordFields("Confirme Senha", form1, f);
      
      StyleButtons cad = new StyleButtons("CADASTRAR" , 14, "Blue", form1, "Cheio", f);
      cad.setMaximumSize(new Dimension(Integer.MAX_VALUE, cad.getMinimumSize().height));
      
      cad.addActionListener(
      
         new ActionListener(){
            public void actionPerformed(ActionEvent event){
               if(event.getSource() == cad){
               
                  if( nomeCompleto.getText().equals("Nome Completo")                       || nomeCompleto.getText().equals("") ||
                      email.getText().equals("Email")                                      || email.getText().equals("")        ||
                      cpf.getText().equals("Cpf")                                          || cpf.getText().equals("")          ||
                      String.valueOf(senha.getPassword()).equals("Senha")                  || String.valueOf(senha.getPassword()).equals("") ||
                      String.valueOf(confirmeSenha.getPassword()).equals("Confirme Senha") || String.valueOf(confirmeSenha.getPassword()).equals(""))
                  {
                     JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
                  }else if(!String.valueOf(senha.getPassword()).equals(String.valueOf(confirmeSenha.getPassword()))){
                     JOptionPane.showMessageDialog(null, "As senhas não são iguais.");
                  }else{
                     try{
                     Usuario u = new Usuario(nomeCompleto.getText(), cpf.getText(), email.getText(), String.valueOf(senha.getPassword()), "", 0);
                     
                        conn = bd.obtemConexao();
                        conn.setAutoCommit(false);
                        u.IncluirColaborador(conn);
                        conn.commit();
                        JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
                        u.Login(conn, email.getText(), String.valueOf(senha.getPassword()), TelaAtual);
                        //u.DoLogin(TelaAtual);
                        
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
                     finally{
                        if(conn != null){
                           try{
                              conn.close();
                           }
                           catch(SQLException e1){
                              System.out.print(e1.getStackTrace());
                           }
                        }
                     }
                  }
               }
            }
         }
      );
      
      
      //Linha de divisao cinza
      line = new JPanel();
      line.setLayout(new GridBagLayout());
      line.setOpaque(false);
      line.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode("#cccccc") ) );
      c.anchor = GridBagConstraints.PAGE_START;      
      c.gridx = 0;
      c.gridy = 1;
      c.ipady = 380;
      c.insets = new Insets(40, 30, 0, 0);
      barraTop.add(line, c);
            
      //bloco do formulario de Login
      form2 = new JPanel();
      form2.setLayout(new GridBagLayout());            
      form2.setOpaque(false);
      form2.setPreferredSize(new Dimension(320, 220));
      c.anchor = GridBagConstraints.FIRST_LINE_END;      
      c.gridx = 0;
      c.gridy = 1;
      c.ipady = 0;
      c.insets = new Insets(70, 0, 0, 32);
      barraTop.add(form2, c);
      
      StyleFields usuario;
      
      f.insets = new Insets(0, 0, 0, 0);
      new StyleLabels("Login", "#444444", 22, form2, f);
      f.insets = new Insets(0, 0, 20, 0);
      new StyleLabels("Entre com usuário e senha para ver suas sugestões", "#444444", 12, form2, f);      
      
      f.insets = new Insets(0, 0, 10, 0);
      usuario = new StyleFields("adm@adm.com", form2, f);
      verificaSenha = new StylePasswordFields("1", form2, f);
      
      
      StyleButtons login = new StyleButtons("LOGIN" , 14, "Green", form2, "Cheio", f);
      login.setMaximumSize(new Dimension(Integer.MAX_VALUE, login.getMinimumSize().height));
     

      login.addActionListener(
      
         new ActionListener(){
            public void actionPerformed(ActionEvent event){
               if(event.getSource() == login){
                  if( usuario.getText().equals("") || usuario.getText().equals("Email") || verificaSenha.getPassword().equals("") || verificaSenha.getPassword().equals("Senha")){
                     JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
                  }
                  else{
                     try{
                     Usuario u = new Usuario();   
                     
                     conn = bd.obtemConexao();
                     conn.setAutoCommit(false);
                     u.Login(conn, usuario.getText(), String.valueOf(verificaSenha.getPassword()), TelaAtual);
                     
                     conn.commit();
                    
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
                     finally{
                        if(conn != null){
                           try{
                              conn.close();
                           }
                           catch(SQLException e1){
                              System.out.print(e1.getStackTrace());
                           }
                        }
                     }
                  }
               }
            }
         }
      );
      
     //posicionamento do tituloRodape1
      c.anchor = GridBagConstraints.LAST_LINE_START;      
      c.gridx = 0;
      c.gridy = 2;
      c.insets = new Insets(0, 10, 10, 0);
      tituloRodape1 = new StyleLabels("S&S Software e Soluções", "#1b3280", 12, barraTop, c);      
      
      //posicionamento do tituloRodape2
      c.anchor = GridBagConstraints.LAST_LINE_END;      
      c.gridx = 0;
      c.gridy = 2;
      c.insets = new Insets(0, 0, 10, 10);
      tituloRodape2 = new StyleLabels("2016 Todos os direitos reservados", "#1b3280", 12, barraTop, c);
     
     // sair.addMouseListener(new MouseAdapter() {

           // @Override
           // public void mouseEntered(MouseEvent me) {
           //     sair.setBackground(Color.decode("#ffffff"));
           // }

           //@Override
           // public void mouseExited(MouseEvent me) {
           //     sair.setBackground(Color.decode("#ffffff"));
           // }

      //});
      

   }
   
   public void closeLogin(){
      setVisible(false);
   } 
 
}