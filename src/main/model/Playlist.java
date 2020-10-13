package model;

import java.util.ArrayList;

public class Playlist implements SongList {
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
     * REQUIRES: Either a Song object or a song name
     * MODIFIES: this
     * EFFECTS: If the parameter is an object, the method checks if the song is in the playlist,
     *          then removes it if found. If the parameter is a song name, it traverses the playlist
     *          and checks if the given song name is equal to a song name in the playlist. If a song
     *          in the playlist has the same song name, then it removes it.
     */
    @Override
    public void removeSong(Song song) {
        if (this.playlist.contains(song))  {
            this.playlist.remove(song);
        }
    }

    @Override
    public void removeSong(String name) {
        for (Song song: this.playlist) {
            if (song.getTitle().equals(name)) {
                this.playlist.remove(song);
            }
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
        String desc = "Songs in " + this.name + ": ";
        for (Song song: this.playlist) {
            desc += song.description() + ", ";
        }
        desc = desc.substring(0,desc.length() - 2);
        return desc;
    }
}

