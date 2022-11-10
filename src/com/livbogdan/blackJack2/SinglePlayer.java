package com.livbogdan.blackJack2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class SinglePlayer
{
    public static double money;
    public static double bet;
    public static double insuranceBet;
    public static void runGame()
    {
        Scanner reader = new Scanner(System.in);

        String name = GameManager.getName(reader);

        money = GameManager.getMoney(reader);

        if (money == -1)
        {
            System.exit(10); //10 = Bad money
        }

        boolean playing = true;

        while (playing)
        {
            if(money < 0)
            {
                out.println("You really screwed up now, didn't you? Now you owe us $" + (-1 * money) + ".\nExpect a bill.");
                playing = false;
                System.exit(10); //10 = Bad money
            }
            else if(money < 1.00)
            {
                out.println("You don't have enough money to keep playing!"); // You get this messege when you has no money
                playing = false;
                System.exit(10); //10 = Bad money
            }

            Dealer.resetDeck(Dealer.deck);
            Dealer.initDeck(Dealer.deck);
            Dealer.shuffleDeck(Dealer.deck);

            boolean blackjack = false;
            boolean bust = false;
            boolean playStillGoing = true;

            Hand playerHand = new Hand(new ArrayList<Card>(Dealer.dealStartingCards(Dealer.deck)));
            Dealer.hand =  new Hand(new ArrayList<Card>(Dealer.dealStartingCards(Dealer.deck)));


            while(true)
            {
                bet = GameManager.getBet(reader, 1.00);
                if(bet <= money)
                {
                    break;
                }

                out.println("You entered more money than you currently have! Please enter a valid bet.");
            }

            out.println("Dealing cards...\n");

            //Wait
            try
            {
                TimeUnit.SECONDS.sleep(2); // Dealear hand out card 2 second
            }
            catch (InterruptedException ie)
            {
                throw new RuntimeException(ie);
            }

            out.println("You have recieved a " +
                    playerHand.hand.get(0).suit.symbol +
                    playerHand.hand.get(0).name + " and a " +
                    playerHand.hand.get(1).suit.symbol +
                    playerHand.hand.get(1).name);

            out.println("The dealer has recieved a " +
                    Dealer.hand.hand.get(0).suit.symbol +
                    Dealer.hand.hand.get(0).name +
                    " and an unknown card.");

            if(Dealer.hand.hand.get(0).name.equals("Ace"))
            {
                while(true)
                {
                    insuranceBet = GameManager.getInsuranceWager(reader, 1.00);
                    if(insuranceBet + bet <= money)
                    {
                        break;
                    }

                    out.println("you're entering more money than you have to bet! Please wager a value less than or equal to " + (money - bet));
                }
            }

            if(GameManager.checkForBlackjack(playerHand))
            {
                //Blackjack!
                out.println("That's a blackjack! The dealer now must reveal his cards");
                playStillGoing = false;
                blackjack = true;
            }

            boolean firstPlay = true;

            while (playStillGoing)
            {
                String choice = GameManager.getChoice(reader);

                if (choice.equals("H"))
                {
                    Dealer.dealCard(playerHand.hand);
                    out.println
                            ("You have recieved a " +
                            playerHand.hand.get(playerHand.hand.size() - 1).suit.symbol +
                            playerHand.hand.get(playerHand.hand.size() - 1).name);
                }
                else if (choice.equals("D"))
                {
                    Dealer.dealCard(playerHand.hand);
                    bet *= 2;

                    out.println("Upping your bid to $" + bet);
                    out.println
                            ("You have recieved a " +
                            playerHand.hand.get(playerHand.hand.size() - 1).suit.symbol +
                            playerHand.hand.get(playerHand.hand.size() - 1).name);

                    playStillGoing = false;
                }
                else if(choice.equals("S"))
                {
                    playStillGoing = false;
                    break;
                }
                else
                {
                    if(firstPlay)
                    {
                        bet *= 0.5;
                        playerHand.active = false;
                        firstPlay = false;
                        break;
                    }
                    else
                    {
                        out.println("Sorry, but you can only Surrender on your first hand!");
                        continue;
                    }
                }

                out.println("Your current hand is: " +
                        Hand.printFullHand(playerHand));

                firstPlay = false;

                bust = GameManager.checkForBust(playerHand);

                if(bust){
                    playStillGoing = false;
                }

            }

            if(!blackjack)
            {
                Dealer.useTurn(Dealer.hand);
            }
            else{
                Dealer.revealHand();
            }

            getWinner(playerHand,
                    Dealer.hand,
                    blackjack,
                    bust);

            out.println("\nYou have $" +
                    money +
                    " left.");

            /*****RESET******/
            Hand.clearHand(playerHand);
            Hand.clearHand(Dealer.hand);
            insuranceBet = 0;
            bet = 0;

            if(money < 0)
            {
                out.println("\nYou really screwed up now, didn't you? Now you owe us $"
                        + (-1 * money) +
                        ".\nExpect a bill.\nGet out of here.\n"); // You got this messege when you on minus
                playing = false;
                break;
            }


            while(true)
            {
                out.println("Would you like to play another hand? Y/N"); // Make you desition would you contine play or not
                String answer = reader.nextLine().toUpperCase();
                if (answer.equals("Y") || answer.equals("N"))
                {
                    if(answer.equals("Y")) // Starting another round
                    {
                        playing = true;
                        break;
                    }
                    else
                    {
                        playing = false;
                        break;
                    }
                }

                out.println("I'm sorry, but '" + answer + "' is not a valid input."); // If player type small y/n or different charter upcoming this message.
            }
        }
    }

    public static void getWinner(Hand playerHand,
                                 Hand dealerHand,
                                 boolean playerBlackjack,
                                 boolean playerBust)
    {
        if(playerBust)
        {
            //The player goes first, so it checks if the player busted first.
            out.println("You busted! You lose your bet of $" + bet + ".");
            money -= bet;
            if(!GameManager.checkForBlackjack(dealerHand))
            {
                if(insuranceBet != 0.0)
                {
                    //They took insurance
                    out.println("The dealer didn't have a blackjack! You lose your $" + insuranceBet + " insurance!");
                    money -= insuranceBet;
                }
            }
            else
            {
                if(insuranceBet != 0.0)
                {
                    //They took insurance
                    insuranceBet *= 2;
                    out.println("However, you win back 2:1 ($" + insuranceBet + ") from your insurance!");
                    money += insuranceBet;
                }
            }
        }
        else if(GameManager.checkForBust(dealerHand))
        {
            out.println("The dealer busted! You win your bet of $" + bet + ".");
            money += bet;
            if(insuranceBet != 0.0)
            {
                //They took insurance
                out.println("The dealer didn't have a blackjack! You lose your $" + insuranceBet + " insurance!");
                money -= insuranceBet;
            }
        }
        else{
            //Neither busted
            if(playerBlackjack && GameManager.checkForBlackjack(dealerHand))
            {
                //2 blackjacks == tie
                out.println("It's a push! You don't lose or gain anything!");
                if(insuranceBet != 0.0)
                {
                    //They took insurance
                    insuranceBet *= 2;
                    out.println("However, you win back 2:1 ($" +
                            insuranceBet +
                            ") from your insurance!");
                    money += insuranceBet;
                }
            }
            else if(playerBlackjack)
            {
                bet *= 1.5;
                out.println("You got a blackjack! You win 3:2 on your bet, or $" + bet + ".");
                if(insuranceBet != 0.0)
                {
                    //They took insurance
                    out.println("The dealer didn't have a blackjack! You lose your $" + insuranceBet + " insurance!");
                    money -= insuranceBet;
                }
            }
            else if(GameManager.checkForBlackjack(dealerHand))
            {
                out.println("The dealer got a blackjack! You lose your $" + bet + " wager.");
                money -= bet;
                if(insuranceBet != 0.0)
                {
                    //They took insurance
                    insuranceBet *= 2;
                    out.println("However, you win back 2:1 ($" + insuranceBet + ") from your insurance!");
                    money += insuranceBet;
                }
            }
            else
            {
                //No busts, no blackjacks
                if(insuranceBet != 0.0)
                {
                    //They took insurance
                    out.println("The dealer didn't have a blackjack! You lose your $" + insuranceBet + " insurance!");
                    money -= insuranceBet;
                }

                if(Hand.getPoints(playerHand) == Hand.getPoints(dealerHand))
                {
                    out.println("It's a push! You don't lose or gain anything!");
                }
                else if(Hand.getPoints(playerHand) > Hand.getPoints(dealerHand))
                {
                    // Add money in your bank
                    out.println("You win! You get $" + bet + ".");
                    money += bet;
                }
                else
                {
                    // You lose money from your bank
                    out.println("You lose! You lose $" + bet + ".");
                    money -= bet;
                }
            }
        }
    }
}
