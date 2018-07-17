import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

/*	The SongList class provides a structure for a linkedList of type Song.
* 	This is where the playlist of the music player is held.
*/

public class SongList{
	private LinkedList<Song> songList;

	public SongList(){
		songList = new LinkedList<Song>();
	}
	//Adds new songs to the playlist
	public void addSong(Song newSong){
		songList.add(newSong);
	}
	//Accessor Methods

	public int size(){
		return songList.size();
	}

	public LinkedList<Song> getListRef(){
		return songList;
	}

	public Song get(int i){
		return songList.get(i);
	}
	public String getSongTitle(int i){
		return songList.get(i).getSongTitle();
	}

	public String getAlbArt(int i){
		return songList.get(i).getAlbCovArt();
	}

}