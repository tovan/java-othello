package neuburger.othello;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class TestGameBoard {

	@Test
	public void testOnlyValidMovesAreMade() {
		GameBoard gameBoard = new GameBoard();
		GamePiece[][] board = gameBoard.getBoard();

		board[2][2] = new GamePiece(2, 2, Color.BLACK);
		gameBoard.makeMove(Color.CYAN, 2, 2);
		// make sure that piece already taken was not overridden by new piece
		// and new color
		assertEquals(board[2][2].getColor(), Color.BLACK);

		gameBoard.makeMove(Color.CYAN, 3, 3);
		// make sure that an invalid move is not taken
		assertEquals(board[3][3].getColor(), null);

	}

	@Test
	public void testFlipPieces() {
		GameBoard board = new GameBoard();
		board.setUpBoard();
		System.out.println(board.toString());
		board.makeComputerMove(Color.BLACK);

		assertTrue(board.getBoard()[4][5].getColor() == Color.BLACK);
		System.out.println(board.toString());
	}
}
