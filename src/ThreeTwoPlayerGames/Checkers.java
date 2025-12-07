package ThreeTwoPlayerGames;


import java.util.*;


public class Checkers {
    static final int SIZE = 8;
    static char[][] board = new char[SIZE][SIZE];
    static int scoreX = 0;
    static int scoreO = 0;
    static Scanner input;


    public Checkers(Scanner k)
    {
    	input = k;
    }
    
    public int startGame()
    {
    	initializeBoard();
        printBoard();


        char currentPlayer = 'X';
        boolean gameOn = true;


        while(gameOn) 
        {
            System.out.println("\nPlayer " + currentPlayer + "'s turn.");
            if(!hasAnyMove(currentPlayer)) 
            {
                System.out.println("No moves available for " + currentPlayer + "!");
                gameOn = false;
                break;
            }


            boolean moved = false;
            while(!moved) 
            {
                if(hasCapture(currentPlayer)) 
                {
                    System.out.println("You have a capture! You must jump.");
                }
                System.out.print("Enter move (row col newRow newCol): ");
                int row = input.nextInt();
                int col = input.nextInt();
                int newRow = input.nextInt();
                int newCol = input.nextInt();


                if(isValidMove(currentPlayer, row, col, newRow, newCol)) 
                {
                    makeMove(currentPlayer, row, col, newRow, newCol);
                    printBoard();
                    moved = true;


                    // Continue multi-capture
                    while(canContinueCapture(currentPlayer, newRow, newCol)) 
                    {
                        System.out.println("You can continue capturing!");
                        System.out.print("Next jump (row col newRow newCol): ");
                        row = input.nextInt();
                        col = input.nextInt();
                        newRow = input.nextInt();
                        newCol = input.nextInt();


                        if(isValidMove(currentPlayer, row, col, newRow, newCol))
                            makeMove(currentPlayer, row, col, newRow, newCol);
                        else
                            break;
                        printBoard();
                    }


                } 
                else 
                {
                    System.out.println("Invalid move, try again.");
                }
            }


            if(countPieces('X') == 0 || countPieces('O') == 0)
                gameOn = false;
            else
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }


        System.out.println("\nGame Over!");
        System.out.println("Final Score:");
        System.out.println("Player X: " + scoreX);
        System.out.println("Player O: " + scoreO);
        if (scoreX > scoreO)
        {
        	return 1;
        }
        else if (scoreX < scoreO)
        {
        	return 2;
        }
        else
        {
        	return 0;
        }
    }


    static void initializeBoard() 
    {
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                board[i][j] = '.';


        // O pieces (top 3 rows)
        for(int i = 0; i < 3; i++)
            for(int j = (i + 1) % 2; j < SIZE; j += 2)
                board[i][j] = 'O';


        // X pieces (bottom 3 rows)
        for(int i = SIZE - 3; i < SIZE; i++)
            for(int j = (i + 1) % 2; j < SIZE; j += 2)
                board[i][j] = 'X';
    }


    static void printBoard() 
    {
        System.out.println("\n  0 1 2 3 4 5 6 7");
        for(int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for(int j = 0; j < SIZE; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }


    static boolean isValidMove(char player, int row, int col, int newRow, int newCol) 
    {
        if(row < 0 || row >= SIZE || col < 0 || col >= SIZE ||
            newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE)
            return false;


        if(board[row][col] != player || board[newRow][newCol] != '.')
            return false;


        int dir = (player == 'X') ? -1 : 1;
        int rowDiff = newRow - row;
        int colDiff = Math.abs(newCol - col);


        // Capture move
        if(Math.abs(rowDiff) == 2 && colDiff == 2) 
        {
            int midRow = (row + newRow) / 2;
            int midCol = (col + newCol) / 2;
            char opponent = (player == 'X') ? 'O' : 'X';
            if (board[midRow][midCol] == opponent)
                return true;
        }


        // Normal move only if no captures are available
        if(!hasCapture(player) && rowDiff == dir && colDiff == 1)
            return true;


        return false;
    }


    static void makeMove(char player, int row, int col, int newRow, int newCol) 
    {
        int rowDiff = newRow - row;
        int colDiff = Math.abs(newCol - col);
        board[newRow][newCol] = player;
        board[row][col] = '.';


        // If capture
        if(Math.abs(rowDiff) == 2 && colDiff == 2) 
        {
            int midRow = (row + newRow) / 2;
            int midCol = (col + newCol) / 2;
            board[midRow][midCol] = '.';
            if (player == 'X') scoreX++;
            else scoreO++;
        }
    }


    static boolean hasCapture(char player) 
    {
        int dir = (player == 'X') ? -1 : 1;
        char opponent = (player == 'X') ? 'O' : 'X';


        for(int i = 0; i < SIZE; i++) 
        {
            for(int j = 0; j < SIZE; j++) 
            {
                if(board[i][j] == player) 
                {
                    int[][] moves = {{dir * 2, 2}, {dir * 2, -2}};
                    for(int[] m : moves) 
                    {
                        int newRow = i + m[0];
                        int newCol = j + m[1];
                        if(newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) 
                        {
                            int midRow = (i + newRow) / 2;
                            int midCol = (j + newCol) / 2;
                            if (board[newRow][newCol] == '.' && board[midRow][midCol] == opponent)
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    static boolean canContinueCapture(char player, int row, int col)
    {
        int dir = (player == 'X') ? -1 : 1;
        char opponent = (player == 'X') ? 'O' : 'X';


        int[][] moves = {{dir * 2, 2}, {dir * 2, -2}};
        for(int[] m : moves) 
        {
            int newRow = row + m[0];
            int newCol = col + m[1];
            if(newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) 
            {
                int midRow = (row + newRow) / 2;
                int midCol = (col + newCol) / 2;
                if(board[newRow][newCol] == '.' && board[midRow][midCol] == opponent)
                    return true;
            }
        }
        return false;
    }


    static boolean hasAnyMove(char player) 
    {
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                if(board[i][j] == player)
                    if (canMoveOrCapture(player, i, j))
                        return true;
        return false;
    }


    static boolean canMoveOrCapture(char player, int row, int col) 
    {
        int dir = (player == 'X') ? -1 : 1;
        char opponent = (player == 'X') ? 'O' : 'X';


        // Regular moves
        int[][] normal = {{dir, 1}, {dir, -1}};
        for (int[] n : normal) {
            int newRow = row + n[0];
            int newCol = col + n[1];
            if(newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE && board[newRow][newCol] == '.')
                return true;
        }


        // Capture moves
        int[][] jumps = {{dir * 2, 2}, {dir * 2, -2}};
        for(int[] m : jumps) 
        {
            int newRow = row + m[0];
            int newCol = col + m[1];
            if(newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) 
            {
                int midRow = (row + newRow) / 2;
                int midCol = (col + newCol) / 2;
                if(board[newRow][newCol] == '.' && board[midRow][midCol] == opponent)
                    return true;
            }
        }
        return false;
    }


    static int countPieces(char player) 
    {
        int count = 0;
        for(int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == player)
                    count++;
        return count;
    }
}
