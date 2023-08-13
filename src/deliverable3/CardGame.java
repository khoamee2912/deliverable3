/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package deliverable3;


import java.util.Scanner;
import java.util.Random;
/**
 *
 * @author khoa van
 */
import java.util.*;

public class CardGame {
    
    public static void main(String[] args) {
    CardGame game = new CardGame();
    Player player1 = new Player("Khoa Van");
    Player player2 = new Player("Jane");
    game.addPlayer(player1);
    game.addPlayer(player2);
    game.playGame();
}
    
    private List<Player> players;
    private Deck deck;
    private Scanner scanner;

    public CardGame() {
        players = new ArrayList<>();
        deck = new Deck();
        scanner = new Scanner(System.in);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void playGame() {
        boolean playAgain = true;
        while (playAgain) {
            deck.shuffle();
            dealCards();
            determineWinner();
            playAgain = askToPlayAgain();
            for (Player player : players) {
                player.clearHand();
            }
        }
        scanner.close();
    }

    public void dealCards() {
        for (int i = 0; i < 3; i++) {
            for (Player player : players) {
                player.addCard(deck.dealCard());
            }
        }
    }

    public void determineWinner() {
        Player winner = null;
        int highestValue = 0;
        for (Player player : players) {
            int handValue = player.getHandValue();
            if (handValue > highestValue) {
                winner = player;
                highestValue = handValue;
            }
            System.out.println(player.getName() + "'s hand: " + player.getHand() + " (" + handValue + ")");
        }
        System.out.println("Winner: " + winner.getName() + " with a hand value of " + highestValue);
    }

    public boolean askToPlayAgain() {
        System.out.print("Do you want to play again? (Y/N): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("Y");
    }
}

class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue();
        }
        return value;
    }
}

class Deck {
    private List<Card> cards;
    private int nextCardIndex;

    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        nextCardIndex = 0;
    }

    public void shuffle() {
        Collections.shuffle(cards);
        nextCardIndex = 0;
    }

    public Card dealCard() {
        if (nextCardIndex >= cards.size()) {
            throw new IllegalStateException("No more cards in the deck.");
        }
        return cards.get(nextCardIndex++);
    }
}

class Card {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

enum Suit {
    CLUBS,
    DIAMONDS,
    HEARTS,
    SPADES
}

enum Rank {
    ACE(11),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10);

    private int value;

    private Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

