package game;

public class GameLogic {
	
	// Game Status indicators
	public enum GameStatus {
		END_WITH_WIN, END_WITH_DRAW, STILL_PLAYING;
	}
	
	private static GameTree gameTree;
	private static GameTree.Node currentNode;
	private static char currentPlayer = 'X';
	
	// Checks game status on a given board
	public static GameStatus checkGameStatus(char[][] board) {
		for (int i=0; i<3; i++) {
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] != ' ')
				return GameStatus.END_WITH_WIN;
		}
		
		for (int i=0; i<3; i++) {
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] != ' ')
				return GameStatus.END_WITH_WIN;
		}
		
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] != ' ')
			return GameStatus.END_WITH_WIN;
		
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] != ' ')
			return GameStatus.END_WITH_WIN;
		
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				if (board[i][j] == ' ')
					return GameStatus.STILL_PLAYING;
			}
		}
		
		return GameStatus.END_WITH_DRAW;
	}
	
	// Checks current game status
	public static GameStatus checkGameStatus() {
		return checkGameStatus(currentNode.board);
	}
	
	// Prints the board
	public static void printBoard() {
		System.out.println();
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				System.out.print(" " + currentNode.board[i][j] + " ");
				if (j < 2) System.out.print("|");
			}
			System.out.println();
			if (i < 2) System.out.println("-----------");
		}
		System.out.println();
	}
	
	// Initializes the game tree and starts the game
	public static void startGame() {
		gameTree = new GameTree();
		currentNode = gameTree.getRoot();
		currentPlayer = 'X';
		printBoard();
	}
	
	// Switches to the next player
	public static void nextPlayer() {
		if (currentPlayer == 'X') currentPlayer = 'O';
		else currentPlayer = 'X';
	}
	
	// Gets the current player
	public static char getCurrentPlayer() {
		return currentPlayer;
	}
	
	// Finds the best move for CPU to play as X
	public static void playCPUasX() {
		int max = currentNode.nextMoves[0].minMaxValue;
		GameTree.Node next = currentNode.nextMoves[0];
		for (int i=1; i<currentNode.numNextMoves; i++) {
			if (currentNode.nextMoves[i].minMaxValue > max || 
					(currentNode.nextMoves[i].minMaxValue == max && currentNode.nextMoves[i].gameOver)) {
				max = currentNode.nextMoves[i].minMaxValue;
				next = currentNode.nextMoves[i];
			}
		}
		currentNode = next;
	}
	
	// Finds the best move for CPU to play as O
	public static void playCPUasO() {
		int min = currentNode.nextMoves[0].minMaxValue;
		GameTree.Node next = currentNode.nextMoves[0];
		for (int i=1; i<currentNode.numNextMoves; i++) {
			if (currentNode.nextMoves[i].minMaxValue < min ||
					(currentNode.nextMoves[i].minMaxValue == min && currentNode.nextMoves[i].gameOver)) {
				min = currentNode.nextMoves[i].minMaxValue;
				next = currentNode.nextMoves[i];
			}
		}
		currentNode = next;
	}
	
	// Plays the next move chosen by the human player
	public static boolean playHuman(int row, int column) {
		if (currentNode.board[row][column] != ' ')
			return false;
		
		for (int i=0; i<currentNode.numNextMoves; i++) {
			if (currentNode.nextMoves[i].board[row][column] == currentPlayer) {
				currentNode = currentNode.nextMoves[i];
				break;
			}
		}
		
		return true;
	}
	
	// Checks if the game ended
	public static boolean isGameOver() {
		return currentNode.gameOver;
	}

}
