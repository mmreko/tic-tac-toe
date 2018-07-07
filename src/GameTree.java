import java.util.ArrayDeque;
import java.util.Stack;

public class GameTree {

	class Node {
		char[][] board;
		int minMaxValue;
		int level;
		Node[] nextMoves;
		int numChildren;
		boolean gameOver;
		
		public String toString() {
			String s = "";
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					s += " " + board[i][j] + " ";
					if (j < 2) s += "|";
				}
				s += "\n";
				if (i < 2) s += "-----------\n";
			}
			return s;
		}
	}
	
	public enum GameStatus {
		END_WITH_WIN, END_WITH_DRAW, STILL_PLAYING;
	}
	
	private Node root;
	Node currentNode;
	char currentPlayer = 'X';
	
	private void initializeRoot() {
		root = new Node();
		root.board = new char[3][3];
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				root.board[i][j] = ' ';
			}
		}
		root.nextMoves = new Node[9];
		root.level = 0;
		root.numChildren = 0;
		root.gameOver = false;
	}
	
	private char[][] copyBoard(char[][] board) {
		char[][] copy = new char[3][3];
		
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				copy[i][j] = board[i][j];
			}
		}
		
		return copy;
	}
	
	private void nextPlayer() {
		if (currentPlayer == 'X') currentPlayer = 'O';
		else currentPlayer = 'X';
	}
	
	private int max(Node node) {
		int max = Integer.MIN_VALUE;
		
		for (int i=0; i<node.numChildren; i++) {
			if (node.nextMoves[i].minMaxValue > max) {
				max = node.nextMoves[i].minMaxValue;
			}
		}
		
		return max;
	}
	
	private int min(Node node) {
		int min = Integer.MAX_VALUE;
		
		for (int i=0; i<node.numChildren; i++) {
			if (node.nextMoves[i].minMaxValue < min) {
				min = node.nextMoves[i].minMaxValue;
			}
		}
		
		return min;
	}
	
	public void printBoard() {
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
	
	private GameStatus checkGameStatus(char[][] board) {
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
	
	private void populateTree() {
		ArrayDeque<Node> queue = new ArrayDeque<>();
		Stack<Node> stack = new Stack<>();
		
		initializeRoot();
		queue.add(root);
		stack.push(root);
		
		while(!queue.isEmpty()) {
			Node current = queue.remove();
			
			if (current.gameOver) continue;
			
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					if (current.board[i][j] == ' ') {
						Node newNode = new Node();
						newNode.board = copyBoard(current.board);
						newNode.level = current.level + 1;
						if (newNode.level % 2 == 0) {
							newNode.board[i][j] = 'O';
						}
						else {
							newNode.board[i][j] = 'X';
						}
						newNode.nextMoves = new Node[9];
						
						GameStatus gameStatus = checkGameStatus(newNode.board);
						if (gameStatus == GameStatus.END_WITH_WIN) {
							if (newNode.level % 2 == 0) {
								newNode.minMaxValue = -1;
								newNode.gameOver = true;
							}
							else {
								newNode.minMaxValue = 1;
								newNode.gameOver = true;
							}
						}
						else if (gameStatus == GameStatus.END_WITH_DRAW) {
							newNode.minMaxValue = 0;
							newNode.gameOver = true;
						}
						
						current.nextMoves[current.numChildren] = newNode;
						current.numChildren++;
						
						queue.add(newNode);
						stack.push(newNode);
					}
				}
			}
		}
		
		while (!stack.isEmpty()) {
			Node current = stack.pop();
			
			if (!current.gameOver) {
				if (current.level % 2 == 0) {
					current.minMaxValue = max(current);
				}
				else {
					current.minMaxValue = min(current);
				}
			}
		}
		
	}
	
	public void startGame() {
		populateTree();
		currentNode = root;
		currentPlayer = 'X';
		printBoard();
	}
	
	public GameStatus play(int row, int column) {
		for (int i=0; i<currentNode.numChildren; i++) {
			if (currentNode.nextMoves[i].board[row][column] == currentPlayer) {
				currentNode = currentNode.nextMoves[i];
				break;
			}
		}
		
		printBoard();
		
		if (currentNode.gameOver) {
			GameStatus gameStatus = checkGameStatus(currentNode.board);
			return gameStatus;
		}
		
		nextPlayer();
		
		int min = currentNode.nextMoves[0].minMaxValue;
		Node next = currentNode.nextMoves[0];
		for (int i=1; i<currentNode.numChildren; i++) {
			if (currentNode.nextMoves[i].minMaxValue < min) {
				min = currentNode.nextMoves[i].minMaxValue;
				next = currentNode.nextMoves[i];
			}
		}
		
		currentNode = next;
		
		printBoard();
		
		if (currentNode.gameOver) {
			GameStatus gameStatus = checkGameStatus(currentNode.board);
			return gameStatus;
		}
		
		nextPlayer();
		
		return GameStatus.STILL_PLAYING;
	}
	
}
