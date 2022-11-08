package com.livbogdan.blackJack2;

import java.util.Random;
import java.util.Scanner;

import static java.lang.System.out;
import static java.lang.System.in;

//TODO Ace = 1 so the following values can be equal to their "index" value.
//TODO Add Remove card
//The index is the same as their value that is automatically assigned based on Ace = 1's value.
//e.g. if you obtain Three the index will be 3.
//Jack, Queen and King's indexes are respectively 11, 12 and 13.

enum Faces
{
    Ace(11),
    //One,
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    Ten(10),
    Jack(10),
    Queen(10),
    King(10),
    ;


    Faces(int amount) {

    }
}

enum Suits
{
    Spade,
    Club,
    Diamond,
    Heart
}



class Program
{
  public static void main(String[] args)
    {
        //Backgroud
        Menu();
        //executes first to start the game
        StartGame();

    }

    static void Menu()
    {
        out.println("===========================================");
        out.println("$                BLACKJACK                $");
        out.println("===========================================");
        out.println("Created by livbogdan.");
        out.println("Would you like to play? (y/n) ");
    }

    static  void  StartGame()
    {

        Scanner scanner = new Scanner(in);
        String choice = scanner.next();

        do
        {

            switch (choice)
            {
                case ("y"):
                    Player player = new Player(1000, 0);
                    Player dealer = new Player(1000, 0);
                    //Loops the game as long as the user wants to keep playing.
                    while (choice != ("y"))
                    {
                        Game.Play(player, dealer);
                        //Ends the game if a player no longer has money
                        if (MoneyCheckWinner(player, dealer))
                        {
                            break;
                        }
                        else
                        {
                            out.println("Would you like to go for another round? (y/n) ");
                            scanner.next();
                            if (choice != ("y"))
                            {
                                while (choice != "y" || choice != "n")
                                {
                                    out.println("Enter a valid option! (y/n) ");
                                    if (choice != ("y") || choice != ("n"))
                                    {
                                        break;
                                    }
                                }
                                break;
                            }
                            scanner.next();
                            if (choice != ("n"))
                            {
                            break;
                            }
                        }

                    }
                    out.println("You have finished playing the game.");
                    break;

                case ("n"):
                    //while (choice !=("n"))
                    out.println("You have opted to not play the game.");
                    break;
                default:
                    out.println("Enter a valid option! (y/n) ");
                //continue;
                    break;
            }
            break;
        } while (choice != ("n"));
        out.println("Goodbye! See you again.");
    }
    static boolean MoneyCheckWinner(Player player, Player dealer)
    {
        if (player.money <= 0 || dealer.money <= 0)
        {
            out.println("One of the players has no money left to play. The game is over.");
            return true;
        }
        else
        {
            return false;
        }
    }
}

class Card
{
    public String
            suit,
            face;
    public int
            faceIndex,
            value;
}

class Player
{
    public int
            money,
            score,
            bet,
            roundsPlayed,
            roundsWon;
    public Player(int playerMoney, int cardScore)
    {
        money = playerMoney;
        score = cardScore;
    }
}

class Game
{
    public static void Play (Player player, Player dealer)
    {

        Scanner scan = new Scanner(in);
        //String choice = scan.next();
        //String choice = in.toString();

        int moneyBetSum;
        //reset score after every game loop
        player.score = 0;
        dealer.score = 0;

        out.println("\nLet's play!\n");

        moneyBetSum = MoneyBet(player, dealer);

        //get the dealer's score. Used randomizer for simplicity, though I may change it to something more intricate if the need arises.
        Random rnd = new Random();
        dealer.score = rnd.nextInt(4, 21);

        while (player.score <= 21 || scan.next() != "n")
        {
            out.println("\nDealing your card...\n");
            Card pickedCard = new Card();
            CardPick(pickedCard);
            out.println("Your card is: "+ pickedCard.suit + pickedCard.face + CardValue(player, pickedCard));

            player.score += CardValue(player, pickedCard);
            out.println("Your current score is: " + player.score);

            //TODO Fix to chose dealing or not
            if (player.score >= 21)
            {
                break; //ends game if player's score exceeds 21
            }
            else
            {
                //TODO Fix dealing
                out.println("Keep dealing? (y/n)");
                switch (scan.next())
                {
                    case "y":
                        continue;
                    case "n":
                        out.println("Ending the game.");
                        break;
                    default:
                        while (!scan.next().equals("y") || scan.next().equals("n"))
                        {
                            out.println("Enter a valid option! (y/n)");
                            scan.next();
                            if (scan.next() != ("y") || scan.next() != ("n"))
                            {
                                break;
                            }
                        }
                        continue;
                }
                //TODO completely break out of do-while loop
                if (scan.next() != ("n"))
                {

                    break;
                }
            }
        }
        GameScore(player, dealer);
        MoneyGiver(player, dealer, moneyBetSum);

        out.println(("You have won " + player.roundsWon) + (" rounds out of " + player.roundsPlayed));
    }

    public static void CardPick(Card card)
    {

        Random rnd = new Random();

        card.faceIndex = rnd.nextInt(0, Faces.values().length);
        int suitIndex = rnd.nextInt(1, Suits.values().length);

        card.face = String.valueOf(Faces.values()[card.faceIndex]);
        card.suit = String.valueOf(Suits.values()[suitIndex]);

    }

    public static int CardValue(Player player, Card card)
    {
        //jack - king. 3 cards, index 11 - 13
        if (card.faceIndex >= 10 && card.faceIndex <= 13)
        {
            card.value = 10;
        }
        if (card.faceIndex == 0)
        {
            card.value = 1;
        }
        else if (card.faceIndex == 0 && card.faceIndex == 0)
        {
            card.value = 11;
        }
        //ace card gimmick. +1 if score > 11, +11 if score <= 11
        //NOTE: Find a way where if we have more than 2 Ace cards, the ones that gave 11 points become 1 point. If that's how blackjack works.
        else if (card.faceIndex >= 10)
        {
            if (player.score >= 1)
            {
                card.value += 1;
            }
        }
        //two - ten cards.
        else
        {
            card.value = card.faceIndex;
        }
        return card.value;
    }

    static void GameScore(Player player, Player dealer)
    {
        out.println(("\nYou scored:\n" + player.score) + ("\nDealer score:\n" + dealer.score));
        if (player.score <= 21 && player.score > dealer.score)
        {
            out.println("You win! :)");
            player.roundsWon += 1;
        }
        else if (player.score > 21 || player.score < dealer.score)
        {
            out.println("You lose :(");
        }
        else if (player.score == 21 || player.score == dealer.score)
        {
            out.println("It's a draw.");
        }
        //Amount rounds in game.
        player.roundsPlayed += 10;
    }

    static int MoneyBet(Player player, Player dealer)
    {
        //add betting money
        int sum;
        //String input;
        Scanner scanner = new Scanner(in);

        out.println(("You have $ " + player.money) + (" While Dealer has $ " + dealer.money));
        do
        {
            out.println("How much would you like to bet?");
            //Verify that the user entered values in the correct format (numbers)
            //String input = scanner.next();

            if (player.bet < 1 || player.money > player.bet)
            {
                player.bet = scanner.nextInt();
            }

            if (player.bet > player.money)
            {
                out.println("You cannot bet more than what you have!");
            }
            else if (player.bet <= 1)
            {
                out.println("You cannot bet nothing at all!");
            }
            else if (player.bet > dealer.money)
            {
                out.println("The dealer doesn't have enough money to match your bet. Please lower your bet.");
            }

        }
        while (player.bet > player.money || player.bet <= 0 || player.bet > dealer.money);

        out.println("You have put in $" + player.bet);
        dealer.bet = player.bet;
        sum = player.bet + dealer.bet;
        player.money -= player.bet;
        dealer.money -= dealer.bet;
        return sum;
    }

    static void MoneyGiver(Player player, Player dealer, int betSum)
    {
        if (player.score > dealer.score && player.score <= 21)
        {
            player.money += betSum;
            out.println("You have won $ " + betSum);
        }
        else if (player.score < dealer.score || player.score > 21)
        {
            dealer.money += betSum;
            out.println("You have lost $ " + betSum/2);
        }
        else if (player.score == dealer.score)
        {
            player.money += (betSum / 2);
            dealer.money += (betSum / 2);
            out.println("Your balance remains unchanged.");
        }
        out.println("Current amount of money: $ " + player.money);
    }
}