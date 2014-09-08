package edu.cmu.cs.cs214.hw4.gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import edu.cmu.cs.cs214.hw4.core.*;
import java.util.*;
public class WelcomeFrame extends JFrame{
	
	/**
	 * Creates the welcome frame
	 * @param message
	 */
	public WelcomeFrame(String message)
	{
		super(message);
		
		setLayout(new GridLayout(3,1));
		JLabel label = new JLabel ("Please enter number of players");
        JButton button = new JButton("Enter");
        JPanel PlayerPanel = new JPanel();
        JPanel namePanel = new JPanel();
        final JTextField name1 = new JTextField();
        final JTextField name2 = new JTextField();
        final JTextField name3 = new JTextField();
        final JTextField name4 = new JTextField();
        
        name1.setText("");
        name2.setText("");
        name3.setText("");
        name4.setText("");
        
        namePanel.setLayout(new GridLayout(4,1));
        namePanel.add(name1);
        namePanel.add(name2);
        namePanel.add(name3);
        namePanel.add(name4);

        SpinnerModel monthModel = new SpinnerNumberModel(2,2,4,1);
        final JSpinner spinner = new JSpinner(monthModel);
        
        Font font2 = new Font("SansSerif", Font.PLAIN, 13);
        label.setFont(font2);
        
        PlayerPanel.add(button); 
        PlayerPanel.add(spinner);
        
        JPanel nextP = new JPanel();
        nextP.add(label);
        
        this.add(nextP);
   

        this.add(PlayerPanel, BorderLayout.CENTER);
        
        this.add(namePanel);
        
        this.setVisible(false);
        pack();
        button.addActionListener(new ActionListener()
        {
        	 public void actionPerformed(ActionEvent e) {
        		 makeUp((Integer) spinner.getValue(), name1.getText(), name2.getText(), name3.getText(), name4.getText());
        		
        	 }
        });
  
        
        
      
	}
	
	/**
	 * Gets all the appropriate values and sets them accordingly
	 * @param val number of players
	 * @param name1 name of player 1
	 * @param name2 name of player 2
	 * @param name3 name of player 3
	 * @param name4 name of player 4
	 */
	public void makeUp(int val, String name1, String name2, String name3, String name4)
	{
		ArrayList<String> players = new ArrayList<String>();
		String[] res;
			players.add(name1);
			players.add(name2);
			players.add(name3);
			players.add(name4);
		
		res = new String[players.size()];
		for (int i = 0; i < res.length; i++)
		{
			res[i] = players.get(i);
		}
		
		this.setVisible(false);
		 SecondFrame frame = new SecondFrame("Scrabble", new Game(val, res));
		 frame.setVisible(true);
	}
	
	
	

}
