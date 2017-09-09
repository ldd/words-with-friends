package view;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import utilities.SpringUtilities;
import utilities.UpcaseFilter;

import java.awt.*;

public class GridPanel {
	private final boolean IS_EDITABLE = false;
	public char[] position;
	public char[] letters;
	public JTextField[] textField = new JTextField[15*15];
	public int gameId;
	public String playerPos;
    MainPanel parent;
	

	public  JPanel createPanel(String board_layout, String contents)  {
        JPanel panel = new JPanel(new SpringLayout());
        position = board_layout.toCharArray();
        letters = contents.toCharArray();
        int rows = 15;
        int cols = 15;
        final int initX = 0;
        final int initY =0;
        final int xPad =-1;
        final int yPad = -2;
		
        for (int r = 0; r < rows; r++) {
        	for (int t = 0; t < cols; t++) {
        		int index = cols*t+r;
                textField[index] = new JTextField(1);
                if (letters[index] != '0'){
                	textField[index].setBackground(Color.getHSBColor(41,100,100));
                	textField[index].setEditable(IS_EDITABLE);
                	textField[index].setText(""+letters[15*t+r] + "");
                }
                else
                    textField[index].setText("");
                	
                
                if (position[index] == '2'){
                	textField[index].setBackground(Color.GREEN);
                }
                else if (position[index] == '3'){
                	textField[index].setBackground(Color.RED);
                }
                
                panel.add(textField[index]);
  //              textField[index].getDocument().addDocumentListener(parent);
                textField[index].getDocument().putProperty("name", index);
                DocumentFilter dfilter = new UpcaseFilter();
                ((AbstractDocument)textField[index].getDocument()).setDocumentFilter(dfilter);
                textField[index].getDocument().addDocumentListener(new GridListener(this));
                //textField[index].setDocument(null);
        	}
        }

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel, //parent
                                        rows, cols,
                                        initX, initY,  //initX, initY
                                        xPad, yPad); //xPad, yPad

        //Set up the content pane.
        //panel.setOpaque(true); //content panes must be opaque

        //Display the window.
        return panel;
    }
	
    public  JPanel createPanel(String contents) {
		String position = new String(this.position);
		return createPanel(position,contents);
	}
	public  JPanel createPanel() {
		String position = new String(this.position);
		String contents = new String(this.letters);
		return createPanel(position,contents);
	}
	
	public int getGame() {
		return gameId;
	}
	public void setGame(int i) {
		gameId = i;
	}

	public  char[] getLetters(){
		return letters;
	}

	public  void setLetter(String l){
		letters =l.toCharArray();
	}

	public  void setLetter(char[] l){
		letters =l;
	}

	public JTextField[] getJTextFields(){
		return textField;
	}
	
	public void setJTextField(int index, String s){
		textField[index].setText(s);
	}
	
	public String getPlayerPos() {
		return playerPos;
	}
	public void setPlayerPos(String i) {
		playerPos = i;
	}

}