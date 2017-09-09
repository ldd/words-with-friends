package model;
import javax.swing.JTextField;

public class BuildAction {
	
	public static String buildBoard(JTextField[] curBoard){
		String newBoard = "";
		for (int i=0;i<15*15 ; i++){
			if (curBoard[i].getText().equals(""))
				newBoard = newBoard + "0";
			else
				newBoard = newBoard + curBoard[i].getText();
		}
		return newBoard;
	}
	
}
