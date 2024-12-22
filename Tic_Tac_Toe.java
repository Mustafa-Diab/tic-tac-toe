import java.util.Scanner;

public class Tic_Tac_Toe 
{
    // Keep track of how many times each player wins and ties
    public static int playerXWin = 0;
    public static int playerOWin = 0;
    public static int tie = 0;
    
    // Keep track of the number of turns taken to check for tie
    public static int counter = 0;
    
    // Store the index of the chosen position
    public static int optionPlace = 0;
    
    // Keep track of numbers already guessed to avoid repetition
    public static String numGuessAlready = "";
    
    // Store the available options to place 'X's and 'O's
    public static String[] placeOption = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    
    // Initiates the scanner for the entire game
    public static Scanner scanner = new Scanner(System.in);
    
    // Keep track of whether the game has ended or not
    public static boolean gameEnd = false;

    // Main method to start the program
    public static void main(String[] args) 
    {
        // Display the main menu
        MAIN_MENU();
        
        // Close the scanner after the entire program has finished executing
        scanner.close();
    }

    // Display the main menu
    public static void MAIN_MENU() 
    {
        // Display the game's title
        DISPLAY_TITLE();
        
        // Prompt the user to choose between tutorial and play
        System.out.print("\nWelcome to Tic Tac Toe!!!\n\nChoose one of the options below: \n - Tutorial\n - Play\n\n");
    
        // Read the user's choice
        String choice = scanner.nextLine().toLowerCase().trim();
    
        // Validate user input
        while (!(choice.equals("tutorial") || choice.equals("play") || choice.equals("0")))
        {
            System.out.print("\nInvalid choice. Please choose one of the options. ");
            choice = scanner.nextLine().toLowerCase().trim();
        }
    
        // Call the appropriate method based on user's choice
        if (choice.equals("tutorial")) 
        {
            CLEAR_SCREEN();
            TUTORIAL();
        } 
        if (choice.equals("play"))
        {
        	CLEAR_SCREEN();
            PLAY_GAME();
        }
    }

    // Display the game tutorial
    public static void TUTORIAL() 
    {
        // Display game rules and board layout
        System.out.println("\nHow to Play Tic Tac Toe:\n");
        System.out.println("- Tic Tac Toe is a two-player game played on a 3x3 grid.");
        System.out.println("- Players take turns placing their symbol ('X' or 'O') in an empty square on the grid.");
        System.out.println("- The first player to get three of their symbols in a row, column, or diagonal wins the game.");
        System.out.println("- If all 9 squares are filled without any player achieving three in a row, the game is a tie.");
        System.out.println("\n- Here's the layout of the Tic Tac Toe board:\n");
        // Display the game board layout
        DISPLAY_BOARD(placeOption);
        
        System.out.print("\nPress Enter to return to the main menu. ");
        scanner.nextLine();
        CLEAR_SCREEN();
        MAIN_MENU();
    }

    // Start the game
    public static void PLAY_GAME() 
    {
        DISPLAY_BOARD(placeOption); // Display the game board
    
        // Main game loop
        while (!gameEnd) 
        {
            TAKE_TURN("X"); // Player X's turn
            
            if (gameEnd)
                break;
            
            TAKE_TURN("O"); // Player O's turn
        }
        
        // Prompt the user to play again
        System.out.print("\nWould you like to play again? (yes/no) ");
        String choicePlayAgain = scanner.nextLine().toLowerCase().trim();
         
        // Validate User Input
        while (!(choicePlayAgain.startsWith("y") || choicePlayAgain.startsWith("n")))
        {
            // Prompt the user to play again
            System.out.print("\nInvalid input. Please enter yes or no. ");
            choicePlayAgain = scanner.nextLine().toLowerCase().trim();
        }
        
        // Handle the user's choice
        if (choicePlayAgain.startsWith("y")) 
        {
            // Reset game variables
            RESET_GAME();
            CLEAR_SCREEN();
            MAIN_MENU();
        } 
        else 
        {
            // Display game statistics and exit
            DISPLAY_STATISTICS();
            System.out.println("\nHave a great day.");
            System.exit(0);
        }
    }
    
    // Take player's turn
    public static void TAKE_TURN(String playerSymbol) 
    {
        // Process player's turn
        System.out.println("\nType 'exit' to leave the game early.");
        PLAYER_TURN(playerSymbol);
        
        if (CHECK_FOR_WIN(placeOption, playerSymbol)) 
        {
            gameEnd = true; // If Player wins
            return;
        }
    
        if (counter == 9) // 9 rounds without anyone winning
        {
            System.out.println("\nIt's a tie.");
            tie++;
            gameEnd = true;
            return;
        }
    }
    
    // Player's turn
    public static void PLAYER_TURN(String playerChoice)
    {
        // Prompt the player to choose a position
        do
        {
            System.out.print("\nPlayer " + playerChoice + ": Select a number between 1-9 to place your option, or type 'exit' to leave the game: ");
            String input = scanner.nextLine().toLowerCase().trim();
    
            // If the input is 'exit', return to the main menu
            if (input.equals("exit")) 
            {
                CLEAR_SCREEN();
                RESET_GAME();
                MAIN_MENU();
                return;
            }
    
            // Check if the input is an integer
            try 
            {
                optionPlace = Integer.parseInt(input);
            } 
            catch (NumberFormatException e) 
            {
                // If not, display an error message and continue the loop
                System.out.println("Invalid input! Please choose a number between 1 and 9 or type 'exit' to leave the game.");
                continue;
            }
            
            // Validate the chosen position
            if (optionPlace < 1 || optionPlace > 9)
            {
                System.out.println("Invalid input! Please choose a number between 1 and 9 or type 'exit' to leave the game.");
                continue;
            }
            
            // Confirming that the number was not chosen before 
            if (numGuessAlready.contains(Integer.toString(optionPlace))) 
            {
                System.out.println("This number has already been chosen. Please choose a different number.");
                continue;
            }
            
            // If all checks pass, break out of the loop
            break;
        } 
        while (true);
    
        // Record the chosen position
        numGuessAlready += Integer.toString(optionPlace);
        optionPlace--;
    
        // Update the game board
        for (int k = 0; k < placeOption.length; k++) 
        {
            if (k == optionPlace) 
            {
                placeOption[k] = playerChoice;
            }
        }
            
        DISPLAY_BOARD(placeOption);
        counter++;
    }

    
    // Checks for a win by checking all possibilities
    public static boolean CHECK_FOR_WIN(String[] placeOption, String typePlayer)
    {
        // Define winning combinations
        if ((placeOption[0].equals(typePlayer) && placeOption[1].equals(typePlayer) && placeOption[2].equals(typePlayer)) || (placeOption[3].equals(typePlayer) && placeOption[4].equals(typePlayer) && placeOption[5].equals(typePlayer)) || (placeOption[6].equals(typePlayer) && placeOption[7].equals(typePlayer) && placeOption[8].equals(typePlayer)) || (placeOption[0].equals(typePlayer) && placeOption[3].equals(typePlayer) && placeOption[6].equals(typePlayer)) || (placeOption[1].equals(typePlayer) && placeOption[4].equals(typePlayer) && placeOption[7].equals(typePlayer)) || (placeOption[2].equals(typePlayer) && placeOption[5].equals(typePlayer) && placeOption[8].equals(typePlayer)) || (placeOption[0].equals(typePlayer) && placeOption[4].equals(typePlayer) && placeOption[8].equals(typePlayer)) || (placeOption[2].equals(typePlayer) && placeOption[4].equals(typePlayer) && placeOption[6].equals(typePlayer)))
        {
            // Display the winner
            System.out.println("\nPlayer " + typePlayer + " wins!!!");
            if (typePlayer.equals("X"))
                playerXWin++;
            else
                playerOWin++;
            return true; 
        }
        return false; 
    }
    
    // Reset game variables
    public static void RESET_GAME() 
    {
        // Reset game variables for a new game
        gameEnd = false;
        counter = 0;
        optionPlace = 0;
        numGuessAlready = "";
        placeOption = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    }
    
    // Clear the console screen
    public static void CLEAR_SCREEN() 
    {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }
    
    // Display game statistics
    public static void DISPLAY_STATISTICS() 
    {
        // Display player win counts and tie counts
        System.out.println("\nPlayer X win count: " + playerXWin + "\nPlayer O win count: " + playerOWin + "\nTie game count: " + tie);
    }
    
    // Display the game's title
    public static void DISPLAY_TITLE() 
    {
        // Title ASCII art
        System.out.println("\t::::::::::: :::::::::::   ::::::::       :::::::::::     :::       ::::::::       :::::::::::  ::::::::   ::::::::::");
        System.out.println("\t    :+:         :+:      :+:    :+:          :+:       :+: :+:    :+:    :+:          :+:     :+:    :+:  :+:       ");
        System.out.println("\t    +:+         +:+      +:+                 +:+      +:+   +:+   +:+                 +:+     +:+    +:+  +:+       ");
        System.out.println("\t    +#+         +#+      +#+                 +#+     +#++:++#++:  +#+                 +#+     +#+    +:+  +#++:++#  ");
        System.out.println("\t    +#+         +#+      +#+                 +#+     +#+     +#+  +#+                 +#+     +#+    +#+  +#+       ");
        System.out.println("\t    #+#         #+#      #+#    #+#          #+#     #+#     #+#  #+#    #+#          #+#     #+#    #+#  #+#       ");
        System.out.println("\t    ###     ###########   ########           ###     ###     ###   ########           ###      ########   ##########");
    }
    
    // Display the game board
    public static void DISPLAY_BOARD(String[] placeOption)
    {
        // Display the current game board
        System.out.println("\n        *         *        \n   " + placeOption[0] + "    *    " + placeOption[1] + "    *    " + placeOption[2] + "   \n        *         *        \n***************************\n        *         *        \n   " + placeOption[3] + "    *    " + placeOption[4] + "    *    " + placeOption[5] + "   \n        *         *        \n***************************\n        *         *        \n   " + placeOption[6] + "    *    " + placeOption[7] + "    *    " + placeOption[8] + "   \n        *         *        ");
    }
}
