package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

public class Game {

	boolean isInProgress;
	boolean isStart = true;
	public Player[] players;
	public Board board;
	int turn;
	public ArrayList<Letter> availableLetters;
	Dictionary dict;
	int pass;
	boolean isExtraActivated = false;
	int oldID;

	/**
	 * This constructor initializes the letter bag, the players and their racks
	 * 
	 * @param noPlay
	 * @param playerNames
	 */
	public Game(int noPlay, String[] playerNames) {
		isInProgress = true;
		this.availableLetters = new ArrayList<Letter>();
		this.board = new Board();
		dict = new Dictionary();
		pass = 0;
		this.turn = 0;

		addToLetterArr('A', 9);
		addToLetterArr('B', 2);
		addToLetterArr('C', 2);
		addToLetterArr('D', 4);
		addToLetterArr('E', 12);
		addToLetterArr('F', 2);
		addToLetterArr('G', 3);
		addToLetterArr('H', 2);
		addToLetterArr('I', 9);
		addToLetterArr('J', 1);
		addToLetterArr('K', 1);
		addToLetterArr('L', 4);
		addToLetterArr('M', 2);
		addToLetterArr('N', 6);
		addToLetterArr('O', 8);
		addToLetterArr('P', 2);
		addToLetterArr('Q', 1);
		addToLetterArr('R', 6);
		addToLetterArr('S', 4);
		addToLetterArr('T', 6);
		addToLetterArr('U', 4);
		addToLetterArr('V', 2);
		addToLetterArr('W', 2);
		addToLetterArr('X', 1);
		addToLetterArr('Y', 2);
		addToLetterArr('Z', 1);

		System.out.println("NUMBER OF PLAYERS = " + noPlay);
		this.players = new Player[noPlay];
		for (int i = 0; i < noPlay; i++) {
			this.players[i] = new Player(playerNames[i], i);
			this.availableLetters = this.players[i].pickTiles(7,
					this.availableLetters);
		}
	}

	/**
	 * This is a helper function that adds the letter to the letter bag no # of
	 * times
	 * 
	 * @param c
	 *            the letter to be added
	 * @param no
	 *            the number of times teh letter has to be added
	 */
	public void addToLetterArr(char c, int no) {
		for (int i = 0; i < no; i++) {
			Position pos = new Position(-1, -1);
			Letter let = new Letter(pos, c);
			this.availableLetters.add(let);
		}
	}

	/**
	 * makeMove takes in a move made by a player and acts according to the type
	 * of move.
	 * 
	 * @param turn
	 *            The type of move played by the player
	 * @param indexes
	 *            corresponds to the indexes of the letter rack that the user
	 *            wishes to use to make his move
	 * @param spIndex
	 *            the index of the special tile that the user wants to use
	 * @param spIndPos
	 *            the position of the special index he wants to place
	 * @param posArr
	 *            the array of positions that correspond to where each tile
	 *            should be placed in that order
	 * @param id
	 *            the id of the player
	 * @param sp
	 *            the special tile the user wishes to purchase.
	 */
	public Game makeMove(Turn turn, int[] indexes, int spIndex,
			Position spIndPos, Position[] posArr, int id, SpecialTiles sp) {
		// If the game has ended then do not proceed.
		if (endGame()) {
			System.out.println("Game has ended");
			return this;
		}

		// If the right player is not playing then do not proceed
		if (this.players[this.turn].getID() != id) {
			System.out.println("WRONG PLAYER");
			return this;
		}

		boolean bool = false;
		// If the user is stumped and wants to pass then go to the next player.
		if (turn.getType().equals("pass")) {
			this.isStart = false;
			this.pass++;
			if (this.turn + 1 == this.players.length)
				this.turn = 0;
			else
				this.turn++;

			if (this.isExtraActivated) {
				this.turn = this.oldID;
				this.isExtraActivated = false;
			}

			return this;
		}

		// The user can shuffle his hand any amount of times
		else if (turn.getType().equals("shuffle")) {
			this.isStart = false;
			// helper function that shuffles the letter rack
			int temp = this.turn;
			this.players[this.turn].shuffleLetterRack();
			this.turn = temp;

			return this;
		}

		// If the user wants to buy specialTiles
		// THE USER CAN ONLY BUY TILES IF HE HAS A TOTAL OF 8 POINTS AND EACH
		// TILE COSTS 7 POINTS
		else if (turn.getType().equals("buysptiles")) {
			if (this.players[this.turn].points >= 8) {
				// helper function that updates the special tile rack
				// accordingly
				players[this.turn].buySpTile(sp);
				// cost for buying a special tile
				this.players[this.turn].addScore(-7);
			}

			return this;
		}

		// If the player is stumped then he can swap all of his tiles with new
		// tiles
		// but will have to forgo his turn
		else if (turn.getType().equals("swap")) {
			this.isStart = false;
			int[] arr = new int[this.players[this.turn].Rack1.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = i;
			}
			// use up all of the tiles.
			this.players[this.turn].submitMove(arr);
			// Pick new tiles and update the letterbag
			this.availableLetters = this.players[this.turn].pickTiles(
					arr.length, this.availableLetters);
			// forgoes turn
			if (this.turn + 1 == this.players.length)
				this.turn = 0;
			else
				this.turn++;

			if (this.isExtraActivated) {
				this.turn = this.oldID;
				this.isExtraActivated = false;
			}

			return this;
		}

		if (indexes.length == 0)
			return this;
		// Constructs the move from the indexes and the positions
		Move mov = constructMove(
				players[this.turn].getLettersFromMove(indexes), posArr);
		// helper function that returns true if the move can be made
		bool = move(mov);

		System.out.println("LET's SEE");
		if (bool) {
			System.out.println("HELLLP");
			this.pass = 0;
			this.isStart = false;
			// Get all the points of all the words that were made by placing the
			// tiles.
			int score = mov.getAllWordPoints(this.board);
			// empty the letter rack of the appropriate tiles
			this.players[this.turn].submitMove(indexes);
			// Place the tiles on the board.
			mov.addTilesToBoard(this.board);
			// If the move triggered any special Tiles then take care of it and
			// get the updated score
			score = makeEffect(mov, score);
			// update the players score
			updatePlayerScore(score);
			// Pick tiles for the players
			this.availableLetters = this.players[this.turn].pickTiles(
					indexes.length, this.availableLetters);
			// Play a special tile
			System.out.println("Does it even reach here?");
			if (!(spIndex > 7 || spIndex < 0)) {
				System.out.println("DOes it even reach here?");
				System.out.println(spIndex + " is the INDEX");
				SpecialTiles til = players[this.turn].useSpTile(spIndex);
				til.setOwner(getCurrentPlayer());
				til.setLocation(spIndPos);
				this.board.placeTile(til);
			}

			if (this.turn + 1 == this.players.length)
				this.turn = 0;
			else
				this.turn++;

			if (this.isExtraActivated) {
				this.turn = this.oldID;
				this.isExtraActivated = false;
			}

			return this;
		}

		if (this.isExtraActivated) {
			this.turn = this.oldID;
			this.isExtraActivated = false;
		}

		return this;

	}

	/**
	 * Helper function that constructs the move with corresponding letters and
	 * positions
	 * 
	 * @param letArr
	 *            the letter array
	 * @param posArr
	 *            positiosn that correspond with th eletter array
	 * @return the constrcuted move
	 */
	public Move constructMove(Letter[] letArr, Position[] posArr) {
		ArrayList<Letter> res = new ArrayList<Letter>();
		for (int i = 0; i < letArr.length; i++) {
			// Associate letter with the position
			Letter let = letArr[i];
			if (let == null)
				System.out.println("LETTER IS NULL");
			Position pos = posArr[i];
			let.setLocation(pos);
			res.add(let);
			if (pos == null)
				System.out.println("POSITION IS NULL");

			System.out.println("Letter " + let.getLetter()
					+ " is at Position (" + pos.getX() + "," + pos.getY());
		}

		return (new Move(res));
	}

	/**
	 * Returns true if you can make a move and false otherwise
	 * 
	 * @param move
	 * @return true if move is valid and false otherwise
	 */
	public boolean move(Move move) {
		boolean canMkMove = move.isPlacedOnEmptyLocations(this.board);

		if (canMkMove) {
			System.out.println("Placed on Empty Locations");
			canMkMove = move.isValidMove(board, isStart);
		}

		if (canMkMove) {
			System.out.println("Valid Move no gaps");
			canMkMove = move.areWordsValid(move.getAllWords(this.board),
					this.dict);
		}

		if (canMkMove) {
			System.out.println("Words are Valid");
			return true;
		}

		return false;
	}

	/**
	 * Updates the player score
	 * 
	 * @param score
	 *            the nuber of points to be added to the player
	 */
	public void updatePlayerScore(int score) {
		// updates the players score
		this.players[turn].addScore(score);
	}

	/**
	 * Function that changes the state of the board if a special tile was
	 * triggered by the move appropriately
	 * 
	 * @param move
	 *            the move being made
	 * @param score
	 *            the current ttal score of the move
	 * @return the updates score of the move after special tiles are taken care
	 *         of
	 */
	public int makeEffect(Move move, int score) {
		ArrayList<Position> positions = move.getPositions();
		boolean negFlag = false;
		NegativePoints tempTile = new NegativePoints(new Position(-1, -1));
		int temp = score;

		for (Position pos : positions) {
			SpecialTiles tile = this.board.getSpTile(pos);
			// If we encounter a negative tile then we take care of it
			// at the very end
			if (tile.getType().equals("negativepoints")) {
				negFlag = true;
				tempTile = (NegativePoints) tile;
			} else {
				// we make use of inheritence to call on effect
				temp = tile.effect(this, pos, temp);
			}
		}

		// take care of negative points
		if (negFlag) {
			if (temp > 0)
				temp = tempTile.effect(this, tempTile.getPosition(), temp);
		}
		return temp;
	}

	/**
	 * Function that reverese the order of the player array
	 */
	public void reverseOrder() {
		int size = this.players.length;
		Player[] playRev = new Player[size];

		for (int i = 0; i < size; i++) {
			playRev[size - (i + 1)] = this.players[i];
		}

		this.players = playRev;
		this.turn = size - (turn + 1);
	}

	/**
	 * Function that is called when boom is triggered to destroy the letters
	 * that are in a radius of three from that position
	 * 
	 * @param pos
	 * @return
	 */
	public int destroy3Rad(Position pos) {
		if (this.board.getSpTile(pos).getOwner().getID() == this.turn)
			return 0;
		return this.board.invalidate3Rad(pos);

	}

	/**
	 * Function that is called when the special tile skip is triggered This
	 * updates the turn count appropriately
	 */
	public void skip() {
		if (this.turn + 1 == this.players.length - 1)
			this.turn = 0;
		else
			this.turn++;
	}

	// Gets the current player
	public Player getCurrentPlayer() {
		return this.players[this.turn];
	}

	/**
	 * Function the returns if the game has ended or not
	 * 
	 * @return whether the game has ended
	 */
	public boolean endGame() {
		int countE = 0;
		if (this.pass >= ((this.players.length) * 2))
			return true;
		for (Player player : this.players) {
			if (player.isLetterRackEmpty()
					&& (this.availableLetters.size() == 0))
				return true;
			else if (player.isLetterRackEmpty())
				countE++;
		}
		if (countE == this.players.length)
			return true;
		return false;
	}

	public void extraTileEffect(Player p) {
		if (this.turn == this.players.length)
			this.oldID = 0;
		else
			this.oldID = this.turn + 1;

		this.turn = p.getID();
		this.isExtraActivated = true;
	}

	/**
	 * @return the array that contains all the players
	 */
	public Player[] getAllPlayers() {
		return this.players;
	}

}
