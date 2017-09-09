package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import utilities.SpringUtilities;

public class RegisterPanel {
	private JTextField username;
	private JPasswordField password;
	private JTextField e_mail;
	private JTextField last_name;
	private JTextField first_name;
	
    public JPanel createPanel() {
        String[] labels = {"Username: ", "Password: ", "E-mail: ", "First Name: ", "Last Name: "};
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

        l = new JLabel(labels[2], JLabel.TRAILING);
        p.add(l);
        e_mail = new JTextField(10);
        l.setLabelFor(e_mail);
        p.add(e_mail);

        l = new JLabel(labels[3], JLabel.TRAILING);
        p.add(l);
        last_name = new JTextField(10);
        l.setLabelFor(last_name);
        p.add(last_name);

        l = new JLabel(labels[4], JLabel.TRAILING);
        p.add(l);
        first_name = new JTextField(10);
        l.setLabelFor(first_name);
        p.add(first_name);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                                        numPairs, 2, //rows, cols
                                        36, 36,        //initX, initY
                                        30, 30);       //xPad, yPad

        //p.setOpaque(true);  //content panes must be opaque
        return p;
    }

	public String getUsername(){
		return username.getText();
	}

	public String getPassword(){
		char[] pswd = password.getPassword();
		String password = new String(pswd);
		return password;
	}

	public String getEmail(){
		return e_mail.getText();
	}

	public String getFirstName(){
		return first_name.getText();
	}

	public String getLastName(){
		return last_name.getText();
	}

	public String[] getFields() {
		char[] pswd = password.getPassword();
		String password = new String(pswd);
		String[] fields = {username.getText(),password, e_mail.getText(),last_name.getText(),first_name.getText()};
		return fields;
	}

}
