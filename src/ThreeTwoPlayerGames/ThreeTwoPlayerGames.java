package ThreeTwoPlayerGames;

import java.util.Scanner;

public class ThreeTwoPlayerGames {

	static Scanner key;

		public static int overallPlayerOneWins = 0;
		public static int overallPlayerTwoWins = 0;
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
		        System.out.println("Current score: \nPlayer 1: " + overallPlayerOneWins + "\nPlayer 2: " +overallPlayerTwoWins);
		        String lineInput = key.nextLine();
		        if (lineInput.isEmpty()) continue;
		        int input = Character.getNumericValue(lineInput.charAt(0));
		        int result;

		        switch(input) {
		        case 1: 
		            System.out.println("Starting TicTacToe... ");
		            Tictactoe t = new Tictactoe();
		            result = t.startGame();
		            updateScores(result); 
		            break;
		        case 2:
		            System.out.println("Starting Hangman...");
		            Hangman h = new Hangman();
		            h.startGame(); 
		            break;
		        case 3:
		            System.out.println("Starting Checkers...");
		            Checkers c = new Checkers();
		            result = c.startGame(); 
		            updateScores(result); 
		            break;
		        case 4:
		            System.out.println("Exiting... ");
		            System.out.println("\n--- Final Overall Scores ---");
		            System.out.println("Player 1 Wins: " + overallPlayerOneWins);
		            System.out.println("Player 2 Wins: " + overallPlayerTwoWins);
		            System.out.println("----------------------------");
		            flag = false;
		            break;
		        }
		            
		    }
		}
		private static void updateScores(int result) {
		    if (result == 1) {
		        overallPlayerOneWins++;
		        System.out.println("Player 1 wins! Overall score updated.");
		    } else if (result == 2) {
		        overallPlayerTwoWins++;
		        System.out.println("Player 2 wins! Overall score updated.");
		    } else if (result == 0) {
		        System.out.println("The game was a tie.");
		    }
		}
	}