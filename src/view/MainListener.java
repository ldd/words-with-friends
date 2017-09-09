package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.BuildAction;
import model.PredefinedQueries;

public class MainListener implements ActionListener {
    private JPanel myCards;
    private RegisterPanel myRegister;
	private GridPanel myGrid;
	private ArrayList<JButton> myButtons;
	private JFrame mainFrame;
	
	MainListener(JFrame aFrame, JPanel cards, RegisterPanel aRegistration, GridPanel aGrid, ArrayList<JButton> buttons){
		myCards = cards;
		myGrid = aGrid;
		myButtons = buttons;
		mainFrame = aFrame;
		myRegister = aRegistration;
	}
	
	public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout)(myCards.getLayout());
		JButton button = (JButton) e.getSource();
		JButton main = myButtons.get(0);
		JButton logout = myButtons.get(1);
		JButton back = myButtons.get(2);
		JButton register = myButtons.get(3);
		
		if (button.getName().equals("Exit"))
			System.exit(0);



		if (button.equals(back)){
			main.setVisible(false);
			back.setVisible(false);
			cl.show(myCards,MainPanel.PANELNAMES.Options.toString());
	        JButton b = new JButton("Just fake button");
	        Dimension buttonSize = b.getPreferredSize();
			myCards.setPreferredSize (new Dimension((int)(buttonSize.getWidth() * 2.5)+35+70,
	                (int)(buttonSize.getHeight() * 3.5)+35 * 2));
			mainFrame.pack();
			return;
		}
		
		
		//actions to perform when logout button is clicked
		if (button.equals(logout)){
			main.setText("Login");
			main.setVisible(true);
			logout.setVisible(false);
			back.setVisible(false);
			register.setVisible(true);
			
			//show login card and resize panel
			cl.show(myCards,MainPanel.PANELNAMES.Login.toString());
	        JButton b = new JButton("Just fake button");
	        Dimension buttonSize = b.getPreferredSize();
			myCards.setPreferredSize (new Dimension((int)(buttonSize.getWidth() * 2.5)+35+70,
	                (int)(buttonSize.getHeight() * 3.5)+35 * 2));
			mainFrame.pack();
	        return;
		}

		
		main.setText("Submit");
		logout.setVisible(true);
		
		//get current card's name
		String card_name=myCards.getComponents()[0].getName();
		for (Component comp : myCards.getComponents()) {
		    if (comp.isVisible() == true) {
		        card_name = comp.getName();
		    }
		}
		
		if (button.equals(register)){
			main.setVisible(true);
			logout.setVisible(false);

			//second click
			if (card_name.equals(MainPanel.PANELNAMES.Register.toString())){
				System.out.println(myRegister.getFields()[0] + myRegister.getFields()[1] +
						myRegister.getFields()[2]+myRegister.getFields()[3]+myRegister.getFields()[4]);
				PredefinedQueries.register(myRegister.getFields());

				//show login card
				cl.show(myCards,MainPanel.PANELNAMES.Login.toString());
		        JButton b = new JButton("Just fake button");
		        Dimension buttonSize = b.getPreferredSize();
				myCards.setPreferredSize (new Dimension((int)(buttonSize.getWidth() * 2.5)+35+70,
		                (int)(buttonSize.getHeight() * 3.5)+35 * 2));
				mainFrame.pack();
				return;
			}

			//first click
			else{
				main.setVisible(false);
				back.setVisible(false);
				cl.show(myCards,MainPanel.PANELNAMES.Register.toString());
				mainFrame.repaint();
				mainFrame.revalidate();	
				myCards.repaint();
				//resize panel
				myCards.setPreferredSize (new Dimension(600, 350));
				mainFrame.setLocationRelativeTo(null);
				mainFrame.pack();
				return;
			}

		}
		//login and check credentials
		if (card_name.equals(MainPanel.PANELNAMES.Login.toString()) && !button.equals(logout)){
			main.setVisible(false);
			back.setVisible(false);
			register.setVisible(false);
			String usr = LoginPanel.getUsername();
			String psd = LoginPanel.getPassword();
	
			if (PredefinedQueries.checkCredentials(usr, psd)){
				cl = (CardLayout)(myCards.getLayout());
		        cl.show(myCards,MainPanel.PANELNAMES.Options.toString());
			}
			
			else{
				main.setVisible(false);
				logout.setVisible(false);
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame,
                        "Invalid credentials!",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
				main.setVisible(true);
				register.setVisible(true);

			}
		

		}
		
		
		else{
			register.setVisible(false);
			if (card_name.equals(MainPanel.PANELNAMES.Options.toString())){
				back.setVisible(true);
				main.setVisible(true);

				if (button.getName().equals("main button")){
					//redraw cards
					JPanel wrapper = new JPanel( new BorderLayout());
					wrapper.setName(MainPanel.PANELNAMES.Grid.toString());
					
					JPanel gridPanel = myGrid.createPanel();
					JPanel letterPanel = new JPanel();
					letterPanel.add(new JLabel("Current Letters :"));
					letterPanel.add(new JLabel(PredefinedQueries.getPlayerLetters(myGrid.getPlayerPos(), myGrid.getGame()).toUpperCase()));
					wrapper.add(gridPanel, BorderLayout.CENTER);
					wrapper.add(letterPanel, BorderLayout.SOUTH);

					myCards.add(wrapper, BorderLayout.CENTER);

					mainFrame.repaint();
					mainFrame.revalidate();	
					myCards.repaint();
					//resize panel
					myCards.setPreferredSize (new Dimension(600, 600));
					mainFrame.setLocationRelativeTo(null);
					mainFrame.pack();
					cl.last(myCards);
				}
				else{
					;//ignore this click				
				}
			}
		
		//'game' or grid view
		else if(card_name.equals(MainPanel.PANELNAMES.Grid.toString()) || button.getName().equals("Option1")){
			back.setVisible(true);
			main.setVisible(true);


			//get string to update the board's state
			String newBoard = BuildAction.buildBoard(myGrid.textField);

			
			//check if it is the turn of the player and if so, finish turn
			boolean checkTurn = PredefinedQueries.isTurn(LoginPanel.getUsername(),myGrid.getPlayerPos(),Integer.toString(myGrid.getGame()));
			 if (checkTurn){
				//update board's state
				myGrid.setLetter(newBoard);
				myGrid.setPlayerPos(PredefinedQueries.getPlayerPosition(myGrid.getGame(),LoginPanel.getUsername()));
				PredefinedQueries.submitWord(newBoard,myGrid.getGame());
				PredefinedQueries.updateTurn(myGrid.getGame(),checkTurn, myGrid.getPlayerPos()); 
				
				//redraw card
				myCards.remove(2);
				JPanel temp = myGrid.createPanel(newBoard);
				myCards.add(temp);
				temp.setName(MainPanel.PANELNAMES.Grid.toString());
				mainFrame.repaint();
				mainFrame.revalidate();
				mainFrame.pack();

			 }
			 else{
				 JFrame frame = new JFrame();
                 JOptionPane.showMessageDialog(frame,
                         "It is not your turn!",
                         "Error",
                         JOptionPane.ERROR_MESSAGE);

			 }
					
			cl.last(myCards);
		}
			
		else
			System.out.println("Reached an invalid Card");
		}

	}

}
