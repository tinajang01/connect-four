/** Name: Tina Jang
 * Course: ICS3U
 * Teacher: Mrs. McCaffery
 * Date: June 21, 2021
 * Description: Final Project, Connect 4
 */

package finalproject;

import java.util.Scanner;

public class Connect4 {

	/* 
	 * Method Name: main
	 * Description: main method, calls introduction
	 * Parameters: String[] args
	 * Returns: void
	 */
	public static void main(String[] args) 
	{				
		// initializes new scanner for menu options
		Scanner myInput = new Scanner(System.in);
		
		String []names = new String[2];
		
		// greets players and asks for names
		System.out.println ("\nWelcome to Connect 4!\nPlease enter both the players' names! ");
		for (int i = 0; i < names.length; i++)
		{
			names[i] = myInput.nextLine();
		}
		System.out.println ("Hello, " + names[0] + " and " + names [1]);

		// calls introduction
		introduction();
		
	} // end main method

	/* 
	 * Method Name: introduction
	 * Description: Introduction & rules for the game
	 * Parameters: n/a
	 * Returns: n/a
	 */
	public static void introduction() 
	{		
		// initializes new scanner for menu options
		Scanner myInput = new Scanner(System.in);
		
		// shows players the menu
		System.out.println ("\nPlease choose from the menu: \n1) View instructions. \n2) View rules. \n3) Start game. ");
		int response = myInput.nextInt();
		
		switch (response) {
		
		// option 1: shows instructions
		case 1:
			System.out.println ("Instructions: \n1) Decide which player goes first. Players will then alternate turns. View rules to decide which player will go first. \n2) When it is your turn, choose a row to drop your disk. \n3) Repeat until one player gets 4 disks in a row. ");
			introduction();
			break;
			
		// option 2: shows rules
		case 2:
			System.out.println ("Rules: \n1) Player RED will go first. \n2) There is a limited number of turns of 21 per player. When a player reaches max turns, the game will stop. If a player chooses an invalid column, they will be asked again for their turn, and the invalid turn will not count as one turn. ");
			introduction();
			break;
			
		// option 3: start game
		case 3:
			game();
			break;
			
		// default if none of the options are chosen
		default:
			System.out.println ("Invalid option. Taking you back to menu... ");
			introduction();
			break;
			
		} // end case switch
		
	} // end introduction

	/* 
	 * Method Name: createBoard
	 * Description: creates the board
	 * Parameters: int row, int column
	 * Returns: board
	 */
	public static String [][] createBoard(int row, int column) 
	{
		// initializes board
		String [][] board = new String[row][column];
		
		// initializes each row and column and the "inside"
		for (int r = 0; r < board.length; r++)
		{
			
			for (int c = 0; c < board[r].length; c++)
			{
					board[r][c] = " "; // makes the column empty inside					
			}
		} // end for
		
		return board;
		
	} // end createBoard method
	
	/* 
	 * Method Name: printBoard
	 * Description: prints board for the game
	 * Parameters: String[][] board
	 * Returns: n/a
	 */
	public static void printBoard(String[][] board)
	{
		// prints column types		
		System.out.println(" 1  2  3  4  5  6  7");
		
		// prints one row of the board at a time
		for (int row = 0; row <= board.length-1; row++) 
		{

			for (int column = 0; column <= board[0].length-1; column++) 
			{
				System.out.print("[" + board[row][column] + "]");
			}
			
			// prints an empty line
			System.out.println();
			
		} // end for
		
	} // end printBoard
	
	/* 
	 * Method Name: game
	 * Description: method where game happens
	 * Parameters: n/a
	 * Returns: n/a
	 */
	public static void game()
	{
		// initializes board methods and prints the board
		String [][] board = createBoard(6, 7);
		printBoard(board);
		
		// initializes scanner
		Scanner myInput = new Scanner (System.in);
				
		// initializes variables for the game
		String playerRed = "R";
		String playerYellow = "Y";
		int moves = 0;
		boolean connectFour = false;
				
		// starts the while loop for the game
		while (connectFour == false && moves < 42)
		{
			if (moves % 2 == 0)
			{
				dropRed(board, playerRed, playerYellow, connectFour, moves);
			}
			else
			{
				dropYellow(board, playerYellow, playerRed, connectFour, moves);
			}
			moves++;
			printBoard(board);
			
			// game over if players reach max turns
			if (moves == 42)
			{
				System.out.println ("Sorry, players have reached the max turns. Game over. ");
				connectFour = true;
				playAgain();
				break;
			}
			
			// checks if there is a connect 4
			if (connectionCheck (board, playerRed, playerYellow) != null)
			{
				if (connectionCheck (board, playerRed, playerYellow) == playerRed)
				{
					System.out.println ("Congratulations, player RED won! ");
				}
				else if (connectionCheck (board, playerRed, playerYellow) == playerYellow)
				{
					System.out.println ("Congratulations, player YELLOW won! ");
				}
				connectFour = true;
				playAgain();
				break;
			} // end if 

		} // end while
			
		//closes scanner
		myInput.close();
		
	} // end method game	
	
	/* 
	 * Method Name: checkEmpty
	 * Description: checks column to see if it is empty
	 * Parameters: int column, String[][] board
	 * Returns: true or false boolean
	 */
	public static boolean checkEmpty (int column, String[][] board)
	{
		// checks if column is available (column number)
		if (column < 0 || column >= board[0].length)
		{
			return false;
		}
		
		// checks if column is full (full column)
		if (board[0][column] != " ")
		{
			return false;
		}
		
		return true;
		
	}
	
	/* 
	 * Method Name: dropRed
	 * Description: process where computer asks player RED to drop a disk
	 * Parameters: String[][] board, String playerRed, String playerYellow, boolean connectFour, int moves
	 * Returns: int moves
	 */
	public static int dropRed (String[][] board, String playerRed, String playerYellow, boolean connectFour, int moves) 
	{
		// initializes scanner
		Scanner myInput = new Scanner (System.in);
		
		// initializes variables
		boolean empty;
		int choice;
		
		do 
		{
			// asks player for their move
			System.out.print ("Player RED. Please enter a column to drop your red disk: ");
			choice = myInput.nextInt() - 1;
			
			// checks that the board is empty
			empty = checkEmpty(choice, board);
			
			// prints error message if it is an invalid column
			if (empty == false)
			{
				System.out.println ("You can't drop a disk here, try again. ");
				choice = myInput.nextInt() - 1;
				moves++;
				break;
			} // end if

		} // end do		
		while (empty == false);
		{
			// stores the move in the column indicated
			for (int row = board.length-1; row >= 0; row--) 
			{
				if (board[row][choice] == " ")
				{
					board[row][choice] = playerRed;
					System.out.println ("You have chosen column " + (choice + 1));
					moves++;
					break;
				}
			}			
		} // end while
		
		// returns moves so that the game method can take the info
		return moves;
								
	} // end method dropRed
	
	/* 
	 * Method Name: dropYellow
	 * Description: process where computer asks player YELLOW to drop a disk
	 * Parameters: String[][] board, String playerYellow, String playerRed, boolean connectFour, int moves
	 * Returns: int moves
	 */
	public static int dropYellow (String[][] board, String playerYellow, String playerRed, boolean connectFour, int moves)
	{
		// initializes scanner
		Scanner myInput = new Scanner (System.in);
		
		// initializes variables
		boolean empty;
		int choice;
		
		do 
		{
			// asks player for their move
			System.out.print ("Player Yellow. Please enter a column to drop your yellow disk: ");
			choice = myInput.nextInt() - 1;
			
			// checks that the board is empty
			empty = checkEmpty(choice, board);
			
			// if column is full or invalid
			if (empty == false)
			{
				System.out.println ("You can't drop a disk here, try again. ");
				choice = myInput.nextInt() - 1;
				moves++;
				break;
			} // end if
			
		} // end do
		while (empty == false);
		{
			// stores the move in the column indicated
			for (int row = board.length-1; row >= 0; row--) 
			{
				if (board[row][choice] == " ")
				{
					board[row][choice] = playerYellow;
					System.out.println ("You have chosen column " + (choice + 1));
					moves++;
					break;
				}
			}
		} // end while
		
		// returns moves so that the game method can take the info
		return moves;
				
	} // end method dropYellow
	
	/* 
	 * Method Name: connectionCheck
	 * Description: checks the board if there is a connect 4
	 * Parameters: String[][] board, String playerRed, String playerYellow
	 * Returns: winner disk
	 */
	public static String connectionCheck (String[][] board, String playerRed, String playerYellow)
	{
		
		// checks connect 4 vertically
		for (int row = 0; row < board.length - 3; row++) 
		{
			for (int column = 0; column < board[0].length; column++) 
			{	
				if (board[row][column] == playerRed && board[row+1][column] == playerRed && board[row+2][column] == playerRed && board[row+3][column] == playerRed)
				{
					return board[row][column];
				}
				if (board[row][column] == playerYellow && board[row+1][column] == playerYellow && board[row+2][column] == playerYellow && board[row+3][column] == playerYellow)
				{
					return board[row][column];
				}
			}
		} // end checking vertically
		
		// checks connect 4 horizontally
		for (int row = 0; row < board.length; row++) 
		{
			for (int column = 0; column < board[0].length - 3; column++) {
				
				if (board[row][column] == playerRed && board[row][column+1] == playerRed && board[row][column+2] == playerRed && board[row][column+3] == playerRed) {
					return board[row][column+1];
				}
				if (board[row][column] == playerYellow && board[row][column+1] == playerYellow && board[row][column+2] == playerYellow && board[row][column+3] == playerYellow) {
					return board[row][column+1];
				}
			}
		} // end checking horizontally
		
		// checks connect 4 downward diagonal
		for (int row = 0; row < board.length - 3; row++)
		{
			for (int column = 0; column < board[0].length - 3; column++) {
				
				if (board[row][column] == playerRed && board[row+1][column+1] == playerRed && board[row+2][column+2] == playerRed && board[row+3][column+3] == playerRed) {
					return board[row][column];
				}
				else if (board[row][column] == playerYellow && board[row+1][column+1] == playerYellow && board[row+2][column+2] == playerYellow && board[row+3][column+3] == playerYellow) {
					return board[row][column];
				}
			}
		} // end checking downward diagonally
		
		// checks connect 4 upward diagonal
		for (int row = 3; row < board.length; row++) 
		{	
			for (int column = 0; column < board[0].length - 3; column++) {
				
				if (board[row][column] == playerRed && board[row-1][column+1] == playerRed && board[row-2][column+2] == playerRed && board[row-3][column+3] == playerRed) {
					return board[row][column];
				}	
				else if (board[row][column] == playerYellow && board[row-1][column+1] == playerYellow && board[row-2][column+2] == playerYellow && board[row-3][column+3] == playerYellow) {
					return board[row][column];
				}
			}
		} // end checking upward diagonally
		
		// returns false if there is no connect 4
		return null;
		
	} // end method connectionCheck

	/* 
	 * Method Name: playAgain
	 * Description: asks players if they want to play again
	 * Parameters: n/a
	 * Returns: n/a
	 */
	public static void playAgain()
	{
		// initializes scanner
		Scanner myInput = new Scanner (System.in);
		
		// asks player if they want to play again
		System.out.print ("Would you like to play again? Please enter Y for yes and N for no: ");
		String answer = myInput.nextLine();
		
		if (answer.equalsIgnoreCase("y"))
		{
			// takes player back to the introduction if they want to play again
			introduction();
		}
		else if (answer.equalsIgnoreCase("n"))
		{
			// thanks player for playing if they want to quit
			System.out.println ("Thank you for playing Connect 4! ");
		}
		else
		{
			// message for an invalid answer and takes players back to re-ask the question
			System.out.println ("Sorry, this is an invalid answer. Please try again. ");
			playAgain();
		} // end if statements
				
	} // end method playAgain
	
} // end class Connect4
