package game;
import java.util.ArrayDeque;
import java.util.Stack;

public class GameTree {

	// One node of the game tree
	public class Node {
		char[][] board; // current status of the board
		int minMaxValue; // value for the minimax algorithm
		int level; // level of the node
		Node[] nextMoves; // possible next moves 
		int numNextMoves; // number of possible next moves
		boolean gameOver; // flag indicating that the game ends in this node
		
		public int getMinMaxValue() {
			return minMaxValue;
		}
		
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
	
	private Node root;
	
	// Initializes the root of the tree (empty board at the beginning of the game)
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
		root.numNextMoves = 0;
		root.gameOver = false;
	}
	
	// Copies the board
	private char[][] copyBoard(char[][] board) {
		char[][] copy = new char[3][3];
		
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				copy[i][j] = board[i][j];
			}
		}
		
		return copy;
	}
	
	// Finds the next move with maximum minimax value
	private int max(Node node) {
		int max = Integer.MIN_VALUE;
		
		for (int i=0; i<node.numNextMoves; i++) {
			if (node.nextMoves[i].minMaxValue > max) {
				max = node.nextMoves[i].minMaxValue;
			}
		}
		
		return max;
	}
	
	// Finds the next move with minimum minimax value
	private int min(Node node) {
		int min = Integer.MAX_VALUE;
		
		for (int i=0; i<node.numNextMoves; i++) {
			if (node.nextMoves[i].minMaxValue < min) {
				min = node.nextMoves[i].minMaxValue;
			}
		}
		
		return min;
	}
	
	// Generates the game tree before the game starts
	public GameTree() {
		ArrayDeque<Node> queue = new ArrayDeque<>(); // used for generating the tree
		Stack<Node> stack = new Stack<>(); // used for computing minimax values
		
		initializeRoot();
		queue.add(root);
		stack.push(root);
		
		// Generates the tree top down
		while(!queue.isEmpty()) {
			Node current = queue.remove();
			
			// If the game is over, there is no need to compute possible next moves
			if (current.gameOver) continue;
			
			// Generate all possible next moves
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
						
						// If the game is over (leaf node), set the minimax value
						GameLogic.GameStatus gameStatus = GameLogic.checkGameStatus(newNode.board);
						if (gameStatus == GameLogic.GameStatus.END_WITH_WIN) {
							if (newNode.level % 2 == 0) {
								newNode.minMaxValue = -1;
								newNode.gameOver = true;
							}
							else {
								newNode.minMaxValue = 1;
								newNode.gameOver = true;
							}
						}
						else if (gameStatus == GameLogic.GameStatus.END_WITH_DRAW) {
							newNode.minMaxValue = 0;
							newNode.gameOver = true;
						}
						
						current.nextMoves[current.numNextMoves] = newNode;
						current.numNextMoves++;
						
						queue.add(newNode);
						stack.push(newNode);
					}
				}
			}
		}
		
		// Computes minimax values bottom up
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
	
	// Getter method for the root
	public Node getRoot() {
		return root;
	}
	
}
