package edu.cmu.cs.cs214.hw4.core;

public class Letter extends Tiles {

	char letter;
	int multiplier = 1;
	boolean isWordMultiplier;

	public Letter(Position pos, char let) {
		super(pos);
		this.letter = let;
		setPoints(getPoints());
	}

	public String getType() {
		return "letter";
	}

	public char getLetter() {
		return this.letter;
	}

	public void setMultiplier(int m, boolean isW) {
		this.multiplier = m;
		this.isWordMultiplier = isW;
	}

	public boolean isWordMult() {
		return this.isWordMultiplier;
	}

	public int getMultiplier() {
		return this.multiplier;
	}

	/**
	 * Returns the points of the letter depending on the character that it is
	 * associated with
	 */
	public int getPoints() {
		char res = getLetter();
		if ((res == 'A') || (res == 'E') || (res == 'I') || (res == 'U')
				|| (res == 'N') || (res == 'R') || (res == 'O') || (res == 'S')
				|| (res == 'L') || (res == 'T'))
			return 1;
		if ((res == 'B') || (res == 'C') || (res == 'M') || (res == 'P'))
			return 3;
		if ((res == 'F') || (res == 'H') || (res == 'V') || (res == 'W')
				|| (res == 'Y'))
			return 4;
		if ((res == 'X') || (res == 'J'))
			return 8;
		if ((res == 'D') || (res == 'G'))
			return 2;
		if ((res == 'Q') || (res == 'Z'))
			return 10;

		return 5;
	}

	public boolean equals(Tiles tile) {
		if (!(tile.getType().equals("letter")))
			return false;
		else {
			Letter res = (Letter) tile;
			if (this == tile)
				return true;
			if (this.getLetter() == res.getLetter())
				return true;
		}

		return false;
	}

}
