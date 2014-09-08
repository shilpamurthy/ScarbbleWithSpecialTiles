package edu.cmu.cs.cs214.hw4.core;

import java.util.*;

public class Player {

	String name;
	int points;
	LetterRack Rack1 = new LetterRack();
	SpecialTileRack spcRack = new SpecialTileRack();
	int PlayerID;

	/**
	 * Instantiates the name and the id of the player
	 * 
	 * @param Name
	 * @param id
	 */
	public Player(String Name, int id) {
		this.name = Name;
		this.PlayerID = id;
		this.points = 0;
	}

	/**
	 * Returns the name
	 * 
	 * @return name of player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the id
	 * 
	 * @return ID of player
	 */
	public int getID() {
		return this.PlayerID;
	}

	/**
	 * adds the score to the players current score
	 * 
	 * @param x
	 *            score to be added
	 */
	public void addScore(int x) {
		this.points += x;
	}

	/**
	 * Shuffles the tiles of the letter rack
	 */
	public void shuffleLetterRack() {
		this.Rack1.shuffle();
	}

	/**
	 * Gets all the letters from the move
	 * 
	 * @param indexes
	 *            returns the letters from the
	 * @return the letters in the indexes specified from the players letter rack
	 */
	public Letter[] getLettersFromMove(int[] indexes) {
		Tiles[] tileArr = this.Rack1.getTiles(indexes);
		int counter = 0;
		Letter[] letArr = new Letter[tileArr.length];
		for (Tiles tile : tileArr) {
			letArr[counter] = (Letter) tile;
			counter++;
		}
		return letArr;
	}

	/**
	 * this clears the letter rack of the indices specified
	 * 
	 * @param indexes
	 */
	public void submitMove(int[] indexes) {
		this.Rack1.useTiles(indexes);
	}

	/**
	 * Returns true if the rack is empty
	 * 
	 * @return true if the rack is empty false otehrwise
	 */
	public boolean isLetterRackEmpty() {
		return (this.Rack1.size() == 0);
	}

	/**
	 * Adds a secial tile to the special tile rack
	 * 
	 * @param tile
	 *            the tile to be bought
	 */
	public void buySpTile(SpecialTiles tile) {
		if (this.spcRack.size() == 7) {
			System.out.println("Cannot buy more");
			return;
		} else
			this.spcRack.addTile(tile);
	}

	/**
	 * Function picks a random tile from the arraylist, a specified number of
	 * times
	 * 
	 * @param no
	 *            number of times you shoudl pick the letter from the list
	 * @param arr
	 *            the arraylist of letters you can pick from
	 * @return the modified arraylist
	 */
	public ArrayList<Letter> pickTiles(int no, ArrayList<Letter> arr) {
		Random randomNum = new Random();
		for (int i = 0; i < no; i++) {
			int index = randomNum.nextInt(arr.size());
			Letter let = arr.get(index);
			let.setOwner(this);
			this.Rack1.addTile(let);
			arr.remove(index);
		}
		return arr;
	}

	/**
	 * Function that uses special tile
	 * 
	 * @param index
	 *            index of special tile to be used
	 * @return the special tile that you get
	 */
	public SpecialTiles useSpTile(int index) {
		int[] arr = { index };
		SpecialTiles temp = this.spcRack.rack[index];
		this.spcRack.useTiles(arr);
		return temp;

	}
	
	public LetterRack getLetterRack()
	{
		return this.Rack1;
	}
	
	public SpecialTileRack getSpRack()
	{
		return this.spcRack;
	}

	public int getScore()
	{
		return this.points;
	}
}
