package edu.cmu.cs.cs214.hw4.core;

public class SpecialTileRack implements Rack {
	SpecialTiles[] rack;
	int counter;

	public SpecialTileRack() {
		this.rack = new SpecialTiles[7];
		this.counter = 0;
	}

	/**
	 * Deletes all the tiles in the specfied indices by setting them to null,
	 * creating a new arrray with just the lements left over and then setting
	 * the rack to the new array
	 */
	@Override
	public void useTiles(int[] indexes) {
		// TODO Auto-generated method stub
		for (int i = 0; i < indexes.length; i++) {
			this.rack[indexes[i]] = null;
			this.counter--;
		}

		SpecialTiles[] newRack = new SpecialTiles[7];
		int counter = 0;
		for (int j = 0; j < 7; j++) {
			if (!(this.rack[j] == null)) {
				newRack[counter] = this.rack[j];
			}
		}

		this.rack = newRack;
	}

	/**
	 * Adds a tile to a rack
	 */
	@Override
	public void addTile(Tiles tile) {
		// TODO Auto-generated method stub
		if (!(this.counter == 7)) {
			SpecialTiles let = (SpecialTiles) tile;
			this.rack[counter] = let;
			this.counter++;
		}
	}

	/**
	 * Gets all the tiles specified at that index
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

	public int size() {
		return this.counter;
	}
	
	public SpecialTiles getSpTileAt(int i)
	{
		return this.rack[i];
	}

}
