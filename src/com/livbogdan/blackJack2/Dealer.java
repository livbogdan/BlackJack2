package com.livbogdan.blackJack2;
import java.util.Random;
import java.util.Stack;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Dealer
{

    public static Stack<Card> deck = new Stack<Card>();
    public static Hand hand;

    public static void shuffleDeck(Stack<Card> gameDeck)
    {

        Random random = new Random();

        for (int i = 0; i < 10000; i++)
        {

            int indexToChange = random.nextInt(gameDeck.size());
            int indexToChangeTo = random.nextInt(gameDeck.size());

            swap(gameDeck, indexToChange, indexToChangeTo);

        }

    }

    public static void initDeck(Stack<Card> gameDeck)
    {
        final int NUM_SUITS = 4;
        final int STARTING_CARD_NUMBER = 2;
        final int CARDS_PER_SUIT = 14;
        final int DECKS_USED = 6;

        for (int dck = 1; dck <= DECKS_USED; dck++)
        {
            for (int suit = 0; suit < NUM_SUITS; suit++)
            {
                for (int card = STARTING_CARD_NUMBER;
                     card <= CARDS_PER_SUIT;
                     card++)
                {
                    gameDeck.add(Card.initCard(card, Card.Suit.values()[suit]));
                }
            }
        }


    }

    public static void swap(Stack<Card> gameDeck,
                            int indexToChange,
                            int indexToChangeTo)
    {

        Card temp = gameDeck.get(indexToChange);
        gameDeck.set(indexToChange, gameDeck.get(indexToChangeTo));
        gameDeck.set(indexToChangeTo, temp);

    }

    public static List<Card> dealStartingCards(Stack<Card> gameDeck) {
        Card cardOne = gameDeck.pop();
        Card cardTwo = gameDeck.pop();

        return Arrays.asList(cardOne, cardTwo);
    }

    public static void dealCard(List<Card> hand)
    {
        hand.add(deck.pop());
    }

    public static void resetDeck(Stack<Card> gameDeck)
    {
        gameDeck.removeAllElements();
    }

    public static void revealHand() {
        System.out.println("\n\nFlipping over the card in the hole...");

        //Wait
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {

        }

        System.out.println("\n\nThe dealer's 2 cards are: " +
                Hand.printFullHand(Dealer.hand));
    }

    public static void useTurn(Hand myHand)
    {

        boolean stillGoing = true;

        System.out.println("\n\nYour turn is over. It is now the dealer's turn.\n\nFlipping over the card in the hole...");

        //Wait
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {

        }

        System.out.println("\n\nThe dealer's 2 cards are: " + Hand.printFullHand(Dealer.hand));

        while (stillGoing)
        {

            if (GameManager.checkForBust(myHand))
            {
                stillGoing = false;
            }
            else if (Hand.getPoints(myHand) < 16)
            {
                System.out.println("The dealer has less than 16 points, and is hitting...");
                dealCard(myHand.hand);
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
            else if (GameManager.softNumber(myHand, 17))
            {
                //It's a soft 17
                System.out.println("It's a soft 17. The dealer is hitting...");
                dealCard(myHand.hand);
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
                Hand.printFullHand(Dealer.hand) + "\n");

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