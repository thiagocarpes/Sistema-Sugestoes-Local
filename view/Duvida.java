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

public class Duvida{
   
   Connection conn = null;
   JPanel form1;
   JPanel coluns, sidebar1, sidebar2, line;
   String aux = "";
   
   public Duvida(){
   
      
      GridBagConstraints c = new GridBagConstraints();
      c.weighty = 0.1;
      c.weightx = 0.1;     
       
      sidebar1 = new JPanel();
      sidebar1.setLayout(new GridBagLayout());           
      sidebar1.setOpaque(false);
      sidebar1.setPreferredSize(new Dimension(710, 470));
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(0, 0, 0, 0); 
      TelaMain.setContent("Aluma duvida?", "Alguma Dúvida?", sidebar1, c); 

      //Primeira linha
      c.fill = GridBagConstraints.HORIZONTAL;
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      line.setPreferredSize(new Dimension(0, 200));
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.gridx = 0;
      c.gridy = 1;
      c.insets = new Insets(0, 0, 0, 0);
      sidebar1.add(line, c);

      c.fill = GridBagConstraints.NONE;
      c.gridx = 0;
      c.gridy = 0;      
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(20, 20, 0, 0);
      StyleLabels titulo = new StyleLabels(("Como cadastrar uma nova sugestão"), "#444444", 18, line, c);
     
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(50, 20, 0, 0);
      StyleTextArea desc = new StyleTextArea("Na barra de opções, selecione a opção (SUGESTÕES), clique no botão (+ NOVA SUGESTÃO) que se localiza no campo superior direito. ", 500, 90, line, c);
            desc.setForeground(Color.decode("#444444"));
            desc.setEditable(false);
            desc.setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#ffffff")), 
                  BorderFactory.createEmptyBorder(0, 0, 0, 0)));
                  
      //Segunda linha
      c.fill = GridBagConstraints.HORIZONTAL;
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      line.setPreferredSize(new Dimension(0, 200));
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.gridx = 0;
      c.gridy = 2;
      c.insets = new Insets(0, 0, 0, 0);
      sidebar1.add(line, c);

      c.fill = GridBagConstraints.NONE;
      c.gridx = 0;
      c.gridy = 0;      
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(20, 20, 0, 0);
      StyleLabels titulo2 = new StyleLabels(("Como alterar os dados cadastrados"), "#444444", 18, line, c);
     
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(50, 20, 0, 0);
      StyleTextArea desc2 = new StyleTextArea("Na barra de opções, clique na opção (MEUS DADOS).\nOBS: Os dados devem ser preenchidos corretamente para poder realizar as alterações necessárias. ", 500, 90, line, c);
            desc2.setForeground(Color.decode("#444444"));
            desc2.setEditable(false);
            desc2.setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#ffffff")), 
                  BorderFactory.createEmptyBorder(0, 0, 0, 0)));
  
      //Terceira linha
      c.fill = GridBagConstraints.HORIZONTAL;
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      line.setPreferredSize(new Dimension(0, 200));
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.gridx = 0;
      c.gridy = 3;
      c.insets = new Insets(0, 0, 0, 0);
      sidebar1.add(line, c);

      c.fill = GridBagConstraints.NONE;
      c.gridx = 0;
      c.gridy = 0;      
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(20, 20, 0, 0);
      StyleLabels titulo3 = new StyleLabels(("Como comentar uma sugestão"), "#444444", 18, line, c);
     
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(50, 20, 0, 0);
      StyleTextArea desc3 = new StyleTextArea("No mural de sugestões, clique em (Ver sugestão completa) que está localizada do lado direirto da sugestão desejada, assim você poderá ver a sugestão completa e comenta-la, assim que salva-la, ela aparecera no lado direito da tela com o nome do de quem fez o comentário", 500, 90, line, c);
            desc3.setForeground(Color.decode("#444444"));
            desc3.setEditable(false);
            desc3.setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#ffffff")), 
                  BorderFactory.createEmptyBorder(0, 0, 0, 0)));
                  
      //Quarta linha
      c.fill = GridBagConstraints.HORIZONTAL;
      line = new JPanel();
      line.setLayout(new GridBagLayout()); 
      line.setOpaque(false);
      line.setPreferredSize(new Dimension(0, 200));
      c.anchor = GridBagConstraints.FIRST_LINE_START;           
      c.gridx = 0;
      c.gridy = 4;
      c.insets = new Insets(0, 0, 0, 0);
      sidebar1.add(line, c);

      c.fill = GridBagConstraints.NONE;
      c.gridx = 0;
      c.gridy = 0;      
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(20, 20, 0, 0);
      StyleLabels titulo4 = new StyleLabels((""), "#444444", 18, line, c);
     
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(50, 20, 0, 0);
      StyleTextArea desc4 = new StyleTextArea("", 500, 90, line, c);
            desc4.setForeground(Color.decode("#444444"));
            desc4.setEditable(false);
            desc4.setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#ffffff")), 
                  BorderFactory.createEmptyBorder(0, 0, 0, 0)));

                   
   }

}