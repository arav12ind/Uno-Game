package uno;

public class UnoCard
{
  public enum Colour
  {
    Red, Blue, Green, Yellow, Wild;

//    public final String colourLabel;
    private static final uno.UnoCard.Colour colours[] = uno.UnoCard.Colour.values();
/*
    private Colour(String colourLabel)
    {
      this.colourLabel = colourLabel;
    }
*/
    public static uno.UnoCard.Colour getColour(int i)
    {
      return uno.UnoCard.Colour.colours[i];
    } // 0 is red, 1 is blue...
  }

  public enum Number
  {
    Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, Skip,
    Reverse, Wild, WildDrawFour;

  //  public final String numberLabel;
    private static final uno.UnoCard.Number numbers[] = uno.UnoCard.Number.values();
/*    private Number(String numberLabel)
    {
      this.numberLabel = numberLabel;
    }
*/    public static uno.UnoCard.Number getNumber(int i)
    {
      return uno.UnoCard.Number.numbers[i];
    }
  }

  private final uno.UnoCard.Colour colour;
  private final uno.UnoCard.Number number;


  public UnoCard(uno.UnoCard.Colour colour, uno.UnoCard.Number number)
  {
    this.colour = colour;
    this.number = number;
  }

  public uno.UnoCard.Colour getColour()
  {
    return this.colour;
  }

  public uno.UnoCard.Number getNumber()
  {
    return this.number;
  }

  public String toString()
  {
    return this.number + "-" + this.colour;
  }

  public boolean equalsCol(uno.UnoCard.Colour colour)
  {
    uno.UnoCard.Colour wild = uno.UnoCard.Colour.Wild;

    if(this.colour == wild || colour == wild)
      return true;
    if(this.colour == colour)
        return true;
    else return false;
  }

  public boolean equalsNum(uno.UnoCard.Number number)
  {
    uno.UnoCard.Number wild = uno.UnoCard.Number.Wild;
    if(this.number == wild || number == wild)
      return true;
    if(this.number == number)
      return true;
    else return false;
  }


  public boolean equals(UnoCard card)
  {
    return (this.equalsNum(card.number)||this.equalsCol(card.colour));
  }
}

/*
  public static Colour valueOfColourLabel(String label)
  {
    for(Colour colour : Colour.values())
    {
      if(colour.colourLabel.equals(label))
        break;
    }
    return colour;
  }

  public static Number valueOfNumberLabel(String label)
  {
    for(Number number : Number.values())
    {
      if(number.numberLabel.equals(label))
        return number;
    }
  }
*/
