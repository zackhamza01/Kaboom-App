package model;

import java.util.ArrayList;

public class Library {
    private ArrayList<Playlist> library;

    /*
     * EFFECTS: Initializes a library to a default ArrayList of type Playlist
     */

    public Library() {
        this.library = new ArrayList<Playlist>();
    }

    public ArrayList<Playlist> getLibrary() {
        return this.library;
    }

    /*
     * REQUIRES: A Playlist object as a parameter
     * MODIFIES: this
     * EFFECTS: This methods adds the playlist to the library
     */

    public void addPlaylistToLibrary(Playlist playlist) {
        this.library.add(playlist);
    }

    /*
     * REQUIRES: A Playlist object
     * MODIFIES: this
     * EFFECTS: This methods first checks if the playlist exists in the library.
     *          If it does, the method removes the playlist from the library.
     */
/*
    public void deletePlaylist(Playlist playlist) {
        if (this.library.contains(playlist)) {
            library.remove(playlist);
        }
    }

    /*
     * EFFECTS: All playlists in the library will be returned in string representation
     */

    public String showPlaylists() {
        String desc = "Playlist(s) in Library: ";
        for (Playlist p: this.library) {
            desc += p.getPlaylistName() + ", ";
        }
        desc = desc.substring(0, desc.length() - 2);
        return desc;
    }


}
