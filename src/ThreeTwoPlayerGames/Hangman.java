package ThreeTwoPlayerGames;

import java.util.Scanner;

public class Hangman {

	Scanner scanner;
	
	public int startGame()
	{
		scanner = new Scanner(System.in);

		int playerOne = 1;
		int playerTwo = 2;
        int playerOneWins = 0;
        int playerTwoWins = 0;

        boolean playing = true;

        while (playing) {

            System.out.println("\n1. Play Hangman");
            System.out.println("2. Show Scores");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
                scanner.nextLine();
                continue;
            }
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {

                System.out.print("Who will write the sentence? Enter 1 for player 1 and 2 for player 2: ");
                
                int writer = 0;
                
                while (writer != playerOne && writer != playerTwo) {

                    if (scanner.hasNextInt()) {
                        writer = scanner.nextInt();
                        scanner.nextLine(); 
                    } else {
                        scanner.nextLine();
                    }

                    if (writer != playerOne && writer != playerTwo) {
                        System.out.print("Enter a valid choice (1 or 2): ");
                    }
                }

				int guesser = writer == playerOne ? playerTwo : playerOne;
                System.out.println("Player " + writer + ", type your sentence:");

                String sentence = scanner.nextLine().toLowerCase();

                for (int i = 0; i < 40; i++) System.out.println(); 

                char[] guessedLetters = new char[26];
                int guessedCount = 0;
                int misses = 0;
                final int MAX_MISSES = 6;

                while (misses < MAX_MISSES && !checkWinner(sentence, guessedLetters, guessedCount)) {

                    drawScreen(sentence, guessedLetters, guessedCount, misses, MAX_MISSES);
                    System.out.print("Player " + guesser + ", enter a letter guess: ");
                    
                    String input = scanner.nextLine().toLowerCase();

                    if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                        System.out.println("Invalid input. Guess one letter.");
                        continue;
                    }

                    char guess = input.charAt(0);

                    if (alreadyGuessed(guessedLetters, guessedCount, guess)) {
                        System.out.println("You already guessed that!");
                        continue;
                    }

                    guessedLetters[guessedCount++] = guess;

                    if (sentence.indexOf(guess) == -1) {
                        misses++;
                        System.out.println("Wrong guess! Misses: " + misses + "/" + MAX_MISSES);
                    } else {
                        System.out.println("Correct guess!");
                    }

                }

                drawScreen(sentence, guessedLetters, guessedCount, misses, MAX_MISSES);

                // Check for win/loss and update scores
                if (checkWinner(sentence, guessedLetters, guessedCount)) {
                    System.out.println("Player " + guesser + " wins! The sentence was: " + sentence);
                    // FIX 5: Use == for comparing integers, not .equals()
                    if (guesser == playerOne) playerOneWins++;
                    else playerTwoWins++;
                } 
                else {
                    System.out.println("The word was not guessed in time. Player " + writer + " wins! The sentence was: " + sentence);
                    // FIX 6: Use == for comparing integers, not .equals()
                    if (writer == playerOne) playerOneWins++;
                    else playerTwoWins++;
                }

            } else if (choice == 2) {

                System.out.println("\n Current Scores:");
                System.out.println("Player " + playerOne + ": " + playerOneWins);
                System.out.println("Player " + playerTwo + ": " + playerTwoWins);

            } else if (choice == 3) {

                System.out.println("Thanks for playing!");
                playing = false;

            } else {

                System.out.println("Invalid choice. Try again.");

            }

        }

		return 0; 
	}
	
	   public static void drawScreen(String sentence, char[] guessed, int guessedCount, int misses, int max) {

	        System.out.println("\nWord:");

	        for (int i = 0; i < sentence.length(); i++) {

	            char c = sentence.charAt(i);

	            if (c == ' ') {

	                System.out.print(" ");

	            } else if (isGuessed(guessed, guessedCount, c)) {

	                System.out.print(c);

	            } else {

	                System.out.print("_");

	            }

	        }

	        System.out.println("\nMisses: " + misses + "/" + max);

	        System.out.print("Guessed letters: ");

	        for (int i = 0; i < guessedCount; i++) {

	            System.out.print(guessed[i] + " ");

	        }

	        System.out.println();

	    }

	    public static boolean isGuessed(char[] guessed, int guessedCount, char c) {

	        for (int i = 0; i < guessedCount; i++) {

	            if (guessed[i] == c) return true;

	        }

	        return false;

	    }

	    public static boolean checkWinner(String sentence, char[] guessed, int guessedCount) {

	        for (int i = 0; i < sentence.length(); i++) {

	            char c = sentence.charAt(i);

	            if (c != ' ' && !isGuessed(guessed, guessedCount, c)) return false;

	        }

	        return true;

	    }

	    public static boolean alreadyGuessed(char[] guessed, int guessedCount, char guess) {

	        for (int i = 0; i < guessedCount; i++) {

	            if (guessed[i] == guess) return true;

	        }

	        return false;

	    }

	    }  
