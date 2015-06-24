import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;  
import java.util.Collections;
import java.util.HashSet;
/* 
* Author : Qian Xu Date : April 30, 2015
*/
public class GuiTest extends JFrame implements ActionListener {
	// JPanel showCard = null;
	// JPanel showCur = null;
	// JLabel cardsDisplay = null;
	JFrame frame = new JFrame("card");
	static CardSystem cs = new CardSystem();
	JPanel jpleft = new JPanel();
	JPanel jpmid = new JPanel();
	JPanel jpright = new JPanel();
	//JPanel jpend = new JPanel();

	// JPanel mypanel =null;
	// JButton jb1,jb2;
	public static void main(String[] args) {
		cs.init();
		GuiTest demo = new GuiTest();

	}

	public GuiTest() {
		frame.setSize(1200, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addLeft(frame.getContentPane(), cs.curPlayer);
		addMid(frame.getContentPane(), cs.team1, cs.team2);
		addRight(frame.getContentPane(), cs.curPlayer,true);

		// frame.add(jpright,BorderLayout.EAST);

		frame.setVisible(true);
	}

	public void addLeft(Container pane, Player curPlayer) {
		JLabel player = new JLabel();
		if(cs.checkGameEnd()){
			 player = new JLabel("CurrentPlayer: no player");
		}
		else{
		player = new JLabel("CurrentPlayer: " + curPlayer.getpID()
				+ "  ");}
		player.setForeground(Color.red);
		JLabel t1 = new JLabel("team: t1 ");
		t1.setForeground(Color.blue);
		JLabel tp1 = new JLabel("p1 p2 p3");
		JLabel ts1 = new JLabel("Score: " + cs.team1.getScore());
		ts1.setForeground(Color.red);
		//JLabel t2 = new JLabel("team2: " + cs.team2.getScore());
		JLabel t2 = new JLabel("team: t2 ");
		t2.setForeground(Color.blue);
		JLabel tp2 = new JLabel("p4 p5 p6");
		JLabel ts2 = new JLabel("Score: " + cs.team2.getScore());
		ts2.setForeground(Color.red);
		JLabel winner = new JLabel("winner: " + cs.reportWinnerTeam());
		jpleft.setLayout(new GridLayout(0, 1));
		jpleft.add(player);
		jpleft.add(t1);
		jpleft.add(tp1);
		jpleft.add(ts1);
		jpleft.add(t2);
		jpleft.add(tp2);
		jpleft.add(ts2);
		jpleft.add(winner);
		jpleft.setBorder(new EmptyBorder(10, 10, 10, 10));
		pane.add(jpleft, BorderLayout.WEST);
	}

	public void addRight(final Container pane, final Player curPlayer,Boolean flag) {
		JLabel oppo = new JLabel("choose opponent: ");
		final JComboBox<String> oppoBox = new JComboBox<String>();
		final JComboBox<String> cardBox = new JComboBox<String>();
		Team team = cs.getOppoTeamOfPlayer(curPlayer.getpID());
		for (int i = 0; i < team.getPlayers().size(); i++) {
			oppoBox.addItem("" + team.getPlayers().get(i).getpID());
		}
		for (int i = 0; i < cs.otherCard().size(); i++) {
			cardBox.addItem((cs.otherCard()).get(i).toString());
		}
		// final Card cardTemp =
		// cs.getCard(cardBox.getSelectedItem().toString());
		JButton jb = new JButton("confirm");
		if(flag = false){
			//jb.setText("Game End");
			jb.setEnabled(false);
			
		}
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				final String oppoTemp = oppoBox.getSelectedItem().toString();
				final Object cardTemp = cardBox.getSelectedItem();
				Player p = cs.getPlayer(oppoTemp);
				Card cardToRemove = cs.getCard(cardTemp.toString());
				cs.callCard(oppoTemp, cardToRemove);
				
				jpmid.removeAll();
				addMid(frame.getContentPane(), cs.team1, cs.team2);
				jpmid.revalidate();
				
				jpleft.removeAll();
				addLeft(frame.getContentPane(), cs.curPlayer);
				jpleft.revalidate();
				if(cs.checkGameEnd()){
					jpright.removeAll();
					addRight(frame.getContentPane(), cs.curPlayer,false);
					jpright.revalidate();
				}else{
				jpright.removeAll();
				addRight(frame.getContentPane(), cs.curPlayer,true);
				jpright.revalidate();}
			}
		});
		JLabel cards = new JLabel("choose card:");
		jpright.setLayout(new GridLayout(6, 0));
		jpright.add(oppo);
		jpright.add(oppoBox);
		jpright.add(cards);
		jpright.add(cardBox);
		if(!cs.checkGameEnd()){
		jpright.add(jb);}
		jpright.setBorder(new EmptyBorder(10, 10, 10, 10));
		pane.add(jpright, BorderLayout.EAST);
	}

	public void addMid(Container pane, Team team1, Team team2) {
		jpmid.setLayout(new GridLayout(6, 1));
		for (int i = 0; i < team1.getPlayers().size(); i++) {
			JPanel jp1 = new JPanel();
			jp1.setLayout(new GridLayout(0, 6));
			for (int j = 0; j < team1.getPlayers().get(i).showCardList().size(); j++) {
				if(j ==0){
					JLabel pid = new JLabel(team1.getPlayers().get(i).getpID() );
					pid.setForeground(Color.red);
					jp1.add(pid);
				}
				JLabel jl = new JLabel(" "
						+ team1.getPlayers().get(i).showCardList().get(j));
				jp1.add(jl);
			}
			jp1.setBorder(BorderFactory.createLineBorder(Color.black));
			
			jpmid.add(jp1);
		}
		for (int i = 0; i < team2.getPlayers().size(); i++) {
			JPanel jp1 = new JPanel();
			jp1.setLayout(new GridLayout(0, 6));
			for (int j = 0; j < team2.getPlayers().get(i).showCardList().size(); j++) {
				if(j ==0){
					JLabel pid = new JLabel(team2.getPlayers().get(i).getpID() );
					pid.setForeground(Color.red);
					jp1.add(pid);
				}
				JLabel jl = new JLabel(" "
						+ team2.getPlayers().get(i).showCardList().get(j));
				jp1.add(jl);
			}
			jp1.setBorder(BorderFactory.createLineBorder(Color.black));
			jpmid.add(jp1);
		}
		jpmid.setBorder(new EmptyBorder(10, 10, 10, 10));
		pane.add(jpmid, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



}
