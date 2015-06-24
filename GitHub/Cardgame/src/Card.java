import java.util.HashMap;
/* 
* Author : Qian Xu Date : April 30, 2015
*/

public class Card implements Comparable<Card>{
    private String suit;
    private String value;
    //static final String[] SUIT = {"Spades", "Hearts", "Clubs", "Diamonds"};
    //static final String[] VALUE = {"2", "3", "4", "5", "6", "7",
    //"8", "9", "10", "J", "Q", "K", "A"};
    public Card(String suit, String value){     
        this.value = value;
        this.suit = suit;
    }
 
    public String toString(){
        String x = value + " " + suit + " ";
        return x;
    }
     
    public String getSuit() {
        return suit;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    
    public int compareTo(Card c2) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("A", 14); map.put("3", 3);
        map.put("2", 2); map.put("5", 5);
        map.put("4", 4); map.put("7", 7);
        map.put("6", 6); map.put("9", 9);
        map.put("8", 8); map.put("J", 11);
        map.put("10", 10); map.put("K", 13);
        map.put("Q", 12);
        int dif =map.get(this.getValue()) - map.get(c2.getValue());
        if(dif==0){
        	return  this.getSuit().toString().compareTo(c2.getSuit().toString());
        } else if(dif > 0){
        	return 1;
        } else 
        	return -1;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit == null) {
			if (other.suit != null)
				return false;
		} else if (!suit.equals(other.suit))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	/*
	   public int hashCode()
	    {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((value == null) ? 0 : value.hashCode());
	        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
	        return result;
	    }
	    public boolean equals(Card card) {
	        if(card==null)
	          return false;
	        if(this == card){
	          return true;
	        }
	        return this.suit.equals(card.suit)&&this.value.equals(card.value);
	      }
*/
}