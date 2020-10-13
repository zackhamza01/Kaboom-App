package model;

public interface SongList {
    void addSong(Song song);

    void addSong(String name, String artist);

    void removeSong(Song song);

    void removeSong(String name);
}
