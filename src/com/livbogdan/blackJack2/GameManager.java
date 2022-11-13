package com.livbogdan.blackJack2;

import java.util.Scanner;

import static java.lang.System.out;

public class GameManager
{
    public static double GetMoney(Scanner reader)
    {
        double money;

        while (!false)
        {
            out.println("How much money do you have? \nYou must have at least 1 dollar to play.");
            money = reader.nextDouble();
            reader.nextLine();

            String text = Double.toString(Math.abs(money));
            int integerPlaces = text.indexOf('.');
            int decimalPlaces = text.length() - integerPlaces - 1;

            if (money >= 1.00 && decimalPlaces <= 2)
            {
                break;
            }

            out.println("You do not have enough money to begin or entered an invalid amount. Please get more money or check your decimals and try again.");
            return -1;
        }

        return money;
    }

    public static String GetName(Scanner reader)
    {
        String name;

        // If player type less than 3 char comming messege with not valid name
        while (!false)
        {
            out.println("Welcome to the table, what is your name?");
            name = reader.nextLine();

            if (name.length() >= 3)
            {
                break;
            }

            out.println("Sorry, but '" + name + "' is not a valid name.");
        }

        return name;
    }

    public static double GetBet(Scanner reader, double minBet)
    {
        double bet;
        while (!false)
        {
            out.println("What would you like to bet on this hand?");
            bet = reader.nextDouble();
            reader.nextLine();

            String text = Double.toString(Math.abs(bet));
            int integerPlaces = text.indexOf('.');
            int decimalPlaces = text.length() - integerPlaces - 1;

            if (bet >= minBet && decimalPlaces <= 2) {
                break;
            }

            out.println("Sorry, but $" +
                    bet + " is not a valid bet.\nPlease either match or raise $" +
                    minBet + ".");
        }

        return bet;

    }

    public static double GetInsuranceBet(Scanner reader, double minBet) {
        double bet;
        String answer;
        while (true) {
            out.println("Would you like to take insurance? Y/N");
            answer = reader.nextLine();

            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N")) {
                break;
            }

            out.println("Sorry, but '" + answer + "' is not a valid input. \n");
        }

        while (true) {
            if (answer.equalsIgnoreCase("Y")) {
                out.println("What is your insurance wager?");
                bet = reader.nextDouble();
                reader.nextLine();

                String text = Double.toString(Math.abs(bet));
                int integerPlaces = text.indexOf('.');
                int decimalPlaces = text.length() - integerPlaces - 1;

                if (bet >= minBet && decimalPlaces <= 2) {
                    break;
                }

                out.println("Sorry, but $" + bet + " is not a valid bet.\nPlease either match or raise $" + minBet + ".");
            }
            else{
                out.println("Denied insurance.");
                return 0.0;
            }
        }

        return bet;

    }

    public static String GetChoice(Scanner reader)
    {
        String choice;
        while (true)
        {
            out.println("(H)it, (S)tand, (D)ouble, (Su)rrender, (E)xit");
            choice = reader.nextLine();

            String partOne = Character.toString(choice.charAt(0)).toUpperCase();
            choice = partOne + choice.substring(1);

            boolean accepted = false;

            String[] acceptedAnswers = {"H", "S", "D", "Su", "E"};
            for (String acceptedAnswer : acceptedAnswers) {
                if (choice.equals(acceptedAnswer)) {
                    accepted = true;
                    break;
                }
            }

            if (accepted)
                break;
            // If player do not type "H", "S", "D", "Su" comming this messege
            out.println("Sorry, but '" + choice + "' is not an accepted input.\n");
        }

        return choice;
    }

    public static boolean SoftNumber(Hand givenHand, int targetNumber)
    {
        boolean hasAce = false; // Player/Dealear do not have Ace card

        for(int i=0; i < givenHand.hand.size(); i++)// Player/Dealear do not have Ace card
        {
            if(givenHand.hand.get(i).name.equals("Ace"))
            {
                hasAce = true;
                break;
            }
        }
        if(hasAce)
        {
            // Forces the Hand class to correctly assign acePoints
            int handPoints = Hand.GetPoints(givenHand);
            if (Hand.acePoints == 11)
            {
                //It's a soft hand
                if (handPoints == targetNumber)
                {
                    return true;
                }
                else
                {
                    //The soft hand doesn't add up
                    return false;
                }
            }
            else
            {
                //The ace is being valued as a 1 (It's a hard hand)
                return false;
            }
        }
        else
        {
            //No ace
            return false;
        }

    }



    ///<summary>
    /// Checking if anyone has blackjack
    /// Get 21 points
    ///<summary/>

    public static boolean CheckForBlackjack(Hand givenHand)
    {
        if (Hand.GetPoints(givenHand) == 21 && givenHand.hand.size() == 2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean CheckForBust(Hand givenHand)
    {
        if (Hand.GetPoints(givenHand) > 21)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
