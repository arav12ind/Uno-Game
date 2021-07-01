/**
* THE UNO DECK HAS 108 CARDS..
* THE CARDS ARE 1 ZERO, 2 OF ONE TO NINE, 2 OF DrawTwo,Skip,Reverse.
* PLUS FOUR WILD AND WILD DRAW FOUR CARDS.
*/
package uno;

import java.util.*;

public class UnoDeck
{
  private Stack<UnoCard> deck;
  public UnoDeck()
  {
    deck = new Stack<>();
    for(UnoCard.Colour c : UnoCard.Colour.values())
    {
      if( c != UnoCard.Colour.Wild)
      {
        for(UnoCard.Number n : UnoCard.Number.values())
        {
          if(n != UnoCard.Number.WildDrawFour && n != UnoCard.Number.Wild)
          {
            deck.push(new UnoCard(c, n));
          }
        }
      }
    }
    deck.push(new UnoCard(UnoCard.Colour.Wild, UnoCard.Number.Wild));
    deck.push(new UnoCard(UnoCard.Colour.Wild, UnoCard.Number.Wild));
    deck.push(new UnoCard(UnoCard.Colour.Wild, UnoCard.Number.Wild));
    deck.push(new UnoCard(UnoCard.Colour.Wild, UnoCard.Number.Wild));
    deck.push(new UnoCard(UnoCard.Colour.Wild, UnoCard.Number.WildDrawFour));
    deck.push(new UnoCard(UnoCard.Colour.Wild, UnoCard.Number.WildDrawFour));
    deck.push(new UnoCard(UnoCard.Colour.Wild, UnoCard.Number.WildDrawFour));
    deck.push(new UnoCard(UnoCard.Colour.Wild, UnoCard.Number.WildDrawFour));
  }

  public int getCardsInDeck()
  {
    return deck.size() + 1;
  }

  public void replaceDeckWith(Stack<UnoCard> cards)  // TO REPLACE THE PLAYED DOWN CARDS INTO DECK.
  {
    deck = cards;
  }
  public boolean isEmpty()
  {
    return deck.isEmpty();
  }

  public void shuffle()
  {
    Collections.shuffle(deck);
  }// end of shuffle function

  public UnoCard drawCard(Stack<UnoCard> cards) throws EmptyStackException
  {
    if(deck.isEmpty())
    {
      deck = (Stack<UnoCard>) cards.clone();
      cards.clear();
    }
    return deck.pop();
  }
   public ArrayList<UnoCard> drawMultipleCards(int n,Stack<UnoCard> cards) throws IllegalArgumentException
  {
    if(n<0)
      throw new IllegalArgumentException("Only positive draw allowed");
    if(n>deck.size()) {
      deck = (Stack<UnoCard>) cards.clone();
      cards.clear();
    }
    ArrayList<UnoCard> drawn = new ArrayList<UnoCard>();
    for(int i=0;i<n;++i)
    {
      drawn.add(deck.pop());
    }
    return drawn;
  }
}// end of class
