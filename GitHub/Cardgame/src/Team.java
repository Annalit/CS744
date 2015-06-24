import java.util.ArrayList;
import java.util.Iterator;
/* 
* Author : Qian Xu Date : April 30, 2015
*/

public class Team {
	private String tID;
	private int score;

	private ArrayList<Player> players = new ArrayList<Player>(); 
	 public Team(String tID){
	    	this.tID = tID;
	    }

	public void deletePlayer(String pid){
		Iterator<Player> iter = players.iterator();
		while(iter.hasNext()){
			if(iter.next().getpID().equals(pid)){
				iter.remove();
			}
		}
	}	 
	 
/*
 * get and set 
 * */	 
	 public String gettID() {
		return tID;
	}
	public void settID(String tID) {
		this.tID = tID;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	 public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
