import java.util.Scanner;

public class TicTacToe {

	public static void main(String[] args) {
		GameTree gameTree = new GameTree();
		boolean playerFirst = true;
		
		System.out.println("Welcome to Tic-Tac-Toe!");
	/*	System.out.println("Choose your sign (X or O):");
		String playerSign = (new Scanner(System.in)).next();
		if (playerSign.equals("X")) playerFirst = true;
		else playerFirst = false;*/
		
		gameTree.startGame();
		GameTree.GameStatus gameStatus = GameTree.GameStatus.STILL_PLAYING;
		
		while (gameStatus == GameTree.GameStatus.STILL_PLAYING) {
			System.out.println("Enter row: ");
			int row = (new Scanner(System.in)).nextInt();
			System.out.println("Enter column: ");
			int column = (new Scanner(System.in)).nextInt();
			
			gameStatus = gameTree.play(row, column);
		}
		
		System.out.println("Game over!");
		
	}

}
