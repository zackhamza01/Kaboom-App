package model;

import java.util.*;

public class Queue implements SongList {
    private ArrayList<Song> queue;

    /*
     * EFFECTS: Initializes a queue to a default ArrayList of type Song
     */

    public Queue() {
        this.queue = new ArrayList<Song>();
    }

    public ArrayList<Song> getQueue() {
        return this.queue;
    }

    /*
     * REQUIRES: An existing Playlist object
     * MODIFIES: this
     * EFFECTS: The method takes every song in the playlist and adds it to the queue
     */

    public void addPlaylist(Playlist playlist) {
        for (Song song: playlist.getPlaylist()) {
            this.queue.add(song);
        }
    }

    /*
     * REQUIRES: An existing Playlist object
     * MODIFIES: this
     * EFFECTS: The method copies the content of the playlist, then shuffles the songs in it.
     *          That shuffled playlist is then added to the queue.
     */

    public void addShuffledPlaylist(Playlist playlist) {
        ArrayList<Song> shuffledpl = playlist.getPlaylist();
        Collections.shuffle(shuffledpl);
        for (Song song: shuffledpl) {
            this.queue.add(song);
        }
    }

    /*
     * REQUIRES: Either a Song object or both a song name and artist with non-zero lengths
     * MODIFIIES: this
     * EFFECTS: If given a Song object, it adds the object to the queue. If given a song name
     *          and artist name, the method initializes a Song object with the given parameters,
     *          and adds it to the queue.
     */

    @Override
    public void addSong(Song song) {
        this.queue.add(song);
    }

    @Override
    public void addSong(String title, String artist) {
        Song song = new Song(title, artist);
        this.queue.add(song);
    }

    /*
     * REQUIRES: Either a Song object or a song name
     * MODIFIES: this
     * EFFECTS: If the parameter is an object, the method checks if the song is in the queue,
     *          then removes it if found. If the parameter is a song name, it traverses the queue
     *          and checks if the given song name is equal to a song name in the queue. If a song
     *          in the queue has the same song name, then it removes it.
     */

    @Override
    public void removeSong(Song song) {
        if (this.queue.contains(song)) {
            this.queue.remove(song);
        }
    }

    @Override
    public void removeSong(String name) {
        for (Song s: this.queue) {
            if (s.getTitle().equals(name)) {
                this.queue.remove(s);
            }
        }
    }

    /*
     * REQUIRES: queue.size() > 0, in other words there should be at least one song remaining in queue
     * MODIFIES: this
     * EFFECTS: Removes the first song in the queue
     */

    public void play() {
        this.queue.remove(0);
    }

    /*
     * EFFECTS: Number of songs in queue will be returned
     */

    public String viewPlaylistSize() {
        return "There are " + this.queue.size() + " songs in Queue";
    }

    /*
     * EFFECTS: All the songs in queue will be returned in string representation
     */

    public String viewQueue() {
        String desc = "Songs in Queue: ";
        for (Song s: this.queue) {
            desc += s.description() + ", ";
        }
        desc = desc.substring(0,desc.length() - 2);
        return desc;
    }


}
