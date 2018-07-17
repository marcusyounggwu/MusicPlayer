import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.sound.sampled.*;
import java.net.*;
import java.io.IOException;


public class MusicPlayer extends JFrame{
	//Initialize Text Areas.
	JTextArea artName;
	JTextArea albName;
	JTextArea songTitle; 
	JTextArea songFN;
	JTextArea albCovArt; 
	JTextArea songList;
	JTextArea nowPlaying;
	//Initialize Buttons.
	JButton quitb;
	JButton submitb;
	JButton playNextSong;
	JButton playRandomSong;
	//Initialize JPanels
	JPanel nextUp;
	JPanel addSongs;
	JPanel centerConsole;
	JPanel playButtons;

	SongList playlist;

	Clip clip;

	private BufferedImage image;
	private String username;
	private int listPH;
	private int songPlace;

	//When the MusicPlayer constructor is called, the gui gets initialized
	public MusicPlayer(int width, int height, String username){
		//initializes the current song playing (clip)
		try{
			clip = AudioSystem.getClip();
		}
		catch (LineUnavailableException e) {
         				e.printStackTrace();
      	}
      	//playlist is the linked list of songs to be played
		SongList playlist = new SongList();
		//listPH and songPlace are index holders.
		listPH = 0;
		songPlace = 0;
		//Parameters of Musicplayer and basic JFrame aspects get initialized, 
		this.username = username;
		this.setTitle(username);
		this.setResizable( true );
    	this.setSize( width, height );
    	this.setLayout(new BorderLayout());
    	//Initializes Text Areas and Buttons
    	artName = new JTextArea("Artist Name: ", 1, 1);
    	albName = new JTextArea("Album Name: ",1, 1);
    	songTitle = new JTextArea("Song Title: ",1,1);
    	songFN = new JTextArea("Song Filename: ",1,1);
    	albCovArt = new JTextArea("Album Art Filename: ",1,1);
    	songList = new JTextArea("List of Songs: "+ "\n", 1, 20);
    	songList.setEditable(false);
    	nowPlaying = new JTextArea("Other user is playing: ",1,1);
    	nowPlaying.setEditable(false);
    	artName.setPreferredSize(new Dimension(20,20));
    	albName.setPreferredSize(new Dimension(20,20));
    	songFN.setPreferredSize(new Dimension(20,20));
    	albCovArt.setPreferredSize(new Dimension(20,20));
    	songList.setPreferredSize(new Dimension(20,20));
    	nowPlaying.setPreferredSize(new Dimension(20,20));
    	playNextSong = new JButton("Next Song");
    	playRandomSong = new JButton("Random Song");
    	submitb = new JButton("Submit Song");
    	quitb = new JButton("Quit");
    	//Initializes JPanels
    	addSongs = new JPanel();
    	playButtons = new JPanel();
    	nextUp = new JPanel();
    	centerConsole = new JPanel();
    	//Sets Layout of GUI
    	centerConsole.setLayout(new BoxLayout(centerConsole, BoxLayout.Y_AXIS));
    	nextUp.setLayout(new BoxLayout(nextUp, BoxLayout.Y_AXIS));
    	addSongs.setLayout(new BoxLayout(addSongs, BoxLayout.Y_AXIS));
    	playButtons.setLayout(new FlowLayout());

    	//Adds the song submission toolbar on the left to a JPanel
    	addSongs.add(artName);
    	addSongs.add(albName);
    	addSongs.add(songTitle);
    	addSongs.add(songFN);
    	addSongs.add(albCovArt);
    	addSongs.add(submitb);
    	//Adds play buttons to a Jpanel
    	playButtons.add(playNextSong);
    	playButtons.add(playRandomSong);
    	//Adds JPanels to the JFrame
    	this.add(playButtons,BorderLayout.NORTH);
    	this.add(nextUp, BorderLayout.EAST);
    	this.add(quitb, BorderLayout.SOUTH);
    	this.add(centerConsole, BorderLayout.CENTER);
    	this.add(addSongs, BorderLayout.WEST);
    	//Quit Button ActionListener quits on click.
    	quitb.addActionListener(new ActionListener() {
        	public void actionPerformed( ActionEvent a ) {
	  				System.exit( 0 );
       			}
     		}
    	);
    	//The Submit button adds new songs to the nextUp Jpanel which is the playlist essentially
    	//After each song is added, the UI is updated.
    	submitb.addActionListener(new ActionListener() {
        	public void actionPerformed( ActionEvent a ){
        			Song newSong = new Song(artName.getText(),albName.getText(),songTitle.getText(),songFN.getText(),albCovArt.getText());
        			playlist.addSong(newSong);
        			for(int i = listPH; i < playlist.size(); i++){
        				String[] splitter = playlist.getSongTitle(i).split(" ");
        				for(int x = 2; x < splitter.length; x++){
        					songList.append(splitter[x]);
        				}
        				if(i == playlist.size()-1){
        					listPH = playlist.size();
        				}
        				
        			}
        			nextUp.add(songList);
        			nextUp.updateUI();
       			}
     		}
    	);
    	//The play next song button plays audio, and sets the JTextAreas for the current song playing
 		//This button also sends the current song playing data to the connected music player.
    	playNextSong.addActionListener(new ActionListener() {
        	public void actionPerformed( ActionEvent a ) {
        			try{
        			//Clip is stopped to stop current song playing when playing the next song
        			clip.stop();
        			clip.close();
        			//The next three lines sets up the next song to be played
        			String[] splitter1 = playlist.get(songPlace).getSongFilename().split(": ");
        			File soundFile = new File(splitter1[1]);
          			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile); 
        			//Clears current song playing window to make room for next song
        			centerConsole.removeAll();
        			//Gets the Album Art of the next song to be played
        			String[] splitter = playlist.getAlbArt(songPlace).split(": ");
        			BufferedImage myPicture = ImageIO.read(new File(splitter[1]));
        			//Displays the 
        			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        			JTextArea artName = new JTextArea("NOW PLAYING: " + "\n" + playlist.get(songPlace).getArtName(),1,1);
        			JTextArea songTit = new JTextArea(playlist.get(songPlace).getSongTitle(),1,1);
					JTextArea albN = new JTextArea(playlist.get(songPlace).getAlbName(),1,1);
        			centerConsole.add(artName);
        			centerConsole.add(songTit);
        			centerConsole.add(albN);
        			centerConsole.add(picLabel);
        			clip.open(audioIn);
         			clip.start();
         			DatagramSocket ds = null;
					try{
						// create a new socket to send data packets
						ds = new DatagramSocket();
						// prepare the message
						String songTitle = playlist.get(songPlace).getSongTitle();
						String usernameSend = username;
						String artN = playlist.get(songPlace).getArtName();
						String albNa = playlist.get(songPlace).getAlbName();
						// readLine() is just like the read() of InputStream class
						// check the documentation of the class BufferedReader to find more information
						// prepare the buffer to send
						byte[] songTD = songTitle.getBytes();
						byte[] userTD = usernameSend.getBytes();
						byte[] artTD = artN.getBytes();
						byte[] albTD = albNa.getBytes();
						// create the data packet based on the buffer
						DatagramPacket DPSongT = new DatagramPacket(songTD,songTD.length,InetAddress.getLocalHost(),10000);
						DatagramPacket DPuser = new DatagramPacket(userTD,userTD.length,InetAddress.getLocalHost(),10000);
						DatagramPacket DPart = new DatagramPacket(artTD,artTD.length,InetAddress.getLocalHost(),10000);
						DatagramPacket DPalb = new DatagramPacket(albTD,albTD.length,InetAddress.getLocalHost(),10000);
						// send the packet
						ds.send(DPSongT);
						ds.send(DPuser);
						ds.send(DPart);
						ds.send(DPalb);
					}catch(IOException e){}
					finally{
						if(ds != null){
							ds.close();
						}
					}
        			centerConsole.updateUI();
        			songPlace++;


        			}
        			catch(IOException ex){
        				System.out.println (ex.toString());
        			}
        			catch (UnsupportedAudioFileException e) {
         				e.printStackTrace();
      				}
      				catch (LineUnavailableException e) {
         				e.printStackTrace();
      				}
       			}
     		}
    	);
    	//Same as next play next song except chooses a random
    	playRandomSong.addActionListener(new ActionListener() {
        	public void actionPerformed( ActionEvent a ) {
        			try{
        			clip.stop();
        			clip.close();
        			Random rand = new Random();
        			int randIndex = rand.nextInt(playlist.size());
        			System.out.println(randIndex);
        			String[] splitter1 = playlist.get(randIndex).getSongFilename().split(": ");
        			File soundFile = new File(splitter1[1]);
          			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile); 
        			centerConsole.removeAll();
        			String[] splitter = playlist.getAlbArt(randIndex).split(": ");
        			System.out.println(splitter[1]);
        			BufferedImage myPicture = ImageIO.read(new File(splitter[1]));
        			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        			JTextArea artName = new JTextArea(playlist.get(randIndex).getArtName(),1,1);
        			JTextArea songTit = new JTextArea(playlist.get(randIndex).getSongTitle(),1,1);
					JTextArea albN = new JTextArea(playlist.get(randIndex).getAlbName(),1,1);
					artName.setPreferredSize(new Dimension(20,20));
    				songTit.setPreferredSize(new Dimension(20,20));
    				albN.setPreferredSize(new Dimension(20,20));
        			centerConsole.add(artName);
        			centerConsole.add(songTit);
        			centerConsole.add(albN);
        			centerConsole.add(picLabel);
        			clip.open(audioIn);
         			clip.start();
         			DatagramSocket ds = null;
					try{
						// create a new socket to send data packets
						ds = new DatagramSocket();
						// prepare the message
						String songTitle = playlist.get(songPlace).getSongTitle();
						String usernameSend = username;
						String artN = playlist.get(songPlace).getArtName();
						String albNa = playlist.get(songPlace).getAlbName();
						// readLine() is just like the read() of InputStream class
						// check the documentation of the class BufferedReader to find more information
						// prepare the buffer to send
						byte[] songTD = songTitle.getBytes();
						byte[] userTD = usernameSend.getBytes();
						byte[] artTD = artN.getBytes();
						byte[] albTD = albNa.getBytes();
						// create the data packet based on the buffer
						DatagramPacket DPSongT = new DatagramPacket(songTD,songTD.length,InetAddress.getLocalHost(),10000);
						DatagramPacket DPuser = new DatagramPacket(userTD,userTD.length,InetAddress.getLocalHost(),10000);
						DatagramPacket DPart = new DatagramPacket(artTD,artTD.length,InetAddress.getLocalHost(),10000);
						DatagramPacket DPalb = new DatagramPacket(albTD,albTD.length,InetAddress.getLocalHost(),10000);
						// send the packet
						ds.send(DPSongT);
						ds.send(DPuser);
						ds.send(DPart);
						ds.send(DPalb);
					}catch(IOException e){}
					finally{
						if(ds != null){
							ds.close();
						}
					}
        			centerConsole.updateUI();

        			}
        			catch(IOException ex){
        				System.out.println (ex.toString());
        			}
        			catch (UnsupportedAudioFileException e) {
         				e.printStackTrace();
      				}
      				catch (LineUnavailableException e) {
         				e.printStackTrace();
      				}
       			}
     		}
    	);
	}

	public String getUsername(){
		return username;
	}

}
