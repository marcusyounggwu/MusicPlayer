/*
*	The Song class provides a constructor for songs to be added
*	to the music player playlist.
*/

public class Song{

		private String artName;
		private String albName;
		private String songTitle;
		private String songFileName;
		private String albCovArt;

		public Song(String artName, String albName, String songTitle, String songFileName, String albCovArt){
			this.artName = artName;
			this.albName = albName;
			this.songTitle = songTitle;
			this.songFileName = songFileName;
			this.albCovArt = albCovArt;
		}
		//Accessor Methods
		public String getArtName(){
			return artName;
		}

		public String getAlbName(){
			return albName;
		}

		public String getSongTitle(){
			return songTitle;
		}

		public String getSongFilename(){
			return songFileName;
		}

		public String getAlbCovArt(){
			return albCovArt;
		}
	}