package Controler;

import view.*;
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


public class NovaSugest�o {
   

   Connection conn = null;
   AcessoBD bd = new AcessoBD();
   JPanel form1;

   public NovaSugest�o(int idCola){
   
      
      //padr�es de posicionamento do formulario de edi��o do colaborador
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;      
      
      //padroes de posicionamento para os campos dentro do form1
      GridBagConstraints f = new GridBagConstraints();
      f.fill = GridBagConstraints.HORIZONTAL;
      f.gridx = GridBagConstraints.REMAINDER;
      f.weighty = 0.1;
      f.weightx = 0.1;
            
      //bloco do formulario de altera��o
      form1 = new JPanel();
      form1.setLayout(new GridBagLayout()); 
      form1.setBackground(Color.decode("#f9f9f9"));           
      //form1.setOpaque(false);
      form1.setPreferredSize(new Dimension(800, 500));      
      c.anchor = GridBagConstraints.FIRST_LINE_START;      
      c.gridx = 0;
      c.gridy = 0;
      c.insets = new Insets(20, 20, 0, 0);
      TelaMain.setContent(form1, c);      
      
      //inserindo t�tulo do formulario
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels txt1 = new StyleLabels("Qual a sua nova sugest�o?", "#444444", 16, form1, f);
      
      //inserindo campo Categoria
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lNomeColaborador = new StyleLabels("Categoria:", "#000000", 14, form1, f);
      String[] options = new String[]{"Op��o1", "Op��o2", "Op��o3"};
      new StyleComboBox(options, 2, form1, f);

      
      //inserindo campo T�tulo
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lCpfColaborador = new StyleLabels("T�tulo", "#000000", 14, form1, f);
      StyleFields CpfColaborador = new StyleFields("T�tulo", form1, f);
      
      //inserindo campo Sugest�o
      f.insets = new Insets(0, 0, 0, 0);
      StyleLabels lEmailColaborador = new StyleLabels("Sugest�o:", "#000000", 14, form1, f);
      new StyleTextArea("aver maria", 300, 200, form1, f);
      
      //inserindo bot�o salvar
      f.insets = new Insets(0, 0, 0, 0);
      StyleButtons salvar = new StyleButtons("SALVAR" , 14, "Green", form1, "Cheio", f);
                    
  }


}