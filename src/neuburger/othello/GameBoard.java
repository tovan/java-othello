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
	protected void makeMove(Color color, int x, int y) {
		Color colorofDesiredSpot = ((GamePiece)board[x][y]).getColor();
		if(colorofDesiredSpot != null){
			System.out.println("sorry, but spot is already taken!");
		}
		else{
			board[x][y]= new GamePiece(x, y, color);	
		}
	}
	protected void makeComputerMove(Color color){
		//must be black
		//find a smart move and add piece there
		//next line may look confusing - class findMoves calls findMoves method 
		ArrayList<GamePiece> potentialMoves = this.boardOperator.getPossibleMoves(color);
		if(potentialMoves.size() == 0){
			System.out.println("no moves can be made");
		}
		else{
			GamePiece nextMove = potentialMoves.get(0);
			this.makeMove(Color.BLACK, nextMove.getLocation().x, nextMove.getLocation().y);
		}
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
				
				else if(((GamePiece)currentBox).equals(Color.WHITE)){
					str.append("W");
				}
				else if (((GamePiece)currentBox).equals(Color.BLACK)){
					str.append("B");
				}
			}
			str.append("\n");
		}
		return str.toString();
	}

}