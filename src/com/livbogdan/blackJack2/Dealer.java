package com.livbogdan.blackJack2;
import java.util.Random;
import java.util.Stack;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Dealer
{

    public static Stack<Card> deck = new Stack<>();
    public static Hand hand;

    public static void ShuffleDeck(Stack<Card> gameDeck)
    {

        Random random = new Random();

        for (int i = 0; i < 10000; i++)
        {

            int indexToChange = random.nextInt(gameDeck.size());
            int indexToChangeTo = random.nextInt(gameDeck.size());

            Swap(gameDeck, indexToChange, indexToChangeTo);

        }

    }

    public static void InitDeck(Stack<Card> gameDeck)
    {
        final int NUM_SUITS = 4; // amount of suits
        final int STARTING_CARD_NUMBER = 2; // Amount card in hand
        final int CARDS_PER_SUIT = 14; // How much cards has one suit
        final int DECKS_USED = 6; //total used cards in deck

        for (int dck = 1; dck <= DECKS_USED; dck++)
        {
            for (int suit = 0; suit < NUM_SUITS; suit++)
            {
                for (int card = STARTING_CARD_NUMBER;
                     card <= CARDS_PER_SUIT;
                     card++)
                {
                    gameDeck.add(Card.InitCard(card, Card.Suit.values()[suit]));
                }
            }
        }


    }

    public static void Swap(Stack<Card> gameDeck,
                            int indexToChange,
                            int indexToChangeTo)
    {

        Card temp = gameDeck.get(indexToChange);
        gameDeck.set(indexToChange, gameDeck.get(indexToChangeTo));
        gameDeck.set(indexToChangeTo, temp);

    }

    public static List<Card> DealStartingCards(Stack<Card> gameDeck)
    {
        Card cardOne = gameDeck.pop();
        Card cardTwo = gameDeck.pop();

        return Arrays.asList(cardOne, cardTwo);
    }

    public static void DealCard(List<Card> hand)
    {
        hand.add(deck.pop());
    }

    public static void ResetDeck(Stack<Card> gameDeck)
    {
        gameDeck.removeAllElements();
    }

    public static void RevealHand()
    {
        System.out.println("\n\nFlipping over the card in the hole...");

        //Wait
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException ie)
        {
            throw new RuntimeException(ie);
        }

        System.out.println("\n\nThe dealer's 2 cards are: " +
                Hand.PrintFullHand(Dealer.hand)); // Dealer show all cards in hands
    }

    public static void UseTurn(Hand myHand)
    {

        boolean stillGoing = true;

        System.out.println("\n\nYour turn is over. It is now the dealer's turn.\n\nFlipping over the card in the hole...");

        //Wait
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException ie)
        {
            throw new RuntimeException(ie);
        }

        System.out.println("\n\nThe dealer's 2 cards are: " + Hand.PrintFullHand(Dealer.hand));

        while (stillGoing)
        {

            if (GameManager.CheckForBust(myHand))
            {
                stillGoing = false;
            }
            else if (Hand.GetPoints(myHand) < 16)
            {
                System.out.println("The dealer has less than 16 points, and is hitting...");
                DealCard(myHand.hand);
                //Wait
                try {
                    TimeUnit.SECONDS.sleep(2);
                }
                catch (InterruptedException ie)
                {
                    throw new RuntimeException(ie);
                }
                System.out.println("The dealer drew a " +
                        myHand.hand.get(myHand.hand.size() - 1).suit.symbol +
                        myHand.hand.get(myHand.hand.size() - 1).name);
            }
            else if (GameManager.SoftNumber(myHand, 17))
            {
                //It's a soft 17
                System.out.println("It's a soft 17. The dealer is hitting...");
                DealCard(myHand.hand);
                //Wait
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (InterruptedException ie)
                {
                    throw new RuntimeException(ie);
                }
                System.out.println("The dealer drew a " +
                        myHand.hand.get(myHand.hand.size() - 1).suit.symbol +
                        myHand.hand.get(myHand.hand.size() - 1).name);
            }
            else
            {
                System.out.println("The dealer is standing.\n");
                stillGoing = false;
            }

        }

        System.out.println("\n\nThe dealer's final cards are: " +
                Hand.PrintFullHand(Dealer.hand) + "\n");

        //Wait
        try
        {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException ie)
        {
            throw new RuntimeException(ie);
        }
    }
}