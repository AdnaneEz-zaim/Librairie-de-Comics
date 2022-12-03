package fr.tse.prinfo3.view;

// import fr.tse.prinfo3.ControllerClass;
// import fr.tse.prinfo3.ModelClass;

import java.beans.PropertyChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import fr.tse.prinfo3.control.Controller;
import fr.tse.prinfo3.model.Model;

public abstract class Interface implements PropertyChangeListener {
	// private String label
	protected Model model;
	protected Controller controller;
	
	protected JFrame interfaceJFrame;
	private JLabel tseUniverse = new JLabel("");
    private JLabel profilePic = new JLabel("");
    private JTextField searchField = new JTextField(30);
    
    
    protected JPanel surface = new JPanel();
    private JPanel separation = new JPanel();
    protected JPanel page = new JPanel();

    // text areas
    
    Interface(String label, Model model,  Controller controller, int posX, int posY) {
        // this.label = label;
        this.model = model;
        this.controller = controller;
    	interfaceJFrame = new JFrame(label);
    	// interfaceJFrame.setIconImage(null);
    	interfaceJFrame.add(new JLabel(label), BorderLayout.NORTH);
        
    	// Create Panels which belong to the interface
        JPanel logo = new JPanel();
        JPanel profile = new JPanel();
        JPanel search = new JPanel();


        // set logo into surface 
        tseUniverse.setIcon(new ImageIcon("/Users/ProBook/Documents/Projet_info/Info3/tseUniverse.png"));
        logo.add(tseUniverse);
        surface.add(logo);
        // set search bar 
        searchField.setLayout(new BorderLayout());
        search.add(searchField);
        surface.add(search);
        // set profile into surface 
        profilePic.setIcon(new ImageIcon("/Users/ProBook/Documents/Projet_info/Info3/profilePic.png"));
        profile.add(profilePic);
        surface.add(profile);
        
        // set the spacing 
        surface.setLayout(new BoxLayout(surface, BoxLayout.X_AXIS));
        // search.setLayout(new BoxLayout(search, BoxLayout.X_AXIS));
        //profile.setLayout(new BoxLayout(profile, BoxLayout.X_AXIS));

        // Box.createVerticalGlue();
        searchField.setAlignmentY(search.BOTTOM_ALIGNMENT);
        profilePic.setAlignmentY(profile.CENTER_ALIGNMENT);

        // set background color RGB(196,68,68)
        Color BackgroundColor = new Color(196, 68, 68); 
        profile.setBackground(BackgroundColor);
        search.setBackground(BackgroundColor);
        logo.setBackground(BackgroundColor);

        interfaceJFrame.add(surface, BorderLayout.NORTH);
        interfaceJFrame.add(separation, BorderLayout.CENTER);
        interfaceJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        //model.addObserver(this);         // preJava8 // Connexion entre la vue et le modele
        model.addPropertyChangeListener(this); // Java8+   // Connexion entre la vue et le modele
        interfaceJFrame.setSize(1200, 600);
        interfaceJFrame.setLocation (posX, posY);
        interfaceJFrame.setVisible(true);
    }
    
    public JPanel getPage() { return page; }
}
