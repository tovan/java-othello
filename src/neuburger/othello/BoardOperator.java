package neuburger.othello;

import java.awt.Color;
import java.util.ArrayList;

public class BoardOperator {

	private GamePiece[][] board;
	
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
			//set direction in following color
			ArrayList<GamePiece> surroundingPieces = getSurroundingPieces(board[row][col]);
			ArrayList<GamePiece> edgePieces = new ArrayList<GamePiece>() ;
			for (GamePiece piece : surroundingPieces){
				edgePieces.addAll(findExistingEdgePieces(color, piece));
			}
			return edgePieces.size() > 0;
			//should return directions of colors linked to 
			//check that other color is linked to piece of current player's color
		}
	}

	protected ArrayList<GamePiece> findExistingEdgePieces(Color playersColor, GamePiece surroundingPiece){
		Color otherColor = (playersColor == Color.white? Color.black: Color.white);
		//receives in a piece of opposite color from player's color
		//method recursively calls itself to find blank spot at other end
		//if blank spot is in board it is a possible move
		ArrayList<GamePiece> edgePieces = new ArrayList<GamePiece>();
		//check if in board (otherwise checking its color will throw an error)
		int nextX = surroundingPiece.getX()+surroundingPiece.getDirection().x;
		int nextY = surroundingPiece.getY()+surroundingPiece.getDirection().y;
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
//			GamePiece northPiece = board[currentPiece.getX()-1][currentPiece.getY()];
			setDirection(currentPiece, northPiece);
//			northPiece.setDirection(0, -1);
			surroundingPieces.add(northPiece);
		}
		if(hasConnectionSouth(currentPiece)){
			GamePiece southPiece = getConnectingOnSouth(currentPiece);
//			GamePiece southPiece = board[currentPiece.getX()][currentPiece.getY()-1];
			southPiece.setDirection(0, 1);
			surroundingPieces.add(getConnectingOnSouth(currentPiece));
		}
		if(hasConnectionEast(currentPiece)){
			GamePiece eastPiece = getConnectingOnEast(currentPiece);
//			GamePiece eastPiece = board[currentPiece.getX()][currentPiece.getY()+1];
			setDirection(currentPiece, eastPiece);
//			eastPiece.setDirection(0, 1);
			surroundingPieces.add(getConnectingOnEast(currentPiece));
		}
		//case that works
		if(hasConnectionWest(currentPiece)){
			GamePiece westPiece = getConnectingOnWest(currentPiece);
//			GamePiece westPiece = board[currentPiece.getX()][currentPiece.getY()-1];
			setDirection(currentPiece, westPiece);
//			westPiece.setDirection(0, -1);
			surroundingPieces.add(getConnectingOnWest(currentPiece));
		}
		if(hasConnectionNE(currentPiece)){
			GamePiece nePiece = getConnectingOnNE(currentPiece);
//			GamePiece nePiece = board[currentPiece.getX()+1][currentPiece.getY()-1];
			setDirection(currentPiece, nePiece);
//			nePiece.setDirection(1, -1);
			surroundingPieces.add(nePiece);
		}
		if(hasConnectionNW(currentPiece)){
			GamePiece nwPiece = getConnectingOnNW(currentPiece);
//			GamePiece nwPiece = board[currentPiece.getX()-1][currentPiece.getY()-1];
			setDirection(currentPiece, nwPiece);
//			nwPiece.setDirection(-1, -1);
			surroundingPieces.add(nwPiece);
		}
		if(hasConnectionSE(currentPiece)){
			GamePiece sePiece = getConnectingOnSE(currentPiece);
			setDirection(currentPiece, sePiece);
//			sePiece.setDirection(1, 1);
			surroundingPieces.add(sePiece);
		}
		if(hasConnectionSW(currentPiece)){
			GamePiece swPiece = getConnectingOnSW(currentPiece);
//			GamePiece swPiece = board[currentPiece.getX()-1][currentPiece.getY()+1];
			setDirection(currentPiece, swPiece);
//			swPiece.setDirection(-1, 1);
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
		if(color == null){
			return Color.black;
		}
		else{
			return color == Color.white? Color.black: Color.white;
		}
	}
	public boolean hasConnectionNorth(GamePiece piece){
		Color otherColor = getOtherColor(piece.getColor());
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
		Color otherColor = getOtherColor(piece.getColor());
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
		Color otherColor = getOtherColor(piece.getColor());
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
		Color otherColor = getOtherColor(piece.getColor());
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
		Color otherColor = getOtherColor(piece.getColor());
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
		Color otherColor = getOtherColor(piece.getColor());
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
		Color otherColor = getOtherColor(piece.getColor());
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
		Color otherColor = getOtherColor(piece.getColor());
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
