package model;

public interface SongList {
    /*
     * EFFECTS: The methods in this interface have been implemented for the Playlist and Queue class.
     *          The Playlist and Queue class both have addSong and removeSong in common.
     */
    void addSong(Song song);

    void addSong(String name, String artist);

    void removeSong(Song song);

  //  void removeSong(String name);
}
