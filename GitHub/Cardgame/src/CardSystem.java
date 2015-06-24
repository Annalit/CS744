import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

/* 
* Author : Qian Xu Date : April 30, 2015
*/
public class CardSystem {
	Card[] cardGroup = new Card[52];// to save all cards
    static final String[] SUIT = {"Spade", "Heart", "Club", "Diamond"};
    static final String[] VALUE = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    Team team1 = new Team("t1");
    Team team2 = new Team("t2");
    Player curPlayer = new Player(" ");
    Team curTeam;
	
    ArrayList<HashSet<Card>> condition = new ArrayList<HashSet<Card>>();
    public void initCardGroup(){
    	for(int i = 0; i< 52; i++){
    		Card card=new Card(SUIT[i/13],VALUE[i%13]);
    		cardGroup[i] = card;
    	}
    } 
    
    public void shuffle(){
    	Random random = new Random();
    	for (int i=0; i < cardGroup.length;i++){
    		int j = random.nextInt(52);
    		Card temp = cardGroup[j];
    		cardGroup[j] = cardGroup[i];
    		cardGroup[i] = temp;
    	}
    }

    public void initTeam(){
    	 	Player player1 = new Player("p1");
    	 	curPlayer=player1;
    	    Player player2 = new Player("p2");
    	    Player player3 = new Player("p3");
    	    Player player4 = new Player("p4");
    	    Player player5 = new Player("p5");
    	    Player player6 = new Player("p6");
    	    for(int i = 0; i< 9; i++){
        		player1.getCards().add(cardGroup[6*i]);
        		player2.getCards().add(cardGroup[6*i+1]);
        		player4.getCards().add(cardGroup[6*i+2]);
        		player5.getCards().add(cardGroup[6*i+3]);
        		if(i < 8){
        			player3.getCards().add(cardGroup[6*i+4]);
        			player6.getCards().add(cardGroup[6*i+5]);
        		}
        	}
    	    Collections.sort(player1.getCards());
    	    Collections.sort(player2.getCards());
    	    Collections.sort(player3.getCards());
    	    Collections.sort(player4.getCards());
    	    Collections.sort(player5.getCards());
    	    Collections.sort(player6.getCards());
    		team1.getPlayers().add(player1);
        	team1.getPlayers().add(player2);
        	team1.getPlayers().add(player3);
        	team2.getPlayers().add(player4);
        	team2.getPlayers().add(player5);
        	team2.getPlayers().add(player6);
        	
    }
    
	public void initCondition() {
		HashSet<Card> condition1 = new HashSet<Card>();
		HashSet<Card> condition2 = new HashSet<Card>();
		HashSet<Card> condition3 = new HashSet<Card>();
		HashSet<Card> condition4 = new HashSet<Card>();
		HashSet<Card> condition5 = new HashSet<Card>();
		HashSet<Card> condition6 = new HashSet<Card>();
		HashSet<Card> condition7 = new HashSet<Card>();
		HashSet<Card> condition8 = new HashSet<Card>();

		for (int i = 0; i < 7; i++) {
			condition1.add(new Card(SUIT[0], VALUE[i]));
			condition2.add(new Card(SUIT[1], VALUE[i]));
			condition3.add(new Card(SUIT[2], VALUE[i]));
			condition4.add(new Card(SUIT[3], VALUE[i]));
		}
		for (int i = 7; i < 13; i++) {
			condition5.add(new Card(SUIT[0], VALUE[i]));
			condition6.add(new Card(SUIT[1], VALUE[i]));
			condition7.add(new Card(SUIT[2], VALUE[i]));
			condition8.add(new Card(SUIT[3], VALUE[i]));
		}
		condition.add(condition1);
		condition.add(condition2);
		condition.add(condition3);
		condition.add(condition4);
		condition.add(condition5);
		condition.add(condition6);
		condition.add(condition7);
		condition.add(condition8);
	}
    public void init(){
    	this.initCardGroup();
    	this.shuffle();
    	this.initTeam();
    	this.initCondition();
    }
    //52/6 = 9,9,8,9,9,8

    
	public void callCard(String opID, Card card) {
		Boolean flagOppo = false;
		Boolean flagCur = false;
		if (contains(opID, card)) {
			Team team = getTeamOfPlayer(opID);
			curPlayer.getCards().add(card);
			for(int i = team.getPlayers().size()-1; i >= 0;i--){
				Player opp = team.getPlayers().get(i);
				if(opp.getpID().equals(opID)){
					opp.getCards().remove(card);
					if(checkPlayerEnd(opp.getpID())){
						flagOppo = true;
					}
					break;
				}				
			}
			//case 1. curPlayer don't have card, it's opponent's turn
			findCompetition(curPlayer.getpID());
			if(checkPlayerEnd(curPlayer.getpID())){
				flagCur = true;
			}
			if(checkGameEnd()){
				return;
			}
			if(flagOppo&&flagCur){//case1: both curPlayer and opponent don't have cards
				Random random = new Random();
				curPlayer = team1.getPlayers().get(random.nextInt(team1.getPlayers().size()));
				return;
			}
			//case2: curPlayer don't have card
			else if(flagCur){
				curPlayer = getPlayer(opID);
				return;
			}
			else{
			takeTurns(curPlayer.getpID());
			}
		} else {
			takeTurns(opID);// add takeTurns
		}
	}  
	public Boolean checkPlayerEnd(String opID){
		if(getPlayer(opID).getCards().isEmpty()){
			getTeamOfPlayer(opID).deletePlayer(opID);
			return true;
		}
		return false;
	}
   	
    
    public Player getPlayer(String pID){
    	for(int i = 0; i < team1.getPlayers().size(); i++){
    		Player tempPlayer = team1.getPlayers().get(i);
    		if(tempPlayer.getpID().equals(pID)){
    			return tempPlayer;
    		}
    	}
    	for(int i = 0; i < team2.getPlayers().size(); i++){
    		Player tempPlayer = team2.getPlayers().get(i);
    		if(tempPlayer.getpID().equals(pID)){
    			return tempPlayer;
    		}
    	}
    	return null;
    }
    public Team getTeam(String tID){
    	if(tID.equals("t1")){
    		return team1;
    	} else {
    		return team2;
    	}
    }
    public Card getCard(String cardString){
    	for(int i = 0; i < cardGroup.length;i++){
    		if(cardGroup[i].toString().equals(cardString)){
    			return cardGroup[i];
    		}
    	}
    	return null;
    }
    public Team getTeamOfPlayer(String pID){

    	for(int i = 0; i<team1.getPlayers().size(); i++){
    		if(team1.getPlayers().get(i).getpID().equals(pID)){
    			return team1;
    		}
    	}
    	return team2;
    }
    public Team getOppoTeamOfPlayer(String pID){
    	if(getTeamOfPlayer(pID).gettID().equals("t1")){
    		return team2;
    	}
    	else
    		return team1;
    }

	public void findCompetition(String pID) {
		Player player = getPlayer(pID);
		ArrayList<Card> cards = player.getCards();
		Boolean competition = false;
		HashSet<Card> spe_condition = new HashSet<Card>();
		int count = 0;// max = 6
		for (int i = 0; i < condition.size(); i++) {
			for (int j = 0; j < cards.size(); j++) {
				HashSet<Card> cur = condition.get(i);
				Card curcard = cards.get(j);
				if (cur.contains(curcard)) {
					count++;
				}
				if (i < 4) {// 1,2,3,4----2-8
					if (count == 7) {
						competition = true;
						Team t = getTeamOfPlayer(player.getpID());
						t.setScore(t.getScore() + 1);
						spe_condition = cur;
						break;
					}
				} else if (count == 6) {
						competition = true;
						Team t = getTeamOfPlayer(player.getpID());
						t.setScore(t.getScore() + 2);
					spe_condition = cur;
					break;
				}
			}
			if (competition == true) {
				break;
			}
			count = 0;
		}
    	if(competition = true){
    		for(int i = cards.size()-1; i >= 0; i--){
    			if(spe_condition.contains(cards.get(i))){
    				cards.remove(i);
    			}
    		}
    	}
    }
    public void takeTurns(String pID){
    	curPlayer = getPlayer(pID);    	
    }
    public Boolean checkGameEnd(){
    	return team1.getPlayers().isEmpty()||team2.getPlayers().isEmpty();
    }
    public String reportWinnerTeam(){
    	if(!checkGameEnd()){
    		return " ";
    	}
    	if(team1.getScore()>team2.getScore()){
    		return team1.gettID();
    	}
    	else if(team2.getScore()>team1.getScore()){
    	return team2.gettID();}
    	else
    		return "no winner";
    }
    public ArrayList<Card> otherCard(){
    	ArrayList<Card> result = new ArrayList<Card>();
    	HashSet<Card> set = new HashSet<Card>();
    	for(int i =0; i<curPlayer.getCards().size();i++){
    		set.add(curPlayer.getCards().get(i));
    	}
		for(int i = 0; i < cardGroup.length;i++){
			if(set.contains(cardGroup[i])){
				continue;
			} else {
				result.add(cardGroup[i]);
			}
		}
		Collections.sort(result);
		return result;
    }
    public Boolean contains(String pid, Card card){
    	Player oppo = getPlayer(pid);
    	for(int i =0; i<oppo.getCards().size();i++){
    		if(oppo.getCards().get(i).equals(card)){
    			return true;
    		}
    	}
    	return false;
    }
  
}