package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.PredefinedQueries;



public class MainPanel {
    public static enum PANELNAMES  {Login, Options, Grid, Register}
    public static enum OPTIONS {NewGame, SomeFunction, AddFriend, ContinueGame,CheckHistory,ShowFriends}

    static JFrame mainFrame;
	private JPanel cards; //a panel that uses CardLayout
    private GridPanel aGridPanel = new GridPanel();
    private RegisterPanel aRegisterPanel;
    private JButton main, logout, back, register;
    private MainListener action;
    
    MainPanel(JFrame frame){
    	mainFrame = frame;
    	aGridPanel = new GridPanel();
    }
    
    
     public void addComponentToPane(Container pane) {
    	JPanel mainPane = new JPanel(); //use FlowLayout
       
        main = new JButton ("Login");
        mainPane.add(main);
        main.setName("main button");

        logout = new JButton ("Logout");
        mainPane.add(logout);
        logout.setName("logout");
        logout.setVisible(false);

        back = new JButton ("Back");
        mainPane.add(back);
        back.setName("Back");
        back.setVisible(false);

        register = new JButton ("Register");
        mainPane.add(register);
        register.setName("Register");

        JButton exit = new JButton ("Exit");
        exit.setName("Exit");
        mainPane.add(exit);
        
        ArrayList<JButton> buttons = new ArrayList<JButton>();
        buttons.add(main);
        buttons.add(logout);
        buttons.add(back);
        buttons.add(register);
        
        //Create the Login panel as a card
        JPanel card1 = LoginPanel.createPanel();
        
        //Create the Options panel as a card
        String[] b_names = {
        		OPTIONS.NewGame.toString(), OPTIONS.SomeFunction.toString(),OPTIONS.AddFriend.toString(),
        		OPTIONS.ContinueGame.toString(),OPTIONS.CheckHistory.toString(),OPTIONS.ShowFriends.toString()
        		};
        OptionPanel temp = new OptionPanel(b_names.length,this);
        JPanel card2 = temp.addComponentsToPane(new JPanel(), b_names);

        //Create the Grid panel
		String s = PredefinedQueries.getBoard(0);
		String letters = PredefinedQueries.getBoardLetters(0);
		aGridPanel.createPanel(s,letters);

		//Create the Register panel as a card
		aRegisterPanel = new RegisterPanel();
		JPanel card3 = aRegisterPanel.createPanel();
		
        //Create the panels that contain the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, PANELNAMES.Login.toString());
        card1.setName("Login");
        cards.add(card2, PANELNAMES.Options.toString());
        card2.setName("Options");
        cards.add(card3, PANELNAMES.Register.toString());
        card3.setName("Register");
        pane.add(mainPane, BorderLayout.CENTER);
        pane.add(cards, BorderLayout.PAGE_START);


        //add button events
        action = new MainListener(mainFrame, cards, aRegisterPanel, aGridPanel, buttons);

        main.addActionListener(action);
        logout.addActionListener(action);
        exit.addActionListener(action);
        back.addActionListener(action);
        register.addActionListener(action);

        //calculate reasonable limits for the window
        JButton b = new JButton("Just fake button");
        Dimension buttonSize = b.getPreferredSize();
		cards.setPreferredSize (new Dimension((int)(buttonSize.getWidth() * 2.5)+35+70,
                (int)(buttonSize.getHeight() * 3.5)+35 * 2));
		
		mainFrame.pack();

    }
    
    public JFrame getFrame(){
    	return mainFrame;
    }

    public GridPanel getGrid(){
    	return aGridPanel;
    }

    public JButton getMainButton(){
    	return main;
    }

    public JButton getLogoutButton(){
    	return logout;
    }
    
    public MainListener getActionEvent(){
    	return action;
    }

}