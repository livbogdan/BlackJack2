package com.livbogdan.blackJack2;

import java.util.ArrayList;
import java.util.List;

public class Hand
{
    public List<Card> hand; // Card List
    public boolean active = true; // Set active cards
    public static int acePoints; //How many points the Ace is being counted as (1/11), used for calculating if a hand is soft or hard.

    Hand(ArrayList<Card> cards) // Method
    {
        this.hand = cards;
    }

    public static int getPoints(Hand inputtedHand)
    {
        int points = 0; // How much points player has
        boolean hasAnAce = false; // Has player ace or not

        for (int i = 0; i < inputtedHand.hand.size(); i++)
        {
            if (!inputtedHand.hand.get(i).name.equals("Ace"))
            {
                if ((!inputtedHand.hand.get(i).name.equals("King")) && (!inputtedHand.hand.get(i).name.equals("Queen")) && (!inputtedHand.hand.get(i).name.equals("Jack")))
                    points += Integer.parseInt(inputtedHand.hand.get(i).name);
                else
                    points += 10; // If player get in hand King Queen or Jack player get 10 points
            }
            else
            {
                hasAnAce = true;
            }
        }
        if (hasAnAce) // If player has Ace
        {
            Hand tempHand = copyHandWithoutAces(copyHand(inputtedHand));
            if (tempHand.hand.size() == 0)
            {
                //inputtedHand.hand.stream().forEach(System.out::println);
                points = (11 + inputtedHand.hand.size() - 1);
                //System.out.println(points);
            }
            else if (points <= 10)  // player got 11 points
            {
                points += 11;
                acePoints = 11;
            }
            else // player got only 1 point.
            {
                points += 1;
                acePoints = 1;
            }
        }

        return points;
    }

    public static Hand copyHand(Hand inputtedHand)
    {
        ArrayList<Card> tempList = new ArrayList<>();
        for (int i = 0; i < inputtedHand.hand.size(); i++) {
            tempList.add(Card.copyCard(inputtedHand.hand.get(i)));
        }

        Hand tempHand = new Hand(tempList);

        return tempHand;
    }

    // If player/dealer dont have Ace Card
    public static Hand copyHandWithoutAces(Hand inputtedHand)
    {
        ArrayList<Card> tempList = new ArrayList<>();
        for (int i = 0; i < inputtedHand.hand.size(); i++) {
            tempList.add(Card.copyCard(inputtedHand.hand.get(i)));
        }

        List<Card> removeList = new ArrayList<Card>();

        for (Card card : tempList)
        {
            if (card.name.equals("Ace"))
                removeList.add(card);
        }

        for (Card card : removeList)
            tempList.remove(card);

        Hand tempHand = new Hand(tempList);

        return tempHand;
    }

    // Get Amount of points
    public static String printFullHand(Hand inputtedHand)
    {
        String result = "";
        for (Card card : inputtedHand.hand) {
            result += card.suit.symbol;
            result += card.name;
            result += ", ";
        }

        return result;
    }

    //Take back all player cards when round/game end
    public static Hand clearHand(Hand inputtedHand) // Method gonna be active when Dealer open cards
    {
        inputtedHand.hand.clear();

        return inputtedHand;
    }
}
