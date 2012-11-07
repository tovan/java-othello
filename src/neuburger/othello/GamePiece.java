package neuburger.othello;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class GamePiece {
	private int xLocation;
	private int yLocation;
	private Point location;
	private Color color;
	private Point direction;

	private ArrayList<GamePiece> potentialMoves;
	private ArrayList<GamePiece> edgePieces;

	public GamePiece() {

	}

	public GamePiece(int x, int y, Color color) {
		this.xLocation = x;
		this.yLocation = y;
		this.location = new Point(xLocation, yLocation);

		this.color = color;
		potentialMoves = new ArrayList<GamePiece>();
		edgePieces = new ArrayList<GamePiece>();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Point getDirection() {
		return direction;
	}

	public void setDirection(int x, int y) {
		this.direction = new Point(x, y);
	}

	public boolean hasColor(Color c) {
		return getColor().equals(c);
	}

	public int getX() {
		return this.xLocation;
	}

	public int getY() {
		return this.yLocation;
	}

	public void addToPotentialMovesList(GamePiece nextPiece) {
		potentialMoves.add(nextPiece);
	}

	public ArrayList<GamePiece> getEdgePieces() {
		return edgePieces;
	}

	public void setEdgePieces(ArrayList<GamePiece> edgePieces) {
		this.edgePieces = edgePieces;
	}
}
