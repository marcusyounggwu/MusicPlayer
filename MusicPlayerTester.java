import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.IOException;

public class MusicPlayerTester{
	public static void main(String[] args){
		Boolean loginVisible = true;
		LoginScreen login = new LoginScreen();
		while(loginVisible){
			if(login.loginComplete()){
				loginVisible = false;
			}

			login.setVisible(loginVisible);
		}
		MusicPlayer spotify = new MusicPlayer(1000,1000,login.getUsername());
		System.out.println(spotify.getUsername());
		spotify.setVisible(true);
			DatagramSocket ds = null;
			try{
				// create the socket and bind the port number
				ds = new DatagramSocket(10000);
				while(true){
				//create the buffer
				byte[] buffer = new byte[1024];
				//create the data packets based upon the buffer
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				//receive data
				ds.receive(dp);
				//use getData(), getLength(), getPort(), getAddress() to analyze the received packet
				byte[] data = dp.getData();
				System.out.println("Network Data: "+ new String(data).trim());
				}
				
			}catch(IOException E){}
			finally{
				// close the socket
				if(ds != null){
					ds.close();
				}
				
			}
	}
}