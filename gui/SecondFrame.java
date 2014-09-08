package edu.cmu.cs.cs214.hw4.gui;

import java.util.*;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import edu.cmu.cs.cs214.hw4.core.*;

/**
 * 
 * @author shilpamurthy
 * 
 */
public class SecondFrame extends JFrame {

	String[] players;
	Game game;
	JButton[][] arr = new JButton[15][15];
	JButton[] letterArr;
	JButton[] SpTilesArr;
	boolean isLetterClicked = false;
	boolean isBoardTileClicked = true;
	boolean isSpTileClicked = false;
	ArrayList<Position> PositionArr = new ArrayList<Position>();
	Position spIndPos = new Position(-1, -1);
	ArrayList<Integer> indexArr = new ArrayList<Integer>();
	int spInd = -1;
	String currLetter;
	String currSP;
	String oldTile;
	Position currTemp;

	/**
	 * This is the class that implements the scrabble frame. It has a score
	 * panel, a letter rack, a rack for special tiles and a Board
	 * 
	 * @param message
	 *            = "Scrabble"
	 * @param game
	 *            The status of the game that the has to be currently drawn on
	 *            the frame.
	 */
	public SecondFrame(String message, Game game) {
		super(message);
		this.game = game;
		makeBoardPanel();
		this.setSize(500, 800);

	}

	/**
	 * This function sets up the different panels, adds action listeners to the
	 * buttons and updates the status of the game as the user interacts with the
	 * gui.
	 */
	public void makeBoardPanel() {
		this.setLayout(new GridLayout(2, 1));
		this.setBackground(Color.gray);

		JPanel Panel = new JPanel();
		JPanel PlayerPanel = new JPanel();

		Panel.setPreferredSize(new Dimension(300, 400));
		Panel.setMaximumSize(Panel.getPreferredSize());
		Panel.setMinimumSize(Panel.getPreferredSize());

		PlayerPanel.setPreferredSize(new Dimension(300, 400));
		PlayerPanel.setMaximumSize(Panel.getPreferredSize());
		PlayerPanel.setMinimumSize(Panel.getPreferredSize());

		Board board = game.board;

		Panel.setLayout(new GridLayout(15, 15));

		// Function to set up all the JButtons for the board and add action
		// listeners to it.
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {

				JButton newButton = new JButton();
				newButton.setOpaque(true);
				newButton.setBackground(Color.black);
				String st = board.getState(new Position(i, j)).getType();
				String res = "";
				if (st.equals("doublewordscore")) {
					res = "DW";
					newButton.setForeground(Color.red);
				}
				if (st.equals("doubleletterscore")) {
					res = "DL";
					newButton.setForeground(Color.blue);
				}
				if (st.equals("tripleletterscore")) {
					res = "TW";
					newButton.setForeground(Color.green);
				}
				if (st.equals("triplewordscore")) {
					res = "TL";
					newButton.setForeground(Color.pink);
				}
				if (i == 7 && j == 7)
				{
					// Indicate the middle of the board
					newButton.setForeground(Color.magenta);
					res = "*";
				}
				
				// Show a special tile only if the owner of the tile is the
				// current player
				if (game.board.getSpTile(new Position(i, j)) != null) {
					SpecialTiles sp = game.board.getSpTile(new Position(i, j));
					if (sp.getOwner().getID() == game.getCurrentPlayer()
							.getID()) {
						newButton.setForeground(new Color(94, 38, 18));
						if (sp.getType().equals("negativepoints"))
							res = "NP";
						if (sp.getType().equals("reverseorderplayer"))
							res = "RO";
						if (sp.getType().equals("boom"))
							res = "BM";
						if (sp.getType().equals("skip"))
							res = "sk";
						if (sp.getType().equals("extraturn"))
							res = "ET";
					}
				}

				// If there are letters on the board then show it to all the
				// players
				if (!(game.board.canATileBePlaced(new Position(i, j)))) {
					if (board.getLetterAt(new Position(i, j)) != null) {
						newButton.setForeground(Color.black);
						res = ""
								+ board.getLetterAt(new Position(i, j))
										.getLetter();
					}
				}

				newButton.setText(res);
				this.arr[i][j] = newButton;
				Panel.add(this.arr[i][j]);

				// add action listeners to the button
				arr[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (isBoardTileClicked() && !posArrEmpty()) {
							deleteFromPosArr();
							addToPositionArr(e);
							setLetterBool(false);
							setSpTileClicked(false);
							setBoardTileClicked(true);
						}
						if (isLetterClicked() && (!(isBoardTileClicked()))) {
							setBoardTileClicked(true);
							addToPositionArr(e);
							setLetterBool(false);
							setSpTileClicked(false);
						}

						if (isSpTileClicked()) {
							setBoardTileClicked(true);
							setToSpIndPos(e);
							setLetterBool(false);
							setSpTileClicked(false);
						}
					}
				});

			}

		PlayerPanel.setLayout(new GridLayout(5, 1));

		// Initialize the scores and names of all th eplayers
		JPanel info = new JPanel();
		info.setLayout(new GridLayout(4, 2));

		// get all the players and display the score and name
		Player[] allPlayers = game.getAllPlayers();
		for (int i = 0; i < allPlayers.length; i++) {
			JLabel Playername = new JLabel();
			JLabel Score = new JLabel();

			Playername.setBackground(Color.gray);
			Score.setBackground(Color.gray);

			Playername.setText(allPlayers[i].getName());
			Score.setText("Score = " + allPlayers[i].getScore());
			info.add(Playername);
			info.add(Score);
		}

		PlayerPanel.add(info);

		// Initialize the letterRack of the player
		JPanel LetterRack = new JPanel();
		LetterRack.setLayout(new GridLayout(1, 7));

		// get the current letter rack of the player and add it to the
		// letterRackArray stored locally
		// add action listeners to each button
		LetterRack letters = game.getCurrentPlayer().getLetterRack();
		letterArr = new JButton[letters.size()];
		for (int a = 0; a < letters.size(); a++) {
			JButton newBut = new JButton();
			newBut.setSize(20, 20);
			newBut.setText("" + letters.getLetterAt(a).getLetter());
			letterArr[a] = newBut;
			letterArr[a].setOpaque(true);
			letterArr[a].setBackground(Color.black);
			letterArr[a].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (isLetterClicked()) {
						deleteFromIndexArr();
						addToIndexArr(e);
						setLetterBool(true);
						setBoardTileClicked(false);
						setSpTileClicked(false);
					} else if (!isSpTileClicked()) {
						addToIndexArr(e);
						setLetterBool(true);
						setBoardTileClicked(false);
						setSpTileClicked(false);
					}
					setLetterBool(true);
					setBoardTileClicked(false);
					setSpTileClicked(false);
				}
			});

			LetterRack.add(letterArr[a], BorderLayout.CENTER);
		}
		PlayerPanel.add(LetterRack);

		// Initialize the specialRack of the current Player
		JPanel SpTileRack = new JPanel();
		SpTileRack.setLayout(new GridLayout(1, 7));

		SpecialTileRack spTiles = game.getCurrentPlayer().getSpRack();
		SpTilesArr = new JButton[spTiles.size()];
		for (int b = 0; b < spTiles.size(); b++) {
			JButton newBut = new JButton();
			newBut.setPreferredSize(new Dimension(20, 20));
			newBut.setText(spTiles.getSpTileAt(b).getType());
			newBut.setBackground(Color.black);
			SpTilesArr[b] = newBut;

			SpTilesArr[b].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setSpecialIndex(e);
					setSpTileClicked(true);
					setLetterBool(false);
					setBoardTileClicked(false);
				}

			});

			SpTileRack.add(SpTilesArr[b]);
		}
		PlayerPanel.add(SpTileRack);

		// Now we add all the buttons associated with the move
		JPanel moves = new JPanel();
		JButton submit = new JButton("Submit Move");
		JButton pass = new JButton("Pass");
		JButton shuffle = new JButton("Shuffle");
		JButton swap = new JButton("Swap");
		JButton recall = new JButton("Recall");
		moves.add(submit);
		moves.add(pass);
		moves.add(shuffle);
		moves.add(swap);
		moves.add(recall);

		PlayerPanel.add(moves);
		JPanel next = new JPanel();
		next.setLayout(new GridLayout(2, 1));

		JPanel SpecialTile = new JPanel();
		SpecialTile.setLayout(new GridLayout(1, 5));

		// Add special tiles to the market
		final JButton negative = new JButton("Negative Points");
		final JButton skip = new JButton("Skip");
		final JButton boom = new JButton("Boom");
		final JButton reverse = new JButton("Reverse");
		final JButton extra = new JButton("Extra Turn");
		JButton buy = new JButton("Buy");
		SpecialTile.add(skip);
		SpecialTile.add(boom);
		SpecialTile.add(negative);
		SpecialTile.add(reverse);
		SpecialTile.add(extra);

		next.add(SpecialTile);
		next.add(buy);

		PlayerPanel.add(next);

		this.add(Panel);
		this.add(PlayerPanel);

		this.pack();

		// Add action listeners to the move buttons

		// When a player pushes the submit button then we make the move that he
		// has already specified
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				makeTheMove();
			}
		});

		// When a player passes then we pass the move pass to the game and
		// recreate the frame
		pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				game.makeMove(new Pass(), null, -1, null, null, game
						.getCurrentPlayer().getID(), null);
				SecondFrame frame = new SecondFrame("Scrabble", game);
				frame.setVisible(true);
			}
		});

		// WHen a person recalls, then all of the potential moves he made will
		// be reset
		recall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				SecondFrame frame = new SecondFrame("Scrabble", game);
				frame.setVisible(true);
			}
		});

		// This passes shuffle into the game as a move, passes the new game
		// state to another frame and sets that to visible
		shuffle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				game.makeMove(new Shuffle(), null, -1, null, null, game
						.getCurrentPlayer().getID(), null);
				SecondFrame frame = new SecondFrame("Scrabble", game);
				frame.setVisible(true);
			}
		});

		swap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				game.makeMove(new Swap(), null, -1, null, null, game
						.getCurrentPlayer().getID(), null);
				SecondFrame frame = new SecondFrame("Scrabble", game);
				frame.setVisible(true);
			}
		});

		// A PERSON CAN ONLY BUY TILES IF HE PRESSES THE BUY BUTTON FIRST AND
		// THEN PRESSES THE SPECIAL TILE
		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				skip.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
						game.makeMove(new BuySpTiles(), null, -1, null, null,
								game.getCurrentPlayer().getID(), new Skip(
										new Position(-1, -1)));
						SecondFrame frame = new SecondFrame("Scrabble", game);
						frame.setVisible(true);
					}
				});

				negative.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
						game.makeMove(new BuySpTiles(), null, -1, null, null,
								game.getCurrentPlayer().getID(),
								new NegativePoints(new Position(-1, -1)));
						SecondFrame frame = new SecondFrame("Scrabble", game);
						frame.setVisible(true);
					}
				});

				reverse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
						game.makeMove(new BuySpTiles(), null, -1, null, null,
								game.getCurrentPlayer().getID(),
								new ReverseOrderPlayer(new Position(-1, -1)));
						SecondFrame frame = new SecondFrame("Scrabble", game);
						frame.setVisible(true);
					}
				});

				boom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
						game.makeMove(new BuySpTiles(), null, -1, null, null,
								game.getCurrentPlayer().getID(), new Boom(
										new Position(-1, -1)));
						SecondFrame frame = new SecondFrame("Scrabble", game);
						frame.setVisible(true);
					}
				});

				extra.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
						game.makeMove(new BuySpTiles(), null, -1, null, null,
								game.getCurrentPlayer().getID(), new ExtraTurn(
										new Position(-1, -1)));
						SecondFrame frame = new SecondFrame("Scrabble", game);
						frame.setVisible(true);
					}
				});
			}
		});

	}

	/**
	 * helper function to close the current frame
	 */
	public void close() {
		this.setVisible(false);
	}

	/**
	 * Updates isLetterClicked, it is a helper function called inside the
	 * actionlisteners to update what has been clicked
	 * 
	 * @param b
	 *            the specified boolean
	 */
	public void setLetterBool(boolean b) {
		this.isLetterClicked = b;
	}

	/**
	 * Updates isBoardTile, it is a helper function called inside the
	 * actionlisteners to update what has been clicked
	 * 
	 * @param b
	 */
	public void setBoardTileClicked(boolean b) {
		this.isBoardTileClicked = b;
	}

	/**
	 * @return whether a letter tile in the letter rack has been clicked
	 */
	public boolean isLetterClicked() {
		return this.isLetterClicked;
	}

	/**
	 * @return whether a board tile is clicked
	 */
	public boolean isBoardTileClicked() {
		return this.isBoardTileClicked;
	}

	/**
	 * Adds the position of the board tile clicked to the position array which
	 * will then be passed onto makeMove
	 * 
	 * @param e
	 *            the actionevent that was performed
	 */
	public void addToPositionArr(ActionEvent e) {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				if (e.getSource().equals(this.arr[i][j])) {
					this.oldTile = this.arr[i][j].getText();
					this.PositionArr.add(new Position(i, j));
					Font newF = new Font("SansSerif", Font.ITALIC, 8);
					this.arr[i][j].setFont(newF);
					this.arr[i][j].setForeground(Color.black);
					this.arr[i][j].setText(this.currLetter);
					this.currTemp = new Position(i, j);
				}
	}

	/**
	 * Set the position of the special tile the player wants to place on the
	 * board
	 * 
	 * @param e
	 *            the actionevent that was performed
	 */
	public void setToSpIndPos(ActionEvent e) {
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++) {
				if (e.getSource().equals(this.arr[i][j])) {
					this.spIndPos = new Position(i, j);
					Font newF = new Font("SansSerif", Font.ITALIC, 8);
					this.arr[i][j].setFont(newF);
					this.arr[i][j].setForeground(Color.black);
					this.arr[i][j].setText(this.currSP);
				}
			}
	}

	public void addToIndexArr(ActionEvent e) {
		for (int i = 0; i < game.getCurrentPlayer().getLetterRack().size(); i++)
			if (e.getSource().equals(this.letterArr[i])) {
				this.indexArr.add(i);
				this.currLetter = this.letterArr[i].getText();
			}

	}

	/**
	 * set appropriately whether a specialtile in the specialtile rack was
	 * clicked
	 * 
	 * @param b
	 *            the specified boolean
	 */
	public void setSpTileClicked(boolean b) {
		this.isSpTileClicked = b;
	}

	/**
	 * @return whether a specialTile is clicked
	 */
	public boolean isSpTileClicked() {
		return this.isSpTileClicked;
	}

	/**
	 * Delete an index of the letter from the index array
	 */
	public void deleteFromIndexArr() {
		this.indexArr.remove((this.indexArr.size() - 1));
	}

	/**
	 * Delete the position from the position array
	 */
	public void deleteFromPosArr() {
		this.PositionArr.remove(this.PositionArr.size() - 1);
		this.arr[currTemp.getX()][currTemp.getY()].setText(this.oldTile);
	}

	/**
	 * Set the index of the special tile from he special tile rack
	 * 
	 * @param e
	 *            the actionevent
	 */
	public void setSpecialIndex(ActionEvent e) {
		for (int i = 0; i < this.SpTilesArr.length; i++)
			if (e.getSource().equals(this.SpTilesArr[i])) {
				String res = this.SpTilesArr[i].getText();
				this.spInd = i;
				if (res.equals("negativepoints"))
					res = "NP";
				if (res.equals("reverse"))
					res = "RO";
				if (res.equals("boom"))
					res = "BM";
				if (res.equals("Skip"))
					res = "sk";
				if (res.equals("extraturn"))
					res = "ET";
				this.currSP = res;
			}
	}

	/**
	 * @return whether the position array is empty
	 */
	public boolean posArrEmpty() {
		return (this.PositionArr.size() == 0);
	}

	/**
	 * Makes the move with global variables that have been appropriately set by
	 * the action events performed by the player
	 */
	public void makeTheMove() {
		int[] res = new int[this.indexArr.size()];
		Position[] pos = new Position[this.PositionArr.size()];
		if (pos.length == res.length) {
	
			for (int i = 0; i < res.length; i++) {
				res[i] = this.indexArr.get(0);
				this.indexArr.remove(0);
				pos[i] = this.PositionArr.get(0);
				this.PositionArr.remove(0);

			}

			this.game.makeMove(new Place(), res, this.spInd, this.spIndPos,
					pos, game.getCurrentPlayer().getID(), null);
			SecondFrame newF = new SecondFrame("Scrabble", this.game);
			newF.setVisible(true);
		}
	}

}
