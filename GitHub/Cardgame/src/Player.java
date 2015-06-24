import java.util.ArrayList;
import java.util.Collections;
/* 
* Author : Qian Xu Date : April 30, 2015
*/

public class Player {
	private String pID;
	private ArrayList<Card> cards = new ArrayList<Card>();

	
    public Player(String pID){
    	this.pID = pID;
    }
	public ArrayList<String> showCardList(){
		ArrayList<String> al= new ArrayList<String>();
		Collections.sort(cards);
		for(int i = 0; i < cards.size(); i++){
			al.add(cards.get(i).toString()+" ");
		}
		return al;
	}
	Boolean hasCard(){
		return cards.isEmpty();
	}

	
	
	
	
/*
 * get and set
 * 	
 */
	public String getpID() {
		return pID;
	}
	public void setpID(String pID) {
		this.pID = pID;
	}
	public ArrayList<Card> getCards() {
		Collections.sort(cards);
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

}
	

