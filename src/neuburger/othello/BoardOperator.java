package neuburger.othello;

import java.awt.Color;
import java.util.ArrayList;

public class BoardOperator {

	private GamePiece[][] board;
	private Color myColor;
	
	public BoardOperator(GamePiece[][] board){
		this.board = board;
		setInitialBoard();
	}
	public void setInitialBoard(){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				board[row][col] = new GamePiece(row, col, null);
			}
		}
	}
	public ArrayList<GamePiece> getPossibleMoves(Color color){
		setMyColor(color);
		ArrayList<GamePiece> possibleMoves = new ArrayList<GamePiece>();
		for(int row = 1; row < board.length; row++){
			for(int col = 1; col<board.length; col++ ){
				if(isPossibleMove(color, row,col)){
					possibleMoves.add(board[row][col]);
					System.out.println("just added "+row+", "+col+" as a potential move");
				}
			}
		}
		return possibleMoves;
	}
	public boolean isPossibleMove(Color color, int row, int col){
		if(spaceIsOccupied(row, col)){
			return false;
		}
		//if piece is empty, check if surrounded by piece of other color
		else {
			ArrayList<GamePiece> surroundingPieces = getSurroundingPieces(board[row][col]);
			ArrayList<GamePiece> edgePieces = new ArrayList<GamePiece>() ;
			for (GamePiece piece : surroundingPieces){
				edgePieces.addAll(findExistingEdgePieces(color, piece));
				board[row][col].setEdgePieces(edgePieces);
			}
			return edgePieces.size() > 0;
		}
	}

	protected ArrayList<GamePiece> findExistingEdgePieces(Color playersColor, GamePiece surroundingPiece){
		Color otherColor = (playersColor == Color.white? Color.black: Color.white);
		//receives in a piece of opposite color from player's color
		//method recursively calls itself to find blank spot at other end
		//if blank spot is in board it is a possible move
		ArrayList<GamePiece> edgePieces = new ArrayList<GamePiece>();
		int nextX = surroundingPiece.getX()+surroundingPiece.getDirection().x;
		int nextY = surroundingPiece.getY()+surroundingPiece.getDirection().y;
		//check if in board (otherwise checking its color will throw an error)
		if(inBoard(nextX, nextY)){
			GamePiece nextPiece = board[nextX][nextY];
			if(nextPiece.getColor() == playersColor){//we hit a piece on other end of blank spot that is player's color, so blank spot is a valid move
				edgePieces.add(nextPiece);
			}
			else if(nextPiece.getColor() == otherColor){
				setDirection(surroundingPiece, nextPiece);
				findExistingEdgePieces(playersColor, nextPiece);
			}
		}
		return edgePieces;
	}
	protected int potentialPiecesGained(GamePiece gamePiece, Color myColor){
		int piecesGained = 0;
		Color otherColor = (myColor == Color.white? Color.black: Color.white);
		ArrayList<GamePiece>surroundingPieces = getSurroundingPieces(gamePiece);
		for (GamePiece surroundingPiece : surroundingPieces){
			
				ArrayList<GamePiece> edgePieces = new ArrayList<GamePiece>();
				int nextX = surroundingPiece.getX()+surroundingPiece.getDirection().x;
				int nextY = surroundingPiece.getY()+surroundingPiece.getDirection().y;
				
				if(inBoard(nextX, nextY)){
					GamePiece nextPiece = board[nextX][nextY];
					piecesGained++;
					while(nextPiece.getColor() == otherColor){
						nextX = surroundingPiece.getX()+surroundingPiece.getDirection().x;
						nextY = surroundingPiece.getY()+surroundingPiece.getDirection().y;
						
					if(nextPiece.getColor() == myColor){//we hit a piece on other end of blank spot that is player's color, so blank spot is a valid move
						edgePieces.add(nextPiece);
						piecesGained++;
					
					}
				}
			}
		}
		return piecesGained;
	}
	private boolean inBoard(int x, int y){
		if(x >= 0 && y >= 0){
			if(x <= 8 && y <= 8){
				return true;
			}
		}
		return false;
	}
	private void setDirection(GamePiece currentPiece, GamePiece surroundingPiece){
		int x = surroundingPiece.getX() - currentPiece.getX();
		int y = surroundingPiece.getY() - currentPiece.getY();
		surroundingPiece.setDirection(x, y);
	}
	protected ArrayList<GamePiece> getSurroundingPieces(GamePiece currentPiece) {
		ArrayList<GamePiece> surroundingPieces = new ArrayList<GamePiece>(); 
		if(hasConnectionNorth(currentPiece)){
			GamePiece northPiece = getConnectingOnNorth(currentPiece);
			setDirection(currentPiece, northPiece);
			surroundingPieces.add(northPiece);
		}
		if(hasConnectionSouth(currentPiece)){
			GamePiece southPiece = getConnectingOnSouth(currentPiece);
			setDirection(currentPiece, southPiece);
//			southPiece.setDirection(0, 1);
			surroundingPieces.add(getConnectingOnSouth(currentPiece));
		}
		if(hasConnectionEast(currentPiece)){
			GamePiece eastPiece = getConnectingOnEast(currentPiece);
			setDirection(currentPiece, eastPiece);
			surroundingPieces.add(getConnectingOnEast(currentPiece));
		}
		if(hasConnectionWest(currentPiece)){
			GamePiece westPiece = getConnectingOnWest(currentPiece);
			setDirection(currentPiece, westPiece);
			surroundingPieces.add(getConnectingOnWest(currentPiece));
		}
		if(hasConnectionNE(currentPiece)){
			GamePiece nePiece = getConnectingOnNE(currentPiece);
			setDirection(currentPiece, nePiece);
			surroundingPieces.add(nePiece);
		}
		if(hasConnectionNW(currentPiece)){
			GamePiece nwPiece = getConnectingOnNW(currentPiece);
			setDirection(currentPiece, nwPiece);
			surroundingPieces.add(nwPiece);
		}
		if(hasConnectionSE(currentPiece)){
			GamePiece sePiece = getConnectingOnSE(currentPiece);
			setDirection(currentPiece, sePiece);
			surroundingPieces.add(sePiece);
		}
		if(hasConnectionSW(currentPiece)){
			GamePiece swPiece = getConnectingOnSW(currentPiece);
			setDirection(currentPiece, swPiece);
			surroundingPieces.add(swPiece);
		}
		return surroundingPieces;
	}
	public boolean isEmpty(int row, int col){
		return board[row][col].getColor() == null;
	}
	private boolean spaceIsOccupied(int row, int col) {
		return board[row][col].getColor() != null;
	}
	
	public Color getOtherColor(Color color){
		return myColor == Color.BLACK? Color.WHITE: Color.BLACK;
		/*
		if(color == null){
			return Color.black;
		}
		else{
			return color == Color.white? Color.black: Color.white;
		}
	*/
	}
	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}
	public boolean hasConnectionNorth(GamePiece piece){
		Color otherColor = getOtherColor(myColor);
		int newX = piece.getX()-1;
		int newY = piece.getY();	//same y
		boolean hasConnection = false;
		if(inBoard(newX, newY)){
			hasConnection = board[newX][newY].getColor() == (otherColor);
		}
		return hasConnection;
	}
	public GamePiece getConnectingOnNorth(GamePiece piece){
		int newX = piece.getX()-1;
		int newY = piece.getY();	//same y
		return board[newX][newY];
	}
	public boolean hasConnectionSouth(GamePiece piece){
		Color otherColor = getOtherColor(myColor);
		int newX = piece.getX()+1;
		int newY = piece.getY();	//same y
		boolean hasConnection = false;
		if(inBoard(newX, newY)){
			hasConnection = board[newX][newY].getColor() == (otherColor);
		}
		return hasConnection;
	}
	public GamePiece getConnectingOnSouth(GamePiece piece){
		int newX = piece.getX()+1;
		int newY = piece.getY();	//same y
		return board[newX][newY];
	}
	public boolean hasConnectionEast(GamePiece piece){
		Color otherColor = getOtherColor(myColor);
		int newX = piece.getX();	//same x
		int newY = piece.getY()+1;
		boolean hasConnection = false;
		if(inBoard(newX, newY)){
			hasConnection = board[newX][newY].getColor() == (otherColor);
		}
		return hasConnection;
	}
	public GamePiece getConnectingOnEast(GamePiece piece){
		int newX = piece.getX();	//same x
		int newY = piece.getY()+1;
		return board[newX][newY];
	}
	public boolean hasConnectionWest(GamePiece piece){
		Color otherColor = getOtherColor(myColor);
		int newX = piece.getX();	//same x
		int newY = piece.getY()-1;
		boolean hasConnection = false;
		if(inBoard(newX, newY)){
			hasConnection = board[newX][newY].getColor() == (otherColor);
		}
		return hasConnection;
	}
	public GamePiece getConnectingOnWest(GamePiece piece){
		int newX = piece.getX();	//same x
		int newY = piece.getY()-1;
		return board[newX][newY];
	}
	public boolean hasConnectionNE(GamePiece piece){
		Color otherColor = getOtherColor(myColor);
		int newX = piece.getX()-1;
		int newY = piece.getY()+1;	
		boolean hasConnection = false;
		if(inBoard(newX, newY)){
			hasConnection = board[newX][newY].getColor() == (otherColor);
		}
		return hasConnection;
	}
	public GamePiece getConnectingOnNE(GamePiece piece){
		int newX = piece.getX()-1;
		int newY = piece.getY()+1;	
		return board[newX][newY];
	}
	public boolean hasConnectionNW(GamePiece piece){
		Color otherColor = getOtherColor(myColor);
		int newX = piece.getX()-1;
		int newY = piece.getY()-1;
		boolean hasConnection = false;
		if(inBoard(newX, newY)){
			hasConnection = board[newX][newY].getColor() == (otherColor);
		}
		return hasConnection;
	}
	public GamePiece getConnectingOnNW(GamePiece piece){
		int newX = piece.getX()-1;
		int newY = piece.getY()-1;
		return board[newX][newY];
	}
	public boolean hasConnectionSE(GamePiece piece){
		Color otherColor = getOtherColor(myColor);
		int newX = piece.getX()+1;
		int newY = piece.getY()+1;
		boolean hasConnection = false;
		if(inBoard(newX, newY)){
			hasConnection = board[newX][newY].getColor() == (otherColor);
		}
		return hasConnection;
	}
	public GamePiece getConnectingOnSE(GamePiece piece){
		int newX = piece.getX()+1;
		int newY = piece.getY()+1;
		return board[newX][newY];
	}
	public boolean hasConnectionSW(GamePiece piece){
		Color otherColor = getOtherColor(myColor);
		int newX = piece.getX()+1;
		int newY = piece.getY()-1;
		boolean hasConnection = false;
		if(inBoard(newX, newY)){
			hasConnection = board[newX][newY].getColor() == (otherColor);
		}
		return hasConnection;
	}
	public GamePiece getConnectingOnSW(GamePiece piece){
		int newX = piece.getX()+1;
		int newY = piece.getY()-1;
		return board[newX][newY];
	}
}
