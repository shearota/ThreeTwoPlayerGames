package CheckersGame;

import java.util.*;

public class TwoPlayerCheckers {

    static final int SIZE = 8;
    static char[][] board = new char[SIZE][SIZE];
    static int scoreX = 0;
    static int scoreO = 0;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) 
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
                break;
            }

            boolean moved = false;

            while(!moved) 
            {

                if(hasCapture(currentPlayer)) 
                {
                    System.out.println("You have a capture! You MUST jump.");
                }

                System.out.print("Enter move (row col newRow newCol): ");
                int row = safeInt();
                int col = safeInt();
                int newRow = safeInt();
                int newCol = safeInt();

                if(isValidMove(currentPlayer, row, col, newRow, newCol)) 
                {

                    char piece = board[row][col];
                    makeMove(piece, row, col, newRow, newCol);
                    printBoard();
                    moved = true;

                    // handling for multi-jump
                    while(canContinueCapture(board[newRow][newCol], newRow, newCol)) 
                    {

                        System.out.println("You must continue capturing with the SAME piece at (" + newRow + "," + newCol + ")");
                        System.out.print("Enter next jump (newRow newCol): ");

                        int r = safeInt();
                        int c = safeInt();

                        if(isValidCaptureMove(board[newRow][newCol], newRow, newCol, r, c)) 
                        {
                            makeMove(board[newRow][newCol], newRow, newCol, r, c);
                            newRow = r;
                            newCol = c;
                            printBoard();
                        } 
                        else 
                        {
                            System.out.println("Invalid capture! You MUST continue jumping.");
                        }
                    }

                } 
                else 
                {
                    System.out.println("Invalid move, try again.");
                }
            }

            // ends game if no pieces left
            if(countPieces('X') == 0 || countPieces('O') == 0)
                break;

            // switches player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        System.out.println("\nGame Over!");
        System.out.println("Final Score:");
        System.out.println("Player X: " + scoreX);
        System.out.println("Player O: " + scoreO);
    }

    // SAFE INTEGER INPUT (fix nextInt crash)
    static int safeInt() 
    {
        while(true) 
        {
            try 
            {
                return input.nextInt();
            } 
            catch(InputMismatchException e) 
            {
                System.out.print("Invalid input! Enter a number: ");
                input.next();
            }
        }
    }

    // initializes board
    static void initializeBoard() 
    {
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                board[i][j] = '.';

        // O pieces (top)
        for(int i = 0; i < 3; i++)
            for(int j = (i + 1) % 2; j < SIZE; j += 2)
                board[i][j] = 'O';

        // X pieces (bottom)
        for(int i = SIZE - 3; i < SIZE; i++)
            for(int j = (i + 1) % 2; j < SIZE; j += 2)
                board[i][j] = 'X';
    }

    static void printBoard() 
    {
        System.out.println("\n  0 1 2 3 4 5 6 7");
        for(int i = 0; i < SIZE; i++)
        {
            System.out.print(i + " ");
            for(int j = 0; j < SIZE; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    // king logic
    static boolean isKing(char p) 
    {
        return p == 'K' || p == 'Q';
    }

    static char ownerOf(char piece) 
    {
        if(piece == 'X' || piece == 'K') return 'X';
        return 'O';
    }

    // move validation
    static boolean isValidMove(char player, int row, int col, int newRow, int newCol) 
    {

        if(row < 0 || row >= SIZE || col < 0 || col >= SIZE ||
                newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE)
            return false;

        char piece = board[row][col];
        if(piece == '.') return false;
        if(ownerOf(piece) != player) return false;
        if(board[newRow][newCol] != '.') return false;

        boolean king = isKing(piece);

        int dir = (player == 'X') ? -1 : 1;
        int rowDiff = newRow - row;
        int colDiff = Math.abs(newCol - col);

        boolean forward = (rowDiff == dir);
        boolean backward = (rowDiff == -dir);

        boolean validStep =
                (king && Math.abs(rowDiff) == 1) ||
                        (!king && forward && Math.abs(rowDiff) == 1);

        boolean validCapture =
                (king && Math.abs(rowDiff) == 2) ||
                        (!king && Math.abs(rowDiff) == 2);

        // capture logic
        if(validCapture && colDiff == 2) 
        {
            int midRow = (row + newRow) / 2;
            int midCol = (col + newCol) / 2;
            char opp = (player == 'X') ? 'O' : 'X';
            char oppKing = (player == 'X') ? 'Q' : 'K';

            if(board[midRow][midCol] == opp || board[midRow][midCol] == oppKing)
                return true;
        }

        // normal move if no captures exist
        if(!hasCapture(player) && validStep && colDiff == 1)
            return true;

        return false;
    }

    // move execution (kinging)
    static void makeMove(char piece, int row, int col, int newRow, int newCol) 
    {

        int rowDiff = newRow - row;

        board[newRow][newCol] = piece;
        board[row][col] = '.';

        // captures
        if(Math.abs(rowDiff) == 2) 
        {
            int midRow = (row + newRow) / 2;
            int midCol = (col + newCol) / 2;

            char captured = board[midRow][midCol];
            if(captured == 'X' || captured == 'K') scoreO++;
            else scoreX++;

            board[midRow][midCol] = '.';
        }

        // king promotion
        if(newRow == 0 && ownerOf(piece) == 'X')
            board[newRow][newCol] = 'K';

        if(newRow == SIZE - 1 && ownerOf(piece) == 'O')
            board[newRow][newCol] = 'Q';
    }

    // checking captures
    static boolean hasCapture(char player) 
    {

        for(int r = 0; r < SIZE; r++) 
        {
            for(int c = 0; c < SIZE; c++) 
            {

                char piece = board[r][c];
                if(ownerOf(piece) != player) continue;

                if(canContinueCapture(piece, r, c))
                    return true;
            }
        }

        return false;
    }

    static boolean canContinueCapture(char piece, int row, int col) 
    {

        char player = ownerOf(piece);
        boolean king = isKing(piece);

        int[] dirs = king ? new int[]{-1, 1} : ((player == 'X') ? new int[]{-1} : new int[]{1});

        for(int d : dirs) 
        {
            int[][] jumps = {{2 * d, 2}, {2 * d, -2}};

            for(int[] j : jumps) 
            {
                int newRow = row + j[0];
                int newCol = col + j[1];

                if(newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE)
                    continue;

                int midRow = (row + newRow) / 2;
                int midCol = (col + newCol) / 2;

                char opp = (player == 'X') ? 'O' : 'X';
                char oppKing = (player == 'X') ? 'Q' : 'K';

                if(board[newRow][newCol] == '.' &&
                        (board[midRow][midCol] == opp || board[midRow][midCol] == oppKing))
                    return true;
            }
        }

        return false;
    }

    static boolean isValidCaptureMove(char piece, int row, int col, int newRow, int newCol) 
    {
        return isValidMove(ownerOf(piece), row, col, newRow, newCol)
                && Math.abs(newRow - row) == 2
                && Math.abs(newCol - col) == 2;
    }

    // move checker
    static boolean hasAnyMove(char player) 
    {
        for(int r = 0; r < SIZE; r++)
            for(int c = 0; c < SIZE; c++)
                if(ownerOf(board[r][c]) == player)
                    if(canMoveOrCapture(board[r][c], r, c))
                        return true;
        return false;
    }

    static boolean canMoveOrCapture(char piece, int row, int col) 
    {
        char player = ownerOf(piece);
        boolean king = isKing(piece);

        int[] dirs = king ? new int[]{-1, 1} : ((player == 'X') ? new int[]{-1} : new int[]{1});

        // normal moves
        for(int d : dirs) 
        {
            int[][] moves = {{d, 1}, {d, -1}};
            for(int[] m : moves) {
                int newRow = row + m[0];
                int newCol = col + m[1];
                if(newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE &&
                        board[newRow][newCol] == '.')
                    return true;
            }
        }

        // capture moves
        return canContinueCapture(piece, row, col);
    }

    static int countPieces(char player) 
    {
        int count = 0;

        for(int r = 0; r < SIZE; r++)
            for(int c = 0; c < SIZE; c++)
                if(ownerOf(board[r][c]) == player)
                    count++;

        return count;
    }
}

