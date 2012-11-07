package neuburger.othello;

import java.awt.Color;
import java.util.ArrayList;

public class GameBoard {
	
	private GamePiece[][]board;
	private BoardOperator boardOperator;
	
	public GameBoard(){
		board = new GamePiece[9][9];
		boardOperator = new BoardOperator(board);
	}
	
	public GamePiece[][] getBoard() {
		return board;
	}
	public void setUpBoard(){
		board[4][4]= new GamePiece(4, 4, Color.BLACK);
		board[4][5]= new GamePiece(4, 5, Color.WHITE);
		board[5][4]= new GamePiece(5, 4, Color.WHITE);
		board[5][5]= new GamePiece(5, 5, Color.BLACK);
		
	}
	protected boolean makeMove(Color color, int x, int y) {
		Color colorofDesiredSpot = ((GamePiece)board[x][y]).getColor();
		boolean madeMove = false;
		if(colorofDesiredSpot != null){
			System.out.println("sorry, but spot is already taken!");
		}
		else if (!boardOperator.isPossibleMove(color, x, y)){
			System.out.println("sorry but that is an invalid move!");	
		}
		else{
			board[x][y]= new GamePiece(x, y, color);
			madeMove = true;
		}
		return madeMove;
	}
	protected void makeComputerMove(Color color){
		//must be black
		//find a smart move and add piece there
		ArrayList<GamePiece> potentialMoves = boardOperator.getPossibleMoves(color);
		if(potentialMoves.size() == 0){
			System.out.println("no moves can be made");
		}
		else{
			GamePiece nextMove = potentialMoves.get(0);
			this.makeMove(Color.BLACK, nextMove.getLocation().x, nextMove.getLocation().y);
			int piecesGained = boardOperator.potentialPiecesGained(nextMove, Color.BLACK);
			this.flipPieces(nextMove, nextMove.getEdgePieces());
//			int piecesGained = computePiecesGained(nextMove);
//			
			System.out.println("you have just captured "+piecesGained+" piece(s)!");
		}
	}
	public void flipPieces(GamePiece nextMove, ArrayList<GamePiece>edgePieces){
		//nextMove --> move just taken
		//has list of edgePieces at other end of pieces to be turned over
		int piecesGained = computePiecesGained(nextMove);
		GamePiece edgePiece = edgePieces.get(0);
		if(nextMove.getX() == edgePiece.getX()){
			while(nextMove.getY() != edgePiece.getY()){
				int totalY = nextMove.getY() - edgePiece.getY();
				int yPerMove = totalY/piecesGained;
				board[nextMove.getX()][nextMove.getY()+yPerMove].setColor(nextMove.getColor());
			}
		}
		
		else if(nextMove.getY() == edgePiece.getY()){
			int totalX = Math.abs(nextMove.getX() - edgePiece.getX());
			int xPerMove = totalX/(piecesGained+1);
			while(nextMove.getX() != edgePiece.getX()){
				
				int newX = nextMove.getX()+xPerMove;
				board[newX][edgePiece.getY()].setColor(edgePiece.getColor());
				nextMove = board[newX][edgePiece.getY()];
			}
		}
		//what if we moved diagonally?
			
	}
	public int computePiecesGained(GamePiece piece){
		//generally there should be one edge piece, can be more
		int distance = 0;
		for(int i=0; i<piece.getEdgePieces().size(); i++){
			GamePiece currEdgePiece = piece.getEdgePieces().get(i);
			//can be a distance of only x, y didn't change
			if(currEdgePiece.getLocation().y == piece.getY()){
				distance = Math.abs(currEdgePiece.getLocation().x - piece.getX());
			}
			
			//can be a distance of only y, x didn't change
			else if(currEdgePiece.getLocation().x == piece.getX()){
				distance = Math.abs(currEdgePiece.getLocation().y - piece.getY());
			}
			
			//can be diagonally apart
			else{
				int xDirection = currEdgePiece.getDirection().x;
				int yDirection = currEdgePiece.getDirection().y;
			}
		}
		//pieces captured are 1 less than distance
		return distance-1;
	}
	public BoardOperator getBoardOperator() {
		return boardOperator;
	}
	public String toString(){
		StringBuffer str =  new StringBuffer();
		for(int row = 1; row < board.length ; row++){
			for(int col = 1; col <board[row].length; col++){
				Object currentBox = board[row][col];
				
				if(((GamePiece)currentBox).getColor() == null){
					str.append("-");
				}
				
				else if(((GamePiece)currentBox).hasColor(Color.WHITE)){
					str.append("W");
				}
				else if (((GamePiece)currentBox).hasColor(Color.BLACK)){
					str.append("B");
				}
			}
			str.append("\n");
		}
		return str.toString();
	}

	public GamePiece getPieceAt(int i, int j) {
		return this.board[i][j];
	}

}