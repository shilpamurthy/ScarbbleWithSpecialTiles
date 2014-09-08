package edu.cmu.cs.cs214.hw4.gui;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import edu.cmu.cs.cs214.hw4.core.*;

public class CustomizedActionListener implements ActionListener{

	JFrame frame;
	Game game;
	int no;
	public CustomizedActionListener(JFrame frame, Game game)
	{
		super();
		this.frame = frame;
		this.game = game;
	}
	
	public CustomizedActionListener(JFrame frame, int no, String[] playerNames)
	{
		super();
		this.frame = frame;
		this.game = new Game(no, playerNames);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(game.getCurrentPlayer().getID());
		this.frame.setVisible(false);
		SecondFrame secondFrame = new SecondFrame("Scrabble", this.game);
		secondFrame.setVisible(true);
		
	}

}
