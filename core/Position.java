package edu.cmu.cs.cs214.hw4.core;

public class Position {
	int x, y;

	/*
	 * Position class. Has an X and a Y component
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int Sx) {
		this.x = Sx;
	}

	public void setY(int Sy) {
		this.y = Sy;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	/**
	 * Checks whether the given position is adjacent to the curret one
	 * 
	 * @param pos
	 *            given position
	 * @return true if the position is adjacent and false otherwise
	 */
	public boolean isAdjacent(Position pos) {
		int posX = pos.getX();
		int posY = pos.getY();

		if (((Math.abs(this.x - posX) == 1) && (this.y - posY == 0))
				|| (Math.abs(this.y - posY) == 1) && (this.x - posX == 0)) {
			return true;
		}

		return false;
	}

	/**
	 * checks whether the current position is valid
	 * 
	 * @return true if the current position is valid and false otherwise
	 */
	public boolean isValidPosition() {
		int posX = this.x;
		int posY = this.y;

		return ((posX < 15) && (posX >= 0) && (posY < 15) && (posY >= 0));
	}

	public boolean isAdjacentOccupied(Board board) {
		return (isLeftOccupied(board) || isRightOccupied(board)
				|| isUpOccupied(board) || isDownOccupied(board));

	}

	/**
	 * @return Position to the left of the current position
	 */
	public Position getLeft() {
		return new Position(this.x, this.y - 1);
	}

	/**
	 * @return Position to the right of the current position
	 */
	public Position getRight() {
		return new Position(this.x, this.y + 1);
	}

	/**
	 * @return Position to above of the current position
	 */
	public Position getUp() {
		return new Position(this.x - 1, this.y);
	}

	/**
	 * @return Position to the bottom of the current position
	 */
	public Position getDown() {
		return new Position(this.x + 1, this.y);
	}

	/**
	 * @return true if position to the left of the current position is occupied
	 */
	public boolean isLeftOccupied(Board board) {
		Position left = getLeft();
		return (left.isValidPosition() && (!(board.canATileBePlaced(left))));
	}

	/**
	 * @return true if position to the right of the current position is occupied
	 */
	public boolean isRightOccupied(Board board) {
		Position right = getRight();
		return (right.isValidPosition() && (!(board.canATileBePlaced(right))));
	}

	/**
	 * @return true if position to the top of the current position is occupied
	 */
	public boolean isUpOccupied(Board board) {
		Position up = getUp();
		return (up.isValidPosition() && (!(board.canATileBePlaced(up))));
	}

	/**
	 * @return true if position to the bottom of the current position is
	 *         occupied
	 */
	public boolean isDownOccupied(Board board) {
		Position down = getDown();
		return (down.isValidPosition() && (!(board.canATileBePlaced(down))));
	}

}
