import java.util.Scanner;

public class TicTacToe {
	
	private static void humanPlaysFirst() {
		GameLogic.startGame();
		GameLogic.GameStatus gameStatus = GameLogic.GameStatus.STILL_PLAYING;
		
		while (gameStatus == GameLogic.GameStatus.STILL_PLAYING) {
			System.out.println("Enter row: ");
			int row = (new Scanner(System.in)).nextInt();
			System.out.println("Enter column: ");
			int column = (new Scanner(System.in)).nextInt();
			
			GameLogic.playHuman(row, column);
			
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
			
			System.out.println("Enter row: ");
			int row = (new Scanner(System.in)).nextInt();
			System.out.println("Enter column: ");
			int column = (new Scanner(System.in)).nextInt();
			
			GameLogic.playHuman(row, column);
			
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
			System.out.println("Choose your sign (X or O):");
			String playerSign = (new Scanner(System.in)).next();
			if (playerSign.equals("X") || playerSign.equals("x")) CPUFirst = false;
			else CPUFirst = true;
			
			if (CPUFirst)
				CPUPlaysFirst();
			else
				humanPlaysFirst();
			
			System.out.println("Start a new game? (Y or N): ");
			String newGame = (new Scanner(System.in)).next();
			if (newGame.equals("N") || newGame.equals("n")) endGame = true;
		}
		
	}

}
