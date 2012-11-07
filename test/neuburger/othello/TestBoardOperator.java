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
		gameBoard.setUpBoard();
		gameBoard.makeMove(Color.WHITE, 3,4);
		gameBoard.makeMove(Color.WHITE, 4,3);

		GamePiece piece = gameBoard.getPieceAt(5,4);
		GamePiece piece2 = gameBoard.getPieceAt(4,5);
		
		assertTrue(operator.hasConnectionNorth(piece));
		assertTrue(operator.hasConnectionSouth(piece2));
		assertTrue(operator.hasConnectionEast(piece));
		assertTrue(operator.hasConnectionWest(piece2));
		
		GamePiece piece3 = gameBoard.getPieceAt(5,3);
		gameBoard.getBoard()[5][3].setColor(Color.WHITE);
		System.out.println(gameBoard.toString());
		assertTrue(operator.hasConnectionNE(piece3));
	
	}
	
	@Test
	public void testGetSurroundingPieces(){
		gameBoard.setUpBoard();
		gameBoard.makeMove(Color.BLACK, 5,3);

		gameBoard.makeMove(Color.WHITE, 3,4);
		
		gameBoard.makeMove(Color.WHITE, 4,3);
		
		System.out.println(gameBoard.toString());
		ArrayList<GamePiece> surPieces = operator.getSurroundingPieces(board[3][4]);
		
		assertEquals(surPieces.size(), 1);

		ArrayList<GamePiece> potentialMoves = operator.getPossibleMoves(Color.BLACK);

		assertEquals(potentialMoves.size(), 6);
		assertTrue(potentialMoves.contains(board[4][6]));
		assertTrue(potentialMoves.contains(board[3][5]));
	}
	@Test
	public void testNumberOfPiecesGained(){
		
		gameBoard.setUpBoard();
		ArrayList<GamePiece> potentialMoves = gameBoard.getBoardOperator().getPossibleMoves(Color.BLACK);

		int piecesGained = gameBoard.computePiecesGained(potentialMoves.get(0));
		assertEquals(piecesGained, 1);
		
		piecesGained = gameBoard.computePiecesGained(potentialMoves.get(1));
		assertEquals(piecesGained, 1);
		
		piecesGained = gameBoard.computePiecesGained(potentialMoves.get(2));
		assertEquals(piecesGained, 1);
		System.out.println(gameBoard.toString());
		
		piecesGained = gameBoard.computePiecesGained(potentialMoves.get(3));
		assertEquals(piecesGained, 1);
		
		gameBoard.getBoard()[3][5].setColor(Color.WHITE);
		gameBoard.getBoard()[3][5].setDirection(1,0);
		gameBoard.getBoard()[2][5].setColor(Color.WHITE);
		gameBoard.getBoard()[2][5].setDirection(1,0);
		potentialMoves = gameBoard.getBoardOperator().getPossibleMoves(Color.BLACK);

		System.out.println(gameBoard.toString());
		
		/*assertEquals(gameBoard.potentialPiecesGained(board[4][6]), 1);
		gameBoard.makeMove(Color.BLACK, 4, 6);
		//confirm that captured piece was actually turned over
		assertEquals(board[4][5].getColor(), Color.BLACK);
		
		//test that more than 1 piece can be gained at a time
		gameBoard.makeMove(color);
		gameBoard.makeMove(color);
		*/
		
		
	}
}
