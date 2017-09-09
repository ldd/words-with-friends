package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.PredefinedQueries;

public class OptionListener implements ActionListener{
	GridPanel myGrid;
	JButton myMainButton;
	
	OptionListener(GridPanel aGrid, JButton aButton){
		myGrid = aGrid;
		myMainButton = aButton;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		
		//IMPORTANT: This assumption could be big man
		myGrid.setPlayerPos("player2");

		if (button.getText().equals(MainPanel.OPTIONS.NewGame.toString())){
			JFrame frame = new JFrame();
            Object[] possibilities = PredefinedQueries.getInactiveGameFriends(LoginPanel.getUsername());
            String s = null;
            if (possibilities.length >0){
            s = (String)JOptionPane.showInputDialog(
                                frame,
                                "Select one of your friends:\n",
                                "Customized Dialog",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                possibilities,
                                null);
            }
            
            else{
                JOptionPane.showMessageDialog(frame,
                        "No available friends",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

            }

            //If a string was returned
            if ((s != null) && (s.length() > 0)) {
            	int game_id = PredefinedQueries.createGame(LoginPanel.getUsername(),s);
            	myGrid.setGame(game_id);
            	myGrid.setPlayerPos(PredefinedQueries.getPlayerPosition(game_id,LoginPanel.getUsername()));
            	//set letters and board too
            	String letters = PredefinedQueries.getBoardLetters(game_id);
            	myGrid.setLetter(letters);
            	myMainButton.doClick();
            	
                return;
            }

            //If you're here, the return value was null/empty.
        }
		
		else if (button.getText().equals(MainPanel.OPTIONS.ContinueGame.toString())){
			JFrame frame = new JFrame();
            Object[] possibilities = PredefinedQueries.getActiveGameFriends(LoginPanel.getUsername());
            String s = null;
            if (possibilities.length >0){
            s = (String)JOptionPane.showInputDialog(
                                frame,
                                "Select one of your friends:\n",
                                "Customized Dialog",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                possibilities,
                                null);
            }
            
            else{
                JOptionPane.showMessageDialog(frame,
                        "No available friends",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

            }

            //If a string was returned
            if ((s != null) && (s.length() > 0)) {
            	int game_id = PredefinedQueries.getActiveGame(LoginPanel.getUsername(), s);
            	System.out.println("This is the game: "+ game_id);
            	myGrid.setGame(game_id);
            	myGrid.setPlayerPos(PredefinedQueries.getPlayerPosition(game_id,LoginPanel.getUsername()));
            	
            	//set letters and board too
            	String letters = PredefinedQueries.getBoardLetters(game_id);
            	myGrid.setLetter(letters);
            	myMainButton.doClick();
            	
                return;
            }

            //If you're here, the return value was null/empty.
		}

		//Show friends
            else if (button.getText().equals(MainPanel.OPTIONS.ShowFriends.toString())){
			JFrame paco = new JFrame();
			
			String[] pa = new String[5]; 
			pa = PredefinedQueries.getFriends(LoginPanel.getUsername());//LoginPanel.getUsername());
			String res= new String();
			for(int i=0;i<pa.length;i++){
				if (pa[i] !=null){
					res += pa[i] + "\n";
				}
			}

			int type = JOptionPane.INFORMATION_MESSAGE ;
			if (res.length() <= 1){
				res = "No friends found!";
				type = JOptionPane.ERROR_MESSAGE;
			}
			
            JOptionPane.showMessageDialog(paco,
                    res,
                    "Friends",
                    type);


		}
		
		else if (button.getText().equals(MainPanel.OPTIONS.AddFriend.toString())){
			JFrame frame = new JFrame();
			//text input
            String s = (String)JOptionPane.showInputDialog(
                                frame,
                                "Enter a name:\n",
                                "Adding a friend",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "");

            //If a string was returned, say so.
            if ((s != null) && (s.length() > 0)) {
            	
            	boolean username_exists = PredefinedQueries.checkExists(s);
            	if(username_exists){
                	boolean not_friends = PredefinedQueries.addFriend(LoginPanel.getUsername(), s);
                   if (not_friends){
                	   JOptionPane.showMessageDialog(frame,
                            "Friend added!",
                            "Success!",
                            JOptionPane.INFORMATION_MESSAGE);
                   }
                   
                   else{
                	   JOptionPane.showMessageDialog(frame,
                               s + " is already your friend!",
                               "Success!",
                               JOptionPane.INFORMATION_MESSAGE);
                   }
            	}
            	
            	else{
                    JOptionPane.showMessageDialog(frame,
                            "Unable to add a friend: The player doesn't exist!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
            	}
                return;
            }
            
            //If you're here, the return value was invalid.
            //display message as error
            else{
                JOptionPane.showMessageDialog(frame,
                        "Unable to add a friend: No name entered",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
		}
		
		//Show friends
        else if (button.getText().equals(MainPanel.OPTIONS.CheckHistory.toString())){
			JFrame paco = new JFrame();
			
			String temp = PredefinedQueries.getHistory(LoginPanel.getUsername());
			String res = "Games Won: " + temp + "\n";
			int type = JOptionPane.INFORMATION_MESSAGE ;
			if (temp.length() < 1){
				res = "No history found!";
				type = JOptionPane.ERROR_MESSAGE;
			}
			
	        JOptionPane.showMessageDialog(paco,
	                res,
                "History",
                type);


		}
		
		else{
			JFrame paco = new JFrame();
            JOptionPane.showMessageDialog(paco,
                    "Yet to be inplemented",
                    "Not Implemented",
                    JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
