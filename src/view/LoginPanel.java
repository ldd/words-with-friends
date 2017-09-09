package view;
import javax.swing.*;

import utilities.SpringUtilities;


public class LoginPanel {
	private static JTextField username;
	private static JPasswordField password;

	public static String getUsername(){
		return username.getText();
	}

	public static String getPassword(){
		char[] pswd = password.getPassword();
		String password = new String(pswd);
		return password;
	}

	public static void setCreds(String s1, String s2){
		username.setText(s1);
		password.setText(s2);
	}

    public static JPanel createPanel() {
        String[] labels = {"Username: ", "Password: "};
        int numPairs = labels.length;

        //Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
        JLabel l = new JLabel(labels[0], JLabel.TRAILING);
        p.add(l);
        username = new JTextField(10);
        l.setLabelFor(username);
        p.add(username);

        l = new JLabel(labels[1], JLabel.TRAILING);
        p.add(l);
        password = new JPasswordField(10);
        l.setLabelFor(password);
        p.add(password);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                                        numPairs, 2, //rows, cols
                                        36, 36,        //initX, initY
                                        30, 30);       //xPad, yPad

        //p.setOpaque(true);  //content panes must be opaque
        return p;
    }
}