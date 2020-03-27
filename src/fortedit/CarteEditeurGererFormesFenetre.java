package fortedit;

import fortedit.carte.Elements;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CarteEditeurGererFormesFenetre
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  private JComboBox choix;
  private JTextField[] allFields;
  
  public CarteEditeurGererFormesFenetre(Editeur editeur)
  {
    this.editeur = editeur;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
	if(arg0.getActionCommand() == "Copier") {
		if(this.editeur.getImage().copyState == false && this.editeur.getImage().isAction == false) {
			this.editeur.setEtatCopie("<html><center>Cliquez à nouveau pour coller votre forme <br/> Clic droit pour annuler la copie</center></html>");
			String[] datas = arg0.getSource().toString().split(",");
			String data = datas[0].split("\\[")[1];
			System.out.println(data);
			CarteImage img = this.editeur.getImage();
			img.copyState = Boolean.valueOf(true);
			img.isCopied = Boolean.valueOf(true);
			img.isPasted = Boolean.valueOf(false);
			img.isAction = Boolean.valueOf(true);
			Graphics g = img.getGraphics();
	    	g.setColor(Color.RED);
		    img.nbrCopiedCases = 0;
		    img.nbrCopiedCasesX = 0;
		    img.nbrCopiedCasesY = 0;
		    
		    String[] blocs = data.split("-");
		    img.nbrCopiedCasesY = blocs.length;
		    System.out.println("Nombre de lignes = "+blocs.length);
		    for(int i=0;i<blocs.length;i++) {
		    	String[] blocks = blocs[i].split("");
		    	img.nbrCopiedCasesX = blocks.length;
		    	for(int j=0;j<blocks.length;j++) {
		    		for(int c=0; c<Elements.codes.length; c++) {
		    			if(blocks[j].charAt(0) == Elements.codes[c]) {
		    				img.copiedCases[j][i] = c;
		    				break;
		    			}
		    		}
		    		img.copiedCases[j][i] = Integer.parseInt(blocks[j]);
		    	}
		    }
			img.startCopieX = 0;
			img.startCopieY = 0;
			
			img.endCopieX = img.nbrCopiedCasesX-1;
			img.endCopieY = img.nbrCopiedCasesY-1;
		} else {
		  final JOptionPane pane = new JOptionPane("Vous avez déjà une forme de sélectionnée ");
		  final JDialog d = pane.createDialog((JFrame)null, "Erreur");
		  d.setAlwaysOnTop(true);
		  d.setLocation(200,200);
		  d.setVisible(true);
		}
	} else if(arg0.getActionCommand() == "Fermer") {
		this.dialogueFenetre.setVisible(false);
	} else if(arg0.getActionCommand() == "Retirer") {
		String[] datas = arg0.getSource().toString().split(",");
		String data = datas[0].split("\\[")[1];
		System.out.println("DATA = "+data);
		System.out.println("Pour le moment cela ne fonctionner pas");
	} else {
	    
	    JFrame f = new JFrame();
	    f.setSize(400,400);
	    f.setLocation(200,200);
	    f.setVisible(false);
	     
	    JPanel panel = new JPanel();
	    panel.setPreferredSize(new Dimension(500,400));
	    this.dialogueFenetre = new JDialog(f, "Gérer les formes enregistrées", false);
	    this.dialogueFenetre.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    this.dialogueFenetre.add(new JScrollPane(panel));
	    this.dialogueFenetre.setSize(600,500);
	    this.dialogueFenetre.setLocation(150, 150);
	    this.dialogueFenetre.setVisible(true);
	    this.dialogueFenetre.setAlwaysOnTop(true);
	    
	    StringBuilder sb = new StringBuilder();
	    try {
	    	File formesFile = new File("formes.txt");
	    	if(!formesFile.exists() && !formesFile.isDirectory()) {
	    		formesFile.createNewFile();
	    	}
		    InputStream is = new FileInputStream(formesFile);
		    InputStreamReader isr = new InputStreamReader(is);
		    BufferedReader br = new BufferedReader(isr);
		    String line = null, tmp;
		    
			while((tmp = br.readLine()) != null) {
				line = tmp;
				
			    String values[]  = line.split(";");
			    panel.add(new JLabel("  #"+values[2]+" "+values[0]));
			    
			    JButton copier = new JButton();
			    copier.setText("Copier");
			    copier.setName(values[1]);
			    copier.addActionListener(this);
			    copier.setEnabled(true);
			    panel.add(copier);
			    
			    JButton supprimer = new JButton();
			    supprimer.setText("Retirer");
			    supprimer.setName(values[2]);
			    supprimer.addActionListener(this);
			    supprimer.setEnabled(true);
			    //panel.add(supprimer);
			}
			br.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(sb.toString());
	    
	    JButton save = new JButton();
	    save.setText("Fermer");
	    save.setEnabled(true);
	    
	    save.addActionListener(this);
	    
	    panel.add(save, BorderLayout.CENTER);
	    
	    /*this.dialogue.add(subPanel);
	    
	    this.dialogueFenetre.add(this.dialogue);
	    this.dialogueFenetre.pack();
	    this.dialogueFenetre.setSize(400, 200);
	    this.dialogueFenetre.setResizable(true);
	    
	    this.dialogueFenetre.setVisible(true);
	    this.dialogueFenetre.setLocationRelativeTo(this.editeur);
	    this.dialogueFenetre.setAlwaysOnTop(true);*/
	}
  }
  
  public JComboBox getChoix()
  {
    return this.choix;
  }
}
