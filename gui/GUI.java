package edu.cmu.cs.cs214.hw4.gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GUI {
	
	public static void main(String[] args)
	{
	    Runnable runner = new Runnable() {
	        public void run() {
	          WelcomeFrame frame = new WelcomeFrame("Welocme to scrabble"); 
	   
	          frame.setVisible(true);
	      
	        }
	      };
	      EventQueue.invokeLater(runner);
	    }

}
