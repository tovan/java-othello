package neuburger.othello;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

public class TestBoardOperator {

	private GameBoard gameBoard;
	private BoardOperator operator;
	private GamePiece[][]board ;
	
	public TestBoardOperator(){
		gameBoard = new GameBoard();
		board = gameBoard.getBoard();
		operator = new BoardOperator(board);
		
	}
	@Test 
	public void testConnections(){
		gameBoard.makeMove(Color.WHITE, 3,3);
		gameBoard.makeMove(Color.BLACK, 2,2);
		//computerize the move I made
		assertTrue(operator.hasConnectionNW(board[3][3]));
	
		gameBoard.makeMove(Color.BLACK, 4, 4);
		assertTrue(operator.hasConnectionSE(board[3][3]));
		
		gameBoard.makeMove(Color.BLACK, 2, 3);
		assertTrue(operator.hasConnectionNorth(board[3][3]));
		
		gameBoard.makeMove(Color.BLACK, 4,3);
		assertTrue(operator.hasConnectionSouth(board[3][3]));
	
		gameBoard.makeMove(Color.BLACK, 3,2);
		assertTrue(operator.hasConnectionWest(board[3][3]));
	}
	
	@Test
	public void testGetSurroundingPieces(){
		gameBoard.makeMove(Color.WHITE, 3,3);
		
		gameBoard.makeMove(Color.BLACK, 2,2);
		gameBoard.makeMove(Color.BLACK, 3,2);
		System.out.println(gameBoard.toString());
		ArrayList<GamePiece> surPieces = operator.getSurroundingPieces(board[3][3]);
		
		
		assertEquals(surPieces.size(), 2);
		ArrayList<GamePiece> potentialMoves = operator.getPossibleMoves(Color.WHITE);

		assertEquals(potentialMoves.size(), 2);
		assertTrue(potentialMoves.contains(board[1][1]));
		assertTrue(potentialMoves.contains(board[3][1]));
	}
}
