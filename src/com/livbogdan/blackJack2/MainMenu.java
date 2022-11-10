package com.livbogdan.blackJack2;

import java.util.Scanner;

public class MainMenu
{

    public static void main (String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int[] acceptedAnswers = {1, 2, 3, 4, 5};

        System.out.println("Welcome to the Java Casino!");

        //While playing
        //It returns false if it's time to break, but otherwise runs some code.
        do
        {
            printMenu();

        } while (runProgram(getInput(scanner, acceptedAnswers)));
        scanner.close();
    }

    public static void printMenu()
    {
        System.out.println("Enter the number to launch the corresponding option. \n");
        System.out.println("1. Play Blackjack");
        System.out.println("2. Blackjack - how to play");
        System.out.println("3. About this game");
        System.out.println("4. Exit");
    }

    public static int getInput(Scanner scanner, int[] acceptedAnswers){
        boolean validInput = false;
        int input = 4;            //Default to exit

        while(!validInput){
            input = scanner.nextInt();
            scanner.nextLine();      //Clear the newline character

            for(int i = 0; i < acceptedAnswers.length; i++){
                if(input == acceptedAnswers[i]){
                    validInput = true;
                    return input;        //Don't say the input is invalid.
                }
            }

            System.out.println("Sorry, but '" + input + "' is not a valid input.\nPlease enter a number between " + acceptedAnswers[0] + " and " + acceptedAnswers[acceptedAnswers.length] /*The last element*/ + ".");

        }

        return input;

    }

    ///Buttons
    ///</summary>>
    //Starting game when player pressing button 1
    /// Show rule when player press button 2
    /// Show authors of this game when player press button 3
    /// Exit game when player press button 4
    ///<summary/>>

    public static boolean runProgram(int input)
    {
        if(input == 4)
        {
            return false;
        }
        else{

            if(input == 1)
                SinglePlayer.runGame();
            else if(input == 2)
                HowToPlay.main(null);
            else
                AboutThisGame.main(null);

            return true;
        }
    }
}
