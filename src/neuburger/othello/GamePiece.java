package neuburger.othello;

import java.awt.Color;
import java.awt.Point;
import java.util.List;


public class GamePiece {
	private int xLocation;
	private int yLocation;
	private List surrondingPiecesList;
	private Point location;
	private Color color; 
	private Point direction;
	
	public GamePiece(){
		
	}
	public GamePiece(int x, int y, Color color){
		this.xLocation = x;
		this.yLocation = y;
		this.location = new Point(xLocation, yLocation);
		
		this.color = color;
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
	
	public List getSurrondingPiecesList() {
		return surrondingPiecesList;
	}

	public void setSurrondingPiecesList(List surrondingPiecesList) {
		this.surrondingPiecesList = surrondingPiecesList;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public Point getDirection() {
		return direction;
	}
	public void setDirection(Point direction) {
		this.direction = direction;
	}
	
	public boolean equals(Color c){
		if(getColor().equals(c))
			return true;
		return false;
	}
	
}
