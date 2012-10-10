package neuburger.othello;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;


public class GameBoard {
	
//	private LinkedList columnsList;
	private ArrayList<ArrayList<GamePiece>> cList;
	private int[][] potentialPieces;
	
	public GameBoard(){
		this.cList = new ArrayList<ArrayList<GamePiece>>();
		
		for(int i = 0; i<8; i++){
			cList.add(new ArrayList<GamePiece>());
			for (int j = 0; j<8; j++){
				ArrayList<GamePiece> tempList = cList.get(i);
				tempList.add(new GamePiece());
			}
		}
		potentialPieces = new int[8][2];
		potentialPieces[0][0] = 1;	potentialPieces[0][1] = 1;
		potentialPieces[1][0] = 1;  potentialPieces[1][1] = 0;
		potentialPieces[2][0] = 1;  potentialPieces[2][1] = -1;
		potentialPieces[3][0] = 0;	potentialPieces[3][1] = 1;
		potentialPieces[4][0] = 0;  potentialPieces[4][1] = -1;
		potentialPieces[5][0] = -1; potentialPieces[5][1] = 1;
	    potentialPieces[6][0] = -1; potentialPieces[6][1] = 0;
	    potentialPieces[7][0] = -1; potentialPieces[7][1] = -1;
	}
	public ArrayList<GamePiece> findMoves(Color color){
		ArrayList<Point> currentBoxes = new ArrayList<Point>();
		ArrayList<GamePiece> surroundingPieces = new ArrayList<GamePiece>();
		ArrayList<GamePiece> possibleMoves = new ArrayList<GamePiece>();
		GamePiece possibleMove;
		Color otherColor = color == Color.white? Color.black: Color.white;
		
		currentBoxes = getCurrentBoxes(color);
		
		for(Point P: currentBoxes){
			System.out.println(P.x +" "+ P.y);
		}
		for(Point p: currentBoxes){
			surroundingPieces = getSurroundingPieces(p, otherColor);
			if (surroundingPieces.size() > 0)
				for(GamePiece P: surroundingPieces){
					
					possibleMove = determineEdgeBox(P);
					System.out.println("your possible move is: ");
					if(possibleMove != null){
						System.out.println(possibleMove != null? possibleMove.getLocation().x+" "+ possibleMove.getLocation().y: "null");
						possibleMoves.add(possibleMove);
					}
				}
		}
		
		/*
		for (GamePiece piece: surroundingPieces){
			possibleMove = determineEdgeBox(piece);
			System.out.println("your possible move is: ");
			System.out.println(possibleMove.getLocation().x+" "+ possibleMove.getLocation().y);
			//possibleMoves 
		}
		
		for(Point P: possibleMoves){
			System.out.println(P.x +" "+ P.y);
		}*/
		return possibleMoves;
		
	}

	private GamePiece determineEdgeBox(GamePiece currentPiece) {
		ArrayList<GamePiece> possibleMoves = null;
		Color currentColor = null;
		GamePiece receivedPiece = currentPiece; //cache pice recieved in 
		Point direction = currentPiece.getDirection();	//the currentPiece gets reset, as does its direction
		do{
			int newX = currentPiece.getLocation().x + direction.x;
			int newY = currentPiece.getLocation().y + direction.y;
//			System.out.println("curr: "+ currentPiece + "direction"+direction);
			if(this.existsInBoard(new Point(newX, newY))){
				//once direction is applied, can end up with a piece off of the board
				currentColor = ((GamePiece) cList.get(newX-1).get(newY-1)).getColor();
				currentPiece = new GamePiece(newX, newY, currentColor);
			}
		} while(currentColor != null && currentColor.equals(currentPiece.getColor()));	
		//color of current location is not equal to that of the received in piece, and is an edge piece!
		return currentPiece == receivedPiece? null: currentPiece;
	}

	private ArrayList<GamePiece> getSurroundingPieces(Point point, Color otherColor) {
//		point = new Point(point.x-1, point.y-1);
		ArrayList<GamePiece> surroundingPiecesList = new ArrayList<GamePiece>();
		//go through all points in potential pieces list, if has a piece of a different color
			//then add it to surroundingPieces list
		Point temp = new Point(point.x, point.y);
		int i; 
		for(i=0; i<potentialPieces.length; i++){
				temp.y = point.y + potentialPieces[i][0];
				temp.x = point.x + potentialPieces[i][1];
				if(existsInBoard(temp)){
					Color currentColor = ((GamePiece)cList.get(temp.x-1).get(temp.y-1)).getColor();
					//if I use .equals() below, I will have to first check for null colors (empty spaces)
					if(currentColor == otherColor){
						GamePiece gamePiece = new GamePiece(temp.x, temp.y, currentColor);
						surroundingPiecesList.add(gamePiece);
						//store direction taken to get to this point
						
						gamePiece.setDirection(new Point(potentialPieces[i][1], potentialPieces[i][0]));
						}
				}
		}
		return surroundingPiecesList;
		
	}

	private boolean existsInBoard(Point point) {
		if(point.x <= 7 && point.x >0 && point.y <= 7 && point.y>0)
			return true;
		else
			return false;
	}
	private ArrayList<Point> getCurrentBoxes(Color color) {
		ArrayList<Point> currentBoxes = new ArrayList<Point>();
		for(int ctr=0; ctr<cList.size(); ctr++){
			for(int i = 0; i<cList.get(ctr).size(); i++){
				GamePiece currentPiece = cList.get(ctr).get(i);
				if(currentPiece.getColor() == color){
					Point temp = new Point(currentPiece.getLocation().x+1,currentPiece.getLocation().y+1);
					currentBoxes.add(temp);
				}
			}
	}
		return currentBoxes;
	}
	private void makeMove(Color color, int x, int y) {
		//computerize the variables - make them 0 based 
		x--;
		y--;
		Color colorofDesiredSpot = ((GamePiece)cList.get(x).get(y)).getColor();
		if(colorofDesiredSpot == Color.BLACK || colorofDesiredSpot == Color.WHITE)
		{
			System.out.println("sorry, but spot is already taken!");
		}
		else
		{
			this.cList.get(x).set(y, new GamePiece(x, y, color));	
		}
		
	}	
	private void makeComputerMove(Color color){
		//must be black
		//find a smart move and add piece there
		ArrayList<GamePiece> potentialMoves = this.findMoves(color);
		if(potentialMoves.size() == 0){
			System.out.println("no moves can be made");
		}
		else{
			GamePiece nextMove = potentialMoves.get(0);
			this.makeMove(Color.BLACK, nextMove.getLocation().x, nextMove.getLocation().y);
		}
	}
	public String toString(){
		StringBuffer board =  new StringBuffer();
		for(int ctr=0; ctr< 8; ctr++){
			for(int i = 0; i<cList.get(ctr).size(); i++){
				Object currentBox = cList.get(ctr).get(i);
				
				if(((GamePiece)currentBox).getColor() == null){
					board.append("-");
				}
				
				else if(((GamePiece)currentBox).equals(Color.WHITE)){
					board.append("W");
				}
				else if (((GamePiece)currentBox).equals(Color.BLACK)){
					board.append("B");
				}
				
			}
			board.append("\n");
		}
		return board.toString();
	}
	public static void main(String[] args) {
		//bug found - black moves - computer moves are going into spots athat are not legit. ex; 3, 4 and 3,5 when there
		//a white in 3, 2
		GameBoard board = new GameBoard();
		String answer;
		
		do{
			Scanner input = new Scanner(System.in);
			System.out.println("what action would you like to perform? "+
					"\nEnter l to list all possible moves,\nm to make a move \nand q to quite playing ");
			
			answer = input.next();
			switch (answer.charAt(0)) {
			case 'l':
			case 'L':
				System.out.println(board);
				break;
			case 'm':
			case 'M':
				board.makeMove(Color.BLACK, 2, 3);
				System.out.println("enter location to move to in format x y:");
				int x = input.nextInt();
				int y = input.nextInt();
				board.makeMove(Color.WHITE, x, y );
				//every time person makes move, computer should too
				board.makeComputerMove(Color.BLACK);
				break;
			case 'p':
			case 'P':
				System.out.println(board);
				break;
			case 'q':
			case 'Q':
				System.out.println(board);
				System.exit(0);
			case 't':
				board.makeMove(Color.WHITE, 1,1 );
				board.makeMove(Color.WHITE, 5,5);
				board.makeMove(Color.WHITE, 5,7 );
				board.makeMove(Color.BLACK, 2,2 );
				board.makeMove(Color.BLACK, 2,3 );
				board.makeMove(Color.BLACK, 4,7);

				board.makeMove(Color.BLACK, 3,3);
				System.out.println("current locations:");
				board.findMoves(Color.BLACK);
				System.out.println(board.toString());
				break;
			default:
				break;
			}
		}while(!(answer.charAt(0) =='q'));
		
	}
}