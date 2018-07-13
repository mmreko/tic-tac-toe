package game;
import java.util.Scanner;

public class TicTacToe {
	
	// Game flow when human player plays first
	private static void humanPlaysFirst() {
		GameLogic.startGame();
		GameLogic.GameStatus gameStatus = GameLogic.GameStatus.STILL_PLAYING;
		
		while (gameStatus == GameLogic.GameStatus.STILL_PLAYING) {
			boolean validMove = false;
			
			while (!validMove) {
				int row = 0;
				int column = 0;
				
				while (true) {
					System.out.println("Enter row: ");
					row = (new Scanner(System.in)).nextInt();
					if (row >= 0 && row <= 2) break;
					else System.out.println("Wrong input! Try again.");
				}
				
				while (true) {
					System.out.println("Enter column: ");
					column = (new Scanner(System.in)).nextInt();
					if (column >= 0 && column <= 2) break;
					else System.out.println("Wrong input! Try again.");
				}
				
				validMove = GameLogic.playHuman(row, column);
				if (!validMove) {
					System.out.println("Invalid move! Try again.");
				}
			}
			
			GameLogic.printBoard();
			
			if (GameLogic.isGameOver()) {
				gameStatus = GameLogic.checkGameStatus();
				break;
			}
			
			GameLogic.nextPlayer();
			
			GameLogic.playCPUasO();
			
			GameLogic.printBoard();
			
			if (GameLogic.isGameOver()) {
				gameStatus = GameLogic.checkGameStatus();
				break;
			}
			
			GameLogic.nextPlayer();
		}
		
		if (gameStatus == GameLogic.GameStatus.END_WITH_DRAW)
			System.out.println("Game over! It's a draw!");
		else 
			System.out.println("Game over! " + GameLogic.getCurrentPlayer() + " won!");
	}
	
	// Game flow when computer plays first
	private static void CPUPlaysFirst() {
		GameLogic.startGame();
		GameLogic.GameStatus gameStatus = GameLogic.GameStatus.STILL_PLAYING;
		
		while (gameStatus == GameLogic.GameStatus.STILL_PLAYING) {
			GameLogic.playCPUasX();
			
			GameLogic.printBoard();
			
			if (GameLogic.isGameOver()) {
				gameStatus = GameLogic.checkGameStatus();
				break;
			}
			
			GameLogic.nextPlayer();
			
			boolean validMove = false;
			
			while (!validMove) {
				int row = 0;
				int column = 0;
				
				while (true) {
					System.out.println("Enter row: ");
					row = (new Scanner(System.in)).nextInt();
					if (row >= 0 && row <= 2) break;
					else System.out.println("Wrong input! Try again.");
				}
				
				while (true) {
					System.out.println("Enter column: ");
					column = (new Scanner(System.in)).nextInt();
					if (column >= 0 && column <= 2) break;
					else System.out.println("Wrong input! Try again.");
				}
				
				validMove = GameLogic.playHuman(row, column);
				if (!validMove) {
					System.out.println("Invalid move! Try again.");
				}
			}
			
			GameLogic.printBoard();
			
			if (GameLogic.isGameOver()) {
				gameStatus = GameLogic.checkGameStatus();
				break;
			}
			
			GameLogic.nextPlayer();
		}
		
		if (gameStatus == GameLogic.GameStatus.END_WITH_DRAW)
			System.out.println("Game over! It's a draw!");
		else 
			System.out.println("Game over! " + GameLogic.getCurrentPlayer() + " won!");
	}

	public static void main(String[] args) {
		boolean endGame = false;
		
		while (!endGame) {
			boolean CPUFirst = false;
			
			System.out.println("Welcome to Tic-Tac-Toe!");
			
			while (true) {
				System.out.println("Choose your sign (X or O):");
				String playerSign = (new Scanner(System.in)).next();
				if (playerSign.equals("X") || playerSign.equals("x")) {
					CPUFirst = false;
					break;
				}
				else if (playerSign.equals("O") || playerSign.equals("o")) {
					CPUFirst = true;
					break;
				}
				else System.out.println("Invalid input! Try again.");
			}
			
			if (CPUFirst)
				CPUPlaysFirst();
			else
				humanPlaysFirst();
			
			while (true) {
				System.out.println("Start a new game? (Y or N): ");
				String newGame = (new Scanner(System.in)).next();
				if (newGame.equals("N") || newGame.equals("n")) {
					System.out.println("Goodbye!");
					endGame = true;
					break;
				}
				else if (newGame.equals("Y") || newGame.equals("y")) {
					endGame = false;
					break;
				}
				else System.out.println("Invalid input! Try again.");
			}
	
		}
		
	}

}
