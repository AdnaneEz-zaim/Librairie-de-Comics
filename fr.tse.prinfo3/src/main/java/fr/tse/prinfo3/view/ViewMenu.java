package fr.tse.prinfo3.view;


import fr.tse.prinfo3.control.Controller;
import fr.tse.prinfo3.model.Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent; // Java8+

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewMenu extends Interface {
    private JLabel pageTitle = new JLabel("Derniers comics :"); 
    private JLabel lastComics = new JLabel(""); 
    private JLabel scrollBar = new JLabel(""); 


    private JButton profileJButton = new JButton();  

	public ViewMenu(Model modele, Controller controleur, int posX, int posY) {
        super(" Menu", modele, controleur, posX, posY);
        
        
        // creating and adding the panels to display the page
        JPanel title = new JPanel();
        JPanel comics = new JPanel();
        JPanel scrollbar = new JPanel();

        //page.add(title);
        page.add(comics);
        page.add(scrollbar);

        // set title and comics in the page "comics récents"
        // title.add(pageTitle);
        lastComics.setIcon(new ImageIcon("/Users/ProBook/Documents/Projet_info/Info3/comicsPage.png"));
        scrollBar.setIcon(new ImageIcon("/Users/ProBook/Documents/Projet_info/Info3/scrollbar.png"));
        comics.add(lastComics);
        scrollbar.add(scrollBar);
        
        // set the display right 
        page.setLayout(new BoxLayout(page, BoxLayout.X_AXIS));
        
        // set background color RGB(196,68,68)
        Color BackgroundColor = new Color(196, 68, 68); 
        comics.setBackground(BackgroundColor);
        scrollbar.setBackground(BackgroundColor);

        


        
        // display la page du menu sur le JFrame
        interfaceJFrame.add(page);
        interfaceJFrame.setVisible(true);

		
        
        //setDisplay(""+model.getC());
        /*addUpListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.augmenteDegresC();
            }});
        addDownListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.diminueDegresC();
            }});
        addDisplayListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double tempC = getDisplay();
                controller.fixeDegresC(tempC);
            }});
         */
    }

	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}