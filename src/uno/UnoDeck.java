/**
* THE UNO DECK HAS 108 CARDS..
* THE CARDS ARE 1 ZERO, 2 OF ONE TO NINE, 2 OF DrawTwo,Skip,Reverse.
* PLUS FOUR WILD AND WILD DRAW FOUR CARDS.
*/
package uno;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class UnoDeck
{
  private UnoCard deck[];
  private int cardsInDeck;
  private UnoCard.Colour colour;
  public UnoDeck()
  {
    deck = new UnoCard[108];  // filling 108 cards into the deck.(no value yet given)
  }

  public void newDeck()
  {
    int i,j;
    UnoCard.Colour colours[] = UnoCard.Colour.values(); // filling the array with all colours
    cardsInDeck = -1;
    UnoCard.Number numbers[], number;
    for(i=0;i<colours.length-1; ++i) // loop to add cards of each colour except the wild card.
    {
      colour = colours[i];
      deck[++cardsInDeck] = new UnoCard(colour, UnoCard.Number.getNumber(0)); // 1 zero card

      for(j=1;j<13; ++j)
      {
        deck[++cardsInDeck] = new UnoCard(colour,UnoCard.Number.getNumber(j));
        deck[++cardsInDeck] = new UnoCard(colour,UnoCard.Number.getNumber(j)); // two cards of 1-9 numbers plus skip reverse, and DrawTwo..
      }
    }// i for loop

    // NOW TO ADD WILD AND WildDrawFour
      for(i=0;i<4; ++i)
      {
        deck[++cardsInDeck] = new UnoCard(UnoCard.Colour.Wild,UnoCard.Number.Wild);
        deck[++cardsInDeck] = new UnoCard(UnoCard.Colour.Wild,UnoCard.Number.WildDrawFour);
      }
  }// end of newDeck function

  public int getCardsInDeck()
  {
    return cardsInDeck + 1;
  }

  public void replaceDeckWith(ArrayList<UnoCard> cards)  // TO REPLACE THE PLAYED DOWN CARDS INTO DECK.
  {
    deck = cards.toArray(new UnoCard[cards.size()]);
    cardsInDeck = deck.length;
    System.out.println("Cards in deck" + cardsInDeck);
  }

  public boolean isEmpty()
  {
    return cardsInDeck==0;
  }

  public void shuffle()
  {
    int n = deck.length,randomValue;
    int i,j;
    Random random = new Random();
    UnoCard randomCard;

    for(i=0;i<deck.length;++i)
    {
      randomValue = i + random.nextInt(n-i);
      randomCard = deck[randomValue];   // now we have a random card;
      deck[randomValue] = deck[i];
      deck[i] = randomCard;
    }
  }// end of shuffle function

  public UnoCard drawCard() throws IllegalArgumentException
  {
    UnoCard card;
    if(isEmpty())
      return null;
    card = deck[cardsInDeck];
    --cardsInDeck;
    return card;
  }

  public ImageIcon drawCardImage() throws IllegalArgumentException
  {
    if(isEmpty())
      throw new IllegalArgumentException("Empty deck");

    return new ImageIcon(deck[cardsInDeck].toString() + ".png");
  }

  public ArrayList<UnoCard> drawMultipleCards(int n) throws IllegalArgumentException
  {
    int i;
    if(n<0)
      throw new IllegalArgumentException("Only positive draw allowed");
    if(n>cardsInDeck)
      throw new IllegalArgumentException("drawing more than in the deck!!");

    ArrayList<UnoCard> retCards = new ArrayList<UnoCard>();
    for(i=0;i<n;++i,--cardsInDeck)
      retCards.add(deck[cardsInDeck]);

    return retCards;
  }

}// end of class
