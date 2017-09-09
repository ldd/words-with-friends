package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
//import javax.swing.text.BadLocationException;

public class GridListener implements DocumentListener {
	GridPanel myPanel;
	GridListener(GridPanel aPanel){
		myPanel = aPanel;
	}
    @Override
    public void insertUpdate(DocumentEvent e) {
    	if (e.getOffset() >= 1){
			//int index = (int) e.getDocument().getProperty("name");
			JOptionPane.showMessageDialog(new JFrame(),
            "You CANNOT write more than 1 letter per tile.!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    	}
    }
	@Override
   public void removeUpdate(DocumentEvent e) {
    }
	@Override
    public void changedUpdate(DocumentEvent e) {
    }

}