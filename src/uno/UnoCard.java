package uno;

public class UnoCard {
  public enum Colour {
    Red, Blue, Green, Yellow, Wild;

    public boolean matches(UnoCard.Colour colour) {
      return this == Colour.Wild || colour == Colour.Wild || this == colour;
    }// 0 is red, 1 is blue...
  }

  public enum Number {
    Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, Skip,
    Reverse, Wild, WildDrawFour;

    public boolean matches(UnoCard.Number number) {
      return this == Number.Wild || number == Number.Wild || this == number || this == Number.WildDrawFour || number == Number.WildDrawFour;
    }
  }

  private final UnoCard.Colour colour;
  private final UnoCard.Number number;
  public boolean equals(UnoCard card)
  {
    return this.colour.equals(card.getColour()) && this.number.equals(card.getNumber());
  }

  public UnoCard(UnoCard.Colour colour, UnoCard.Number number) {
    this.colour = colour;
    this.number = number;
  }

  public UnoCard(String name) throws IllegalArgumentException {
    String[] tkn = name.split("-");
    this.number = Number.valueOf(tkn[0]);
    this.colour = Colour.valueOf(tkn[1]);
  }

  public UnoCard.Colour getColour() {
    return this.colour;
  }

  public UnoCard.Number getNumber() {
    return this.number;
  }

  public String toString() {
    return this.number + "-" + this.colour;
  }

  public boolean matchesColour(UnoCard card) {
    return this.colour.matches(card.getColour());
  }

  public boolean matchesNumber(UnoCard card) {
    return this.number.matches(card.getNumber());
  }

  public boolean matches(UnoCard card)
  {
    return this.matchesColour(card) || this.matchesNumber(card);
  }


  public String toPath() {
    return "/resources/" + this + ".jpg";
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
