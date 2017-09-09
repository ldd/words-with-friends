package utilities;
//UpcaseFilter.java
//A simple DocumentFilter that maps lowercase letters to uppercase.
//from examples found on: http://examples.oreilly.com/jswing2/code/ch22/UpcaseFilter.java

import javax.swing.text.*;

public class UpcaseFilter extends DocumentFilter {

public void insertString(DocumentFilter.FilterBypass fb, int offset,
             String text, AttributeSet attr) throws BadLocationException
{
	
	if (fb.getDocument().getLength() == 0){
//		attr.
		fb.insertString(offset, text.toUpperCase(), attr);
	}
	
	if (fb.getDocument().getLength() >= 1)
		fb.insertString(offset, text, attr);

	
}

//no need to override remove(): inherited version allows all removals

public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
               String text, AttributeSet attr) throws BadLocationException
{
 fb.replace(offset, length, text.toUpperCase(), attr);
}

}