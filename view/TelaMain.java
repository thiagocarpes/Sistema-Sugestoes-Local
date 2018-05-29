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

public class TelaMain extends JFrame {

   private JPanel barraTop, header, nav, barraRodape;
   private static JPanel content;
   private static JLabel titlePage, headerTitlePage;
   Container container;
   private int MainWidth = 1024;
   static Point mouseDownCompCoords;
   StyleButtons button1, sair, minimize;
   StyleLabels tituloRodape1, tituloRodape2;
   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   
   
   public TelaMain(){
      
      setLayout(new GridBagLayout());
      setSize(MainWidth, 768);
      getContentPane().setBackground(Color.decode("#ffffff"));
      setLocationRelativeTo(null);
      setResizable(false);
      setUndecorated(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode("#f1f1f1")));
      setLocationByPlatform( false );
      
      GridBagConstraints mc = new GridBagConstraints();
      mc.fill = GridBagConstraints.HORIZONTAL;
      mc.gridx = GridBagConstraints.REMAINDER;
      mc.anchor = GridBagConstraints.PAGE_START;
      mc.weighty = 0.1;
      mc.weightx = 0.1;
 
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;
      
      barraTop = new JPanel();
      barraTop.setLayout(new GridBagLayout());
      barraTop.setPreferredSize(new Dimension(0, 30));
      barraTop.setBackground(Color.decode("#f1f1f1"));
      add(barraTop, mc);      
                  
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
      c.insets = new Insets(7, 0, 0, 10);
      sair = new StyleButtons("SAIR" , 11, "Red", barraTop, "Vazio", c);
      //ação do botão sair
      sair.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){
            setVisible(false);
            TelaLogin tela = new TelaLogin();      
            tela.setVisible(true);
         }   
      });
      
      //posicionamento botao minimizar
      c.anchor = GridBagConstraints.FIRST_LINE_END;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(5, 0, 0, 50);
      minimize = new StyleButtons("__" , 11, "Grey", barraTop, "Vazio", c);
      //ação do botão sair
      minimize.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){
            setState(Frame.ICONIFIED);
         }   
      });
     
      //posicionamento do titulo da tela
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(7, 10, 0, 0);
      titlePage = new JLabel();
      titlePage.setFont(new Font("Arial", Font.PLAIN, 12));
      titlePage.setForeground(Color.decode("#444444"));
      titlePage.setEnabled(true);      
      barraTop.add(titlePage, c);
      
      String nameshow;
      if(Usuario.getTipoUsuario() == 2){
         nameshow = "Avaliador";
      }else if(Usuario.getTipoUsuario() == 3){
         nameshow = "Administrador";
      }else{
         nameshow = "Colaborador";
      }      
      titlePage.setText("Bem vindo " + Usuario.getPublicNome() + ", ["+nameshow+"]");      
 
      header = new JPanel();
      header.setLayout(new GridBagLayout());
      header.setPreferredSize(new Dimension(0, 100));
      header.setBackground(Color.decode("#ffffff"));
      add(header, mc);
      
      JLabel logo = new JLabel();      
      ImageIcon imageIcon = new ImageIcon("view/images/logo2.jpg");
      Image image = imageIcon.getImage(); // transform it
      Image newimg = image.getScaledInstance(147, 48,  java.awt.Image.SCALE_SMOOTH);
      logo.setIcon(new ImageIcon(newimg));
      logo.setPreferredSize(new Dimension(147, 48));
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_END;
      c.insets = new Insets(25, 0, 0, 25);
      header.add(logo, c);
      
      ImageIcon img = new ImageIcon("view/images/logo.png");
      setIconImage(img.getImage());           
      
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(25, 25, 0, 0);
      headerTitlePage = new JLabel();
      headerTitlePage.setFont(new Font("Arial", Font.PLAIN, 34));
      headerTitlePage.setForeground(Color.decode("#444444"));
      headerTitlePage.setEnabled(true);      
      header.add(headerTitlePage, c);
      
      nav = new JPanel();
      nav.setLayout(new FlowLayout(FlowLayout.LEFT, 18, 7));
      nav.setPreferredSize(new Dimension(0, 36));
      nav.setBackground(Color.decode("#f1f1f1"));
      nav.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.decode("#999999")));
      add(nav, mc);
      
      StyleButtons btn1 = new StyleButtons("SUGESTÕES", 14, "Grey", nav, "Vazio", c);
      nav.add(btn1, c);
      btn1.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){            
            new ListaSugestoes();
         }   
      });
            
      StyleButtons btn2 = new StyleButtons("MEUS DADOS", 14, "Grey", nav, "Vazio", c);
      btn2.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){            
            new EditarColaborador(Usuario.idUsuario);
            
         }   
      });
      if(Usuario.getTipoUsuario() == 2){
         StyleButtons btn3 = new StyleButtons("AVALIAR SUGESTÕES", 14, "Grey", nav, "Vazio", c);
         btn3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){            
               new ListaAvaliaSugestoes();               
            }   
         });
         
         StyleButtons btn5 = new StyleButtons("RANKING", 14, "Grey", nav, "Vazio", c);
         btn5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               new ListaRelatorios();
            }
         });
         
         StyleButtons btn10 = new StyleButtons("ÍNDICE DE APROVAÇÃO", 14, "Grey", nav, "Vazio", c);
         btn10.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               new ListaRelatoriosIndice();
            }
         });         
         
         
      }
      
      if(Usuario.getTipoUsuario() == 1){
         StyleButtons btn3 = new StyleButtons("MINHAS SUGESTÕES", 14, "Grey", nav, "Vazio", c);
         btn3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){            
               new ListaMinhasSugestoes();
               
            }   
         });   
      }
            
      StyleButtons btn4 = new StyleButtons("ALGUMA DUVIDA?", 14, "Grey", nav, "Vazio", c);
      btn4.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent ae){            
            new Duvida();
            
         }   
      });
         
      if(Usuario.getTipoUsuario() == 3){
         StyleButtons btn6 = new StyleButtons("AVALIADOR", 14, "Grey", nav, "Vazio", c);      
         btn6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               new TelaListaAvaliador();
            }   
         });
         
         StyleButtons btn7 = new StyleButtons("CATEGORIAS", 14, "Grey", nav, "Vazio", c);      
         btn7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               new TelaListaCategorias();         
            }   
         });
         StyleButtons btn8 = new StyleButtons("PARTICIPAÇÃO", 14, "Grey", nav, "Vazio", c);      
         btn8.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               new ListaParColaborador();         
            }   
         });
         
         StyleButtons btn9 = new StyleButtons("SUGESTÕES APROVADAS", 14, "Grey", nav, "Vazio", c);
         btn9.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               new ListaAprovadosCatg();
            }
         });
         
         StyleButtons btn11 = new StyleButtons("ÍNDICE GERAL", 14, "Grey", nav, "Vazio", c);
         btn11.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               new ListaRelatoriosMaster();
            }
         });                   
       }
      
      
      content = new JPanel();
      content.setLayout(new GridBagLayout());
      content.setPreferredSize(new Dimension(0, 574));
      content.setBackground(Color.decode("#ffffff"));
      add(content, mc);
      
 
      /*       
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(35, 10, 0, 20);
      new StyleTextArea("aver maria", 300, 200, content, c);
      
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(5, 10, 0, 20);
      String[] options = new String[]{"Opção1", "Opção2", "Opção3"};
      new StyleComboBox(options, 2, content, c);
      */
      
      barraRodape = new JPanel();
      barraRodape.setLayout(new GridBagLayout());
      barraRodape.setSize(MainWidth, 40);
      barraRodape.setOpaque(false);
      mc.anchor = GridBagConstraints.PAGE_END;
      add(barraRodape, mc);
            
            
      //posicionamento do tituloRodape1
      c.anchor = GridBagConstraints.LAST_LINE_START;      
      c.gridx = 0;
      c.gridy = 2;
      c.insets = new Insets(0, 10, 10, 0);
      tituloRodape1 = new StyleLabels("S&S Software e Soluções", "#1b3280", 12, barraRodape, c);      
      
      //posicionamento do tituloRodape2
      c.anchor = GridBagConstraints.LAST_LINE_END;      
      c.gridx = 0;
      c.gridy = 2;
      c.insets = new Insets(0, 0, 10, 10);
      tituloRodape2 = new StyleLabels("2016 Todos os direitos reservados", "#1b3280", 12, barraRodape, c);
     
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
   
  
   public static void setContent(String tp, String thp, JPanel pane, GridBagConstraints c){      
      headerTitlePage.setText(thp);
      
      content.repaint();
      content.removeAll();
      content.add(pane, c);
      content.revalidate();
   } 
  
 
}