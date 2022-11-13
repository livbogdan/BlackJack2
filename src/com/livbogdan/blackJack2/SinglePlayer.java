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
    public static void RunGame()
    {
        Scanner reader = new Scanner(System.in);

        String name = GameManager.GetName(reader);

        money = GameManager.GetMoney(reader);

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

            Dealer.ResetDeck(Dealer.deck);
            Dealer.InitDeck(Dealer.deck);
            Dealer.ShuffleDeck(Dealer.deck);

            boolean blackjack = false;
            boolean bust = false;
            boolean playStillGoing = true;

            Hand playerHand = new Hand(new ArrayList<>(Dealer.DealStartingCards(Dealer.deck)));
            Dealer.hand =  new Hand(new ArrayList<>(Dealer.DealStartingCards(Dealer.deck)));


            while(true)
            {
                bet = GameManager.GetBet(reader, 1.00);
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
                    insuranceBet = GameManager.GetInsuranceBet(reader, 1.00);
                    if(insuranceBet + bet <= money)
                    {
                        break;
                    }

                    out.println("you're entering more money than you have to bet! Please wager a value less than or equal to " + (money - bet));
                }
            }

            if(GameManager.CheckForBlackjack(playerHand))
            {
                //Blackjack!
                out.println("That's a blackjack! The dealer now must reveal his cards");
                playStillGoing = false;
                blackjack = true;
            }

            boolean firstPlay = true;

            while (playStillGoing)
            {
                String choice = GameManager.GetChoice(reader);

                if (choice.equals("H"))
                {
                    Dealer.DealCard(playerHand.hand);
                    out.println
                            ("You have recieved a " +
                            playerHand.hand.get(playerHand.hand.size() - 1).suit.symbol +
                            playerHand.hand.get(playerHand.hand.size() - 1).name);
                }
                else if (choice.equals("D"))
                {
                    Dealer.DealCard(playerHand.hand);
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
                else if (choice.equals("E"))
                {
                 System.exit(0);
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
                        Hand.PrintFullHand(playerHand));

                firstPlay = false;

                bust = GameManager.CheckForBust(playerHand);

                if(bust)
                {
                    playStillGoing = false;
                }

            }

            if(!blackjack)
            {
                Dealer.UseTurn(Dealer.hand);
            }
            else{
                Dealer.RevealHand();
            }

            GetWinner(playerHand,
                    Dealer.hand,
                    blackjack,
                    bust);

            out.println("\nYou have $" +
                    money +
                    " left.");

            /*****RESET******/
            Hand.ClearHand(playerHand);
            Hand.ClearHand(Dealer.hand);
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
                out.println("Would you like to play another hand? (Y)es/(N)o or (E)xit"); // Make you desition would you contine play or not
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
                else if (answer.equals("E"))
                {
                  System.exit(0);
                }

                out.println("I'm sorry, but '" + answer + "' is not a valid input."); // If player type small y/n or different charter upcoming this message.
            }
        }
    }

    public static void GetWinner(Hand playerHand,
                                 Hand dealerHand,
                                 boolean playerBlackjack,
                                 boolean playerBust)
    {
        if(playerBust)
        {
            //The player goes first, so it checks if the player busted first.
            out.println("You busted! You lose your bet of $" + bet + ".");
            money -= bet;
            if(!GameManager.CheckForBlackjack(dealerHand))
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
        else if(GameManager.CheckForBust(dealerHand))
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
        else
        {
            //Neither busted
            if(playerBlackjack && GameManager.CheckForBlackjack(dealerHand))
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
            else if(playerBlackjack) //They took insurance 1.5
            {
                bet *= 1.5;
                out.println("You got a blackjack! You win 3:2 on your bet, or $" + bet + ".");
                if(insuranceBet != 0.0)
                {

                    out.println("The dealer didn't have a blackjack! You lose your $" + insuranceBet + " insurance!");
                    money -= insuranceBet;
                }
            }
            else if(GameManager.CheckForBlackjack(dealerHand))
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

                if(Hand.GetPoints(playerHand) == Hand.GetPoints(dealerHand))
                {
                    out.println("It's a push! You don't lose or gain anything!");
                }
                else if(Hand.GetPoints(playerHand) > Hand.GetPoints(dealerHand))
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
