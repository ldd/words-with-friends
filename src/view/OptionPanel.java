package view;

import java.awt.*;
import javax.swing.*;

public class OptionPanel {

    JPanel optionPanel;
    GridLayout experimentLayout = new GridLayout(0,3);
    JButton[] buttons;
    MainPanel parent;
    int HGAP = 4; 
    int VGAP = 16; 
    int gap = 70;//800;

    public OptionPanel(int i, MainPanel aview){
    	buttons = new JButton[i];
    	parent = aview;
    }
    
    public JButton[] getButtons(){
    	return buttons;
    }
    
    
    public JPanel addComponentsToPane(final Container pane, String[] button_names) {
        optionPanel = new JPanel();

        optionPanel.setLayout(experimentLayout);

        //Set up components preferred size
        JButton b = new JButton("Just fake button");
        Dimension buttonSize = b.getPreferredSize();
        optionPanel.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5)+35+gap,
                (int)(buttonSize.getHeight() * 3.5)+35 * 2));
        
        //Add buttons to go to Grid Layout
        for (int i=0; i < button_names.length;i++){
        	buttons[i] = new JButton(button_names[i]);
        	optionPanel.add(buttons[i]);//new JButton(button_names[i]));
        	OptionListener myOptionListener = new OptionListener(parent.getGrid(),parent.getMainButton());
        	buttons[i].addActionListener(myOptionListener);
       	  buttons[i].addActionListener(parent.getActionEvent());
       	  buttons[i].setName("Option"+ i);
        }
        
        //Process the Apply gaps button press
        pane.add(optionPanel, BorderLayout.NORTH);
        experimentLayout.setHgap(HGAP);
        experimentLayout.setVgap(VGAP);
        return optionPanel;
    }
 
}