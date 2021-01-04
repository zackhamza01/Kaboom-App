package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

public class Song implements Writable {
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
        return this.title + " by " + this.artist;
    }

    // EFFECTS: Returns Song as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("artist", artist);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Song song = (Song) o;
        return Objects.equals(title, song.title)
                && Objects.equals(artist, song.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist);
    }
}
