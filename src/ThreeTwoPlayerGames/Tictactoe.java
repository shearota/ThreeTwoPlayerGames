package ThreeTwoPlayerGames;

import java.util.Scanner;

public class Tictactoe {
	
	private Mark[][] board;
	private Scanner key;
	
	public enum Mark {
		X, O, EMPTY
	}
	
	public Tictactoe(Scanner k)
	{
		board = new Mark[][] { {Mark.EMPTY, Mark.EMPTY, Mark.EMPTY}, 
											{Mark.EMPTY, Mark.EMPTY, Mark.EMPTY}, 
											{Mark.EMPTY, Mark.EMPTY, Mark.EMPTY} };
		key = k;			
	}
	
	public String toString(Mark mark)
	{
		if (mark.equals(Mark.X))
		{
			return "X";
		}
		else if (mark.equals(Mark.O))
		{
			return "O";
		}
		else if (mark.equals(Mark.EMPTY))
		{
			return " ";
		}
		else
		{
			throw new IllegalArgumentException("Mark passed is not X, O, or EMPTY");
		}
	}
	
	
	// Return value is equal to the number of who won
	// 0 = tie
	// 1 = player1 won
	// 2 = player2 won
	// -1 = error
	public int startGame()
	{
		System.out.println("Player 1, choose X's or O's by typing X or O");
		String input = key.nextLine().trim();
		char selection = 'e';
		while (input.isEmpty() || (input.toLowerCase().charAt(0) != 'x' && input.toLowerCase().charAt(0) != 'o'))
		{
			if (input.isEmpty())
			{
				System.out.println("Selection cannot be empty. ");
			}
			else
			{
				System.out.println("Please type x or o. You typed: " + input);
			}
			input = key.nextLine().trim();
		}
		selection = input.toLowerCase().charAt(0);
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
					throw new IllegalStateException();
				}
			Mark winner = checkWinner();
			if (winner != Mark.EMPTY) // if checkwinner == markEmpty then the game is undecided thus far, or tie.
				{ // if checkwinner is not empty then there is a decided winner
				if (winner == player1)
					{
						displayBoard();
						System.out.println("Winner is player 1!");
						return 1; // end program. we found a winner
					}
				else if (winner == player2)
					{
						displayBoard();
						System.out.println("Winner is player 2!");
						return 2; // end program. we found a winner
					}
				}
			// if the code reaches this line then we did not find a winner yet
			if (gameover()) // check if game is over
			{ // if game is over, and we already said we did not find a winner, then it must be a tie
				displayBoard();
				return 0;
			}
			
		}
		
		return -1;
	}
	
	
	
	/**
	 * to do: print 1 2 3 to the left of the rows
	 */
	public void displayBoard()
	{
		System.out.println("   1 2 3");
		for (int row = 1; row <= 3; row++) // printing the rows
		{ 
			if (row == 2 || row == 3)
			{
				System.out.println("   ─┼─┼─");
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
			input = key.nextLine().trim();
			while (!(input.equals("1") || input.equals("2") || input.equals("3")))
			{
				System.out.println("Invalid input. Type 1, 2, or 3");
				input = key.nextLine().trim();
			}
			int col = Character.getNumericValue(input.charAt(0));
			System.out.println("Select which row to place in by typing 1, 2, or 3 (top to bottom): ");
			input = key.nextLine().trim();
			while (!(input.equals("1") || input.equals("2") || input.equals("3")))
			{
				System.out.println("Invalid input. Type 1, 2, or 3");
				input = key.nextLine().trim();
			}
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
		for (int row = 0; row <= 2; row++)
		{
			for (int col = 0; col <= 2; col++)
			{
				if (board[row][col] == Mark.EMPTY)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public Mark checkWinner()
	{
		// check diagonals
		// top left to bottom right
	    if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != Mark.EMPTY)
	    {
	        return board[0][0];
	    }
	    
	    // bottom left to top right
	    if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != Mark.EMPTY)
	    {
	        return board[0][2];
	    }
		
		for (int i = 0; i <= 2; i++)
		{
			// Check rows
	        if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != Mark.EMPTY)
	        {
	            return board[i][0];
	        }
	        // Check columns
	        if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != Mark.EMPTY)
	        {
	            return board[0][i];
	        }
			
			
		}
		
		
		
		return Mark.EMPTY;
	}
}