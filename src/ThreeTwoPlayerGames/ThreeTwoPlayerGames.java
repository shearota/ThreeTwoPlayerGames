package ThreeTwoPlayerGames;

import java.util.Scanner;

public class ThreeTwoPlayerGames {

	static Scanner key;
	
	public static void main(String[] args)
	{
		key = new Scanner(System.in);
		boolean flag = true;
		while (flag)
		{
			System.out.println("Welcome to the Three Two Player Game software. ");
			System.out.println("Pick a game. ");
			System.out.println("1. TicTacToe");
			System.out.println("2. Hangman");
			System.out.println("3. Checkers");
			System.out.println("4. Quit");
			int input = Character.getNumericValue(key.nextLine().charAt(0));
			switch(input) {
			case 1: 
				System.out.println("Starting TicTacToe... ");
				Tictactoe t = new Tictactoe();
				t.startGame();
				break;
			case 2:
				System.out.println("Starting Hangman...");
				Hangman h = new Hangman();
				h.startGame();
				break;
			case 3:
				System.out.println("Starting Checkers...");
				Checkers c = new Checkers();
				c.startGame();
				break;
			case 4:
				System.out.println("Exiting... ");
				flag = false;
				break;
			}
				
		}
	}
	
}
