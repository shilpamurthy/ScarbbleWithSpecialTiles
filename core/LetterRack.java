package edu.cmu.cs.cs214.hw4.core;

import java.util.*;

public class LetterRack implements Rack {

	Letter[] rack;
	int counter;

	public LetterRack() {
		this.rack = new Letter[7];
		this.counter = 0;
	}

	/**
	 * This function takes in an array of indices and then sets those placed to
	 * null then creates a new array with just the elements left from the
	 * previous rack and set the current rack to the new array. This is to
	 * delete the elements in the rack specified by the indices
	 */
	@Override
	public void useTiles(int[] indexes) {
		// TODO Auto-generated method stub
		for (int i = 0; i < indexes.length; i++) {
			this.rack[indexes[i]] = null;
			this.counter--;
		}

		Letter[] newRack = new Letter[7];
		int counter = 0;
		for (int j = 0; j < 7; j++) {
			if (!(this.rack[j] == null)) {
				newRack[counter] = this.rack[j];
				counter++;
			}
		}

		this.rack = newRack;
	}

	/**
	 * addTile adds the tile specified by the parameter to the current rack if
	 * possible
	 */
	@Override
	public void addTile(Tiles tile) {
		// TODO Auto-generated method stub
		if (!(this.counter == 7)) {
			Letter let = (Letter) tile;
			rack[counter] = let;
			this.counter++;
		}
	}

	/**
	 * Gets all the tiles specified by the indices
	 */
	@Override
	public Tiles[] getTiles(int[] indexes) {
		// TODO Auto-generated method stub
		Tiles[] res = new Tiles[indexes.length];
		int counter1 = 0;

		for (int i = 0; i < indexes.length; i++) {
			res[counter1] = this.rack[indexes[i]];
			counter1++;
		}
		return res;
	}

	/**
	 * Shuffles the letters in the rack
	 */
	public void shuffle() {
		List<Letter> list = new ArrayList<Letter>();
		int count = 0;
		while (count < 6 && this.rack[count] != null) {
			Letter l = this.rack[count];
			list.add(l);
			count++;
		}

		Collections.shuffle(list);
		count = 0;
		for (Letter l : list) {
			this.rack[count] = l;
			count++;
		}

	}

	/**
	 * returns the size of the rack
	 * 
	 * @return size of rack
	 */
	public int size() {
		return counter;
	}
	
	public Letter getLetterAt(int i)
	{
		return this.rack[i];
	}

}
