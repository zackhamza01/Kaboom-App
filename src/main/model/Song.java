package model;

public class Song {
    private String title;   //the song title
    private String artist;  //the song artist's name

    /*
     * REQUIRES: songName and artistName have a non-zero length
     * EFFECTS: title is set to songName and artist is set to artistName
     */

    public Song(String songName, String artistName) {
        this.title = songName;
        this.artist = artistName;
    }

    public String getTitle() {
        return this.title;
    }

    public String getArtist() {
        return this.artist;
    }

    /*
     * EFFECTS: returns a string representation of song
     */
    public String description() {
        return this.title + "by " + this.artist;
    }
}
