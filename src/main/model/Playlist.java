package model;

import exception.InvalidNameException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

public class Playlist implements SongList, Writable {
    private String name;    //The name of the playlist
    private ArrayList<Song> playlist;    //The playlist will contain a list of songs

    /*
     * REQUIRES: a playlist name that has non-zero length
     * EFFECTS: name is set to plname and playlist is initialized to a default Arraylist of type Song
     */
    public Playlist(String plname) {
        this.name = plname;
        this.playlist = new ArrayList<Song>();
    }

    public String getPlaylistName() {
        return this.name;
    }

    public ArrayList<Song> getPlaylist() {
        return this.playlist;
    }

    public void rename(String name) throws InvalidNameException {
        if (name.length() == 0) {
            throw new InvalidNameException();
        }
        this.name = name;
    }

    /*
     * REQUIRES: Either a Song object or both a song name and artist with non-zero lengths
     * MODIFIIES: this
     * EFFECTS: If given a Song object, it adds the object to the playlist. If given a song name
     *          and artist name, the method initializes a Song object with the given parameters,
     *          and adds it to the playlist.
     */
    @Override
    public void addSong(Song song) {
        this.playlist.add(song);
    }

    @Override
    public void addSong(String name, String artist) {
        Song song = new Song(name, artist);
        this.playlist.add(song);
    }

    /*
     * REQUIRES: The parameter to be of type Song object
     * MODIFIES: this
     * EFFECTS: The method checks if the Song object is in the playlist,
     *          then removes it if found.
     */
    @Override
    public void removeSong(Song song) {
        if (this.playlist.contains(song))  {
            this.playlist.remove(song);
        }
    }


    /*
     * EFFECTS: Number of songs in playlist will be returned
     */

    public String viewPlaylistSize() {
        return this.name + " has " + this.playlist.size() + " song(s)";
    }

    /*
     * EFFECTS: All the songs in playlist will be returned in string representation
     */
    public String viewPlaylist() {
        String desc = "Song(s) in " + this.name + ": ";
        for (Song song: this.playlist) {
            desc += song.description() + ", ";
        }
        desc = desc.substring(0, desc.length() - 2);
        return desc;
    }

    // EFFECTS: Returns Playlist as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("playlist", songsToPlaylistJson());
        return json;
    }

    // EFFECTS: Returns songs in Playlist as a JSON Array
    private JSONArray songsToPlaylistJson() {
        JSONArray jsonArray = new JSONArray();

        for (Song song: playlist) {
            jsonArray.put(song.toJson());
        }

        return jsonArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Playlist playlist1 = (Playlist) o;
        return name.equals(playlist1.name)
                && playlist.equals(playlist1.playlist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, playlist);
    }
}

