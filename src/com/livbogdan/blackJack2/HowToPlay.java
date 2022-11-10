package com.livbogdan.blackJack2;

public class HowToPlay
{
    public static void main (String[] args)
    {
        println("The object of blackjack is to beat the dealer. To beat the dealer the player must first not bust (go over 21) and second either outscore the dealer or have the dealer bust.");
        println("Blackjack may be played with one to eight decks of 52-card decks. This game will use six.");
        println("Aces may be counted as 1 or 11 points");
        println("Normal cards are worth as many points as their number (i.e. 9 of diamonds = 9 points)");
        println("Tens and face cards count as 10 points.");
        println("The value of a hand is the sum of the point values of the individual cards. Except, a \"blackjack\" is the highest hand, consisting of an ace and any 10-point card, and it outranks all other 21-point hands.");
        println("After the players have bet, the dealer will give two cards to each player and two cards to himself. One of the dealer cards is dealt face up. The facedown card is called the \"hole card.\"");
        println("If the dealer has an ace showing, he will offer a side bet called \"insurance.\" This side wager pays 2 to 1 if the dealer's hole card is any 10-point card. Insurance wagers are optional and may not exceed half the original wager.");
        println("If the dealer has a ten or an ace showing (after offering insurance with an ace showing), then he will peek at his facedown card to see if he has a blackjack. If he does, then he will turn it over immediately.");
        println("If the dealer does have a blackjack, then all wagers (except insurance) will lose, unless the player also has a blackjack, which will result in a push. The dealer will resolve insurance wagers at this time.");
        println("Play begins with the player to the dealer's left. The following are the choices available to the player:");
        println("Stand: Player stands pat with his cards.");
        println("Hit: Player draws another card (and more if he wishes). If this card causes the player's total points to exceed 21 (known as \"breaking\" or \"busting\") then he loses.");
        println("Double: Player doubles his bet and gets one, and only one, more card.");
        println("Surrender: The player forfeits half his wager, keeping the other half, and does not play out his hand. This option is only available on the initial two cards.");
        println("After each player has had his turn, the dealer will turn over his hole card. If the dealer has 16 or less, then he will draw another card. If he has an ace and another set of cards totaling 6 points, it is said that he has a \"Soft 17\". In this casino, the dealer is forced to hit on a soft 17.");
        println("If the dealer goes over 21 points, then any player who didn't already bust will win.");
        println("If the dealer does not bust, then the higher point total between the player and dealer will win.");
        println("Winning wagers pay even money.");
    }

    //Makes it easier to type it all out.
    public static void println(String string)
    {
        System.out.println(string);
    }
}
