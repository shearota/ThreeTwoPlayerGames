package ThreeTwoPlayerGames;

import java.util.Scanner;

public class Tictactoe {
	
	private Mark[][] board;
	private Scanner key;
	
	public enum Mark {
		X, O, EMPTY
	}
	
	public String toString(Mark mark)
	{
		if (mark.equals(Mark.X))
		{
			return "X ";
		}
		else if (mark.equals(Mark.O))
		{
			return "O ";
		}
		else if (mark.equals(Mark.EMPTY))
		{
			return "   ";
		}
		else
		{
			throw new IllegalArgumentException("Mark passed is not X, O, or EMPTY");
		}
	}
	
	public static void main(String[] args)
	{
		
	}
	
	// Return value is equal to the number of who won
	// 0 = tie
	// 1 = player1 won
	// 2 = player2 won
	// -1 = error
	public int startGame()
	{
		System.out.println("Player 1, choose X's or O's by typing X or O");
		key = new Scanner(System.in);
		String input = key.nextLine();
		char selection = input.toLowerCase().charAt(0);
		while (selection != 'x' && selection != 'o')
		{ // make sure selection is an x or an o
			input = key.nextLine();
			selection = input.toLowerCase().charAt(0);
			System.out.println("Please type X or O. You typed: " + selection);
		}
		Mark player1, player2; // initialize player1, 2
		if (selection == 'x')
		{ // if player 1 is x, then player 2 is o
			player1 = Mark.X;
			player2 = Mark.O;
		}
		else
		{ // and vice versa
			player1 = Mark.O;
			player2 = Mark.X;
		}
		// announce to the players
		System.out.println("Player 1 chose " + player1 + "'s. Player 2, you are " + player2 + "'s.");
		int turn = -1;
		while (!gameover())
		{
			if (turn == -1) 
				{ // initialized to -1, player 1 goes first
					turn = 1;
				}
			displayBoard();
			if (turn == 1)
				{ // do stuff for player 1 since its player 1's turn
					System.out.println("It's player 1 (" + player1 + ")'s turn. ");
					makeMove(player1);
					turn = 2; // switch the turn over to player 2 for next loop
				}
			else if (turn == 2)
				{ // do stuff for player 2 since it's player 2's turn
					System.out.println("It's player 2 (" + player2 + ")'s turn. ");
					makeMove(player2);
					turn = 1; // switch the turn over to player 1 for next loop
				}
			else
				{ // should never reach this. it's if turn is still 1 or 2
					key.close();
					throw new IllegalStateException();
				}
			Mark winner;
			if (checkWinner() != Mark.EMPTY) // if checkwinner == markEmpty then the game is undecided thus far, or tie.
				{ // if checkwinner is not empty then there is a decided winner
					winner = checkWinner(); // set winner
			if (winner == player1)
				{
					displayBoard();
					System.out.println("Winner is player 1!");
					key.close();
					return 1; // end program. we found a winner
				}
			else if (winner == player2)
				{
					displayBoard();
					System.out.println("Winner is player 2!");
					key.close();
					return 2; // end program. we found a winner
				}
			}
			// if the code reaches this line then we did not find a winner yet
			if (gameover()) // check if game is over
			{ // if game is over, and we already said we did not find a winner, then it must be a tie
				System.out.println("Tie. Game over.");
				displayBoard();
				key.close();
				return 0;
			}
			
		}
		
		key.close();
		return -1;
	}
	
	public Tictactoe()
	{
		board = new Mark[][] { {Mark.EMPTY, Mark.EMPTY, Mark.EMPTY}, 
											{Mark.EMPTY, Mark.EMPTY, Mark.EMPTY}, 
											{Mark.EMPTY, Mark.EMPTY, Mark.EMPTY} };
	}
	
	/**
	 * to do: print 1 2 3 to the left of the rows
	 */
	public void displayBoard()
	{
		System.out.println("     1  2  3");
		for (int row = 1; row <= 3; row++) // printing the rows
		{ 
			if (row == 2 || row == 3)
			{
				System.out.println("    ─┼─┼─");
			}
			for (int col = 1; col <= 3; col++)
			{
				if (col == 1)
				{
					System.out.print((row) + "  "  + toString(board[row-1][col-1]));
				}
				else 
				{
					System.out.print("|" + toString(board[row-1][col-1]));
				}
			}
			System.out.print("\n");
		}
	}
	
	public void makeMove(Mark mark)
	{
		String input;
		boolean flag = true;
		while (flag)
		{
			System.out.println("Select which column to place in by typing 1, 2, or 3 (left to right): ");
			input = key.nextLine();
			int col = Character.getNumericValue(input.charAt(0));
			System.out.println("Select which row to place in by typing 1, 2, or 3 (top to bottom): ");
			input = key.nextLine();
			int row = Character.getNumericValue(input.charAt(0));
			if (this.board[row-1][col-1] == Mark.EMPTY)
			{
				this.board[row-1][col-1] = mark;
				flag = false;
			}
			else
			{
				System.out.println("Pick a different spot. That one already has a " + this.board[row-1][col-1] + " in it.");
				displayBoard();
			}
			
		}
		
	}
	
	/*
	 * more like boardfull()
	 */
	public boolean gameover()
	{
		boolean gameover = true;
		for (int i = 0; i <= 2; i++)
		{
			if (board[i][0] == Mark.EMPTY || board[i][1] == Mark.EMPTY || board[i][2] == Mark.EMPTY)
			{
				gameover = false;
			}
		}
		return gameover;
	}
	
	public Mark checkWinner()
	{
		for (int i = 0; i <= 2; i++)
		{
			if (board[i][0] == Mark.O && board[i][1] == Mark.O && board[i][2] == Mark.O)
			{
				return Mark.O;
			}
			else if (board[i][0] == Mark.X && board[i][1] == Mark.X && board[i][2] == Mark.X)
			{
				return Mark.X;
			}
			
			if (board[0][i] == Mark.O && board[1][i] == Mark.O && board[2][i] == Mark.O)
			{
				return Mark.O;
			}
			else if (board[0][i] == Mark.X && board[1][i] == Mark.X && board[2][i] == Mark.X)
			{
				return Mark.X;
			}
			
			if (board[0][0] == Mark.X && board[1][1] == Mark.X && board[2][2] == Mark.X)
			{
				return Mark.X;
			}
			if (board[0][0] == Mark.O && board[1][1] == Mark.O && board[2][2] == Mark.O)
			{
				return Mark.O;
			}
			// check diagonals
		}
		
		
		
		if (gameover())
		{
			return Mark.EMPTY;
		}
		else
		{
			return null;
		}
	}
}
