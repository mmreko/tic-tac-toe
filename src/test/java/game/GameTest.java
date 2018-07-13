package test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.GameLogic;
import game.GameTree;

public class GameTest {
	
	// If the tree is generated properly, the root will have minMaxValue = 0
	// This means that if all players play their best move every time, the game will end with a draw
	@Test
	public void testTreeGeneration() {
		GameTree gameTree = new GameTree();
		assertEquals(gameTree.getRoot().getMinMaxValue(), 0);
	}
	
	// Check if row win is detected
	@Test
	public void testStatusRowWin() {
		char[][] board = {
				{'X', 'X', 'X'}, 
				{'O', 'O', ' '}, 
				{' ', ' ', ' '}
		};
		GameLogic.GameStatus status = GameLogic.checkGameStatus(board);
		assertEquals(status, GameLogic.GameStatus.END_WITH_WIN);
	}
	
	// Check if column win is detected
	@Test
	public void testStatusColumnWin() {
		char[][] board = {
				{'O', 'X', 'X'}, 
				{'O', 'X', ' '}, 
				{'O', ' ', ' '}
		};
		GameLogic.GameStatus status = GameLogic.checkGameStatus(board);
		assertEquals(status, GameLogic.GameStatus.END_WITH_WIN);
	}
	
	// Check if diagonal wins are detected
	@Test
	public void testStatusDiagonalWin1() {
		char[][] board = {
				{'O', ' ', 'X'}, 
				{'O', 'X', ' '}, 
				{'X', ' ', ' '}
		};
		GameLogic.GameStatus status = GameLogic.checkGameStatus(board);
		assertEquals(status, GameLogic.GameStatus.END_WITH_WIN);
	}
	
	@Test
	public void testStatusDiagonalWin2() {
		char[][] board = {
				{'O', ' ', 'X'}, 
				{'X', 'O', ' '}, 
				{'X', ' ', 'O'}
		};
		GameLogic.GameStatus status = GameLogic.checkGameStatus(board);
		assertEquals(status, GameLogic.GameStatus.END_WITH_WIN);
	}
	
	// Check if draw is detected
	@Test
	public void testStatusDraw() {
		char[][] board = {
				{'O', 'X', 'O'}, 
				{'O', 'X', 'X'}, 
				{'X', 'O', 'O'}
		};
		GameLogic.GameStatus status = GameLogic.checkGameStatus(board);
		assertEquals(status, GameLogic.GameStatus.END_WITH_DRAW);
	}
	
	// Check if game in progress is detected
	@Test
	public void testStatusStillPlaying() {
		char[][] board = {
				{'O', ' ', ' '}, 
				{'O', 'X', ' '}, 
				{'X', ' ', ' '}
		};
		GameLogic.GameStatus status = GameLogic.checkGameStatus(board);
		assertEquals(status, GameLogic.GameStatus.STILL_PLAYING);
	}
	
	// Check if player switching works
	@Test
	public void testNextPlayer() {
		GameLogic.startGame();
		assertEquals(GameLogic.getCurrentPlayer(), 'X');
		
		GameLogic.nextPlayer();
		assertEquals(GameLogic.getCurrentPlayer(), 'O');
		
		GameLogic.nextPlayer();
		assertEquals(GameLogic.getCurrentPlayer(), 'X');
	}
	
	// Test game when human plays first (one possible case)
	@Test
	public void testHumanFirst() {
		int[][] humanMoves = {{0, 0}, {0, 1}, {2, 2}};
		
		GameLogic.startGame();
		GameLogic.GameStatus status = GameLogic.GameStatus.STILL_PLAYING;
		
		int currentMove = 0;
		
		while (status == GameLogic.GameStatus.STILL_PLAYING) {
			GameLogic.playHuman(humanMoves[currentMove][0], humanMoves[currentMove][1]);
			currentMove++;
			
			if (GameLogic.isGameOver()) {
				status = GameLogic.checkGameStatus();
				break;
			}
			
			GameLogic.nextPlayer();
			
			GameLogic.playCPUasO();
			
			if (GameLogic.isGameOver()) {
				status = GameLogic.checkGameStatus();
				break;
			}
			
			GameLogic.nextPlayer();
		}
		
		assertEquals(true, GameLogic.isGameOver());
		assertEquals(status, GameLogic.GameStatus.END_WITH_WIN);
		assertEquals(GameLogic.getCurrentPlayer(), 'O');
	}
	
	// Test game when CPU plays first (one possible case)
	@Test
	public void testCPUFirst() {
		int[][] humanMoves = {{1, 1}, {0, 2}, {1, 0}, {2, 1}};
		
		GameLogic.startGame();
		GameLogic.GameStatus status = GameLogic.GameStatus.STILL_PLAYING;
		
		int currentMove = 0;
		
		while (status == GameLogic.GameStatus.STILL_PLAYING) {
			GameLogic.playCPUasX();
			
			if (GameLogic.isGameOver()) {
				status = GameLogic.checkGameStatus();
				break;
			}
			
			GameLogic.nextPlayer();
			
			GameLogic.playHuman(humanMoves[currentMove][0], humanMoves[currentMove][1]);
			currentMove++;
			
			if (GameLogic.isGameOver()) {
				status = GameLogic.checkGameStatus();
				break;
			}
			
			GameLogic.nextPlayer();
		}
		
		assertEquals(true, GameLogic.isGameOver());
		assertEquals(status, GameLogic.GameStatus.END_WITH_DRAW);
	}

}
