import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class LoginScreen extends JFrame{

	JTextArea usernameInput;
	JTextArea welcomeMessage;

	JButton submitUsername; 
	JButton quitb;

	private String username; 

	public LoginScreen(){
		this.setResizable(true);
		this.setSize(400,300);
		this.setLayout(new FlowLayout());
		welcomeMessage = new JTextArea("Welcome to my MusicPlayer, please enter a username below.",1,3);
		welcomeMessage.setFont(new Font("Serif", Font.PLAIN, 15));
		welcomeMessage.setEditable(false);
		usernameInput = new JTextArea("Username: ",1,10);
		submitUsername = new JButton("Submit");
		quitb = new JButton("Quit");
		add(welcomeMessage);
		add(usernameInput);
		add(submitUsername);
		add(quitb);
		quitb.addActionListener(new ActionListener() {
        	public void actionPerformed( ActionEvent a ) {
	  				System.exit( 0 );
       			}
     		}
    	);
		submitUsername.addActionListener(new ActionListener() {
        	public void actionPerformed( ActionEvent a ) {
        			String[] splitInput = usernameInput.getText().split(" ");
	  				username = splitInput[1];
       			}
     		}
    	);
	}
	public Boolean loginComplete(){
		if(username != null && !username.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	public String getUsername(){
		return username;
	}
}
