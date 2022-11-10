package com.livbogdan.blackJack2;

import java.util.Scanner;

public class GameManager
{
    public static double getMoney(Scanner reader) {
        double money = 0;

        while (!false) {
            System.out.println("How much money do you have? You must have at least 1 dollar to play.");
            money = reader.nextDouble();
            reader.nextLine();

            String text = Double.toString(Math.abs(money));
            int integerPlaces = text.indexOf('.');
            int decimalPlaces = text.length() - integerPlaces - 1;

            if (money >= 1.00 && decimalPlaces <= 2) {
                break;
            }

            System.out.println("You do not have enough money to begin or entered an invalid amount. Please get more money or check your decimals and try again.");
            return -1;
        }

        return money;
    }

    public static String getName(Scanner reader) {
        String name = "";

        while (!false)
        {
            System.out.println("Welcome to the table, what is your name?");
            name = reader.nextLine();

            if (name.length() >= 3)
            {
                break;
            }

            System.out.println("Sorry, but '" + name + "' is not a valid name.");
        }

        return name;
    }

    public static double getBet(Scanner reader, double minWager) {
        double bet = 0;
        while (!false) {
            System.out.println("What would you like to bet on this hand?");
            bet = reader.nextDouble();
            reader.nextLine();

            String text = Double.toString(Math.abs(bet));
            int integerPlaces = text.indexOf('.');
            int decimalPlaces = text.length() - integerPlaces - 1;

            if (bet >= minWager && decimalPlaces <= 2) {
                break;
            }

            System.out.println("Sorry, but $" + bet + " is not a valid wager.\nPlease either match or raise $" + minWager + ".");
        }

        return bet;

    }

    public static double getInsuranceWager(Scanner reader, double minBet) {
        double bet;
        String answer;
        while (true) {
            System.out.println("Would you like to take insurance? Y/N");
            answer = reader.nextLine();

            if (answer.toUpperCase().equals("Y") || answer.toUpperCase().equals("N")) {
                break;
            }

            System.out.println("Sorry, but '" + answer + "' is not a valid input. \n");
        }

        while (true) {
            if (answer.toUpperCase().equals("Y")) {
                System.out.println("What is your insurance wager?");
                bet = reader.nextDouble();
                reader.nextLine();

                String text = Double.toString(Math.abs(bet));
                int integerPlaces = text.indexOf('.');
                int decimalPlaces = text.length() - integerPlaces - 1;

                if (bet >= minBet && decimalPlaces <= 2) {
                    break;
                }

                System.out.println("Sorry, but $" + bet + " is not a valid bet.\nPlease either match or raise $" + minBet + ".");
            }
            else{
                System.out.println("Denied insurance.");
                return 0.0;
            }
        }

        return bet;

    }

    public static String getChoice(Scanner reader) {
        String choice;
        while (true) {
            System.out.println("(H)it, (S)tand, (D)ouble, (Su)rrender");
            choice = reader.nextLine();

            String partOne = Character.toString(choice.charAt(0)).toUpperCase();
            choice = partOne + choice.substring(1);

            boolean accepted = false;

            String[] acceptedAnswers = {"H", "S", "D", "Su"};
            for (int i = 0; i < acceptedAnswers.length; i++) {
                if (choice.equals(acceptedAnswers[i])) {
                    accepted = true;
                    break;
                }
            }

            if (accepted)
                break;

            System.out.println("Sorry, but '" + choice + "' is not an accepted input.\n");
        }

        return choice;
    }

    public static boolean softNumber(Hand givenHand, int targetNumber) {
        boolean hasAce = false;
        for(int i=0; i < givenHand.hand.size(); i++) {
            if(givenHand.hand.get(i).name.equals("Ace")){
                hasAce = true;
                break;
            }
        }
        if(hasAce) {
            int handPoints = Hand.getPoints(givenHand);              //Forces the Hand class to correctly assign acePoints
            if (Hand.acePoints == 11) {
                //It's a soft hand
                if (handPoints == targetNumber) {
                    return true;
                } else {
                    //The soft hand doesn't add up
                    return false;
                }
            } else {
                //The ace is being valued as a 1 (It's a hard hand)
                return false;
            }
        }
        else{
            //No ace
            return false;
        }

    }

    public static boolean checkForBlackjack(Hand givenHand) {
        if (Hand.getPoints(givenHand) == 21 && givenHand.hand.size() == 2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkForBust(Hand givenHand) {
        if (Hand.getPoints(givenHand) > 21) {
            return true;
        } else {
            return false;
        }
    }
}
