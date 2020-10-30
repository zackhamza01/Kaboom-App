package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class Queue implements SongList, Writable {
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

    public void addPlaylistToQueue(Playlist playlist) {
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
        for (int i = 0; i < shuffledpl.size(); i++) {
            int randindex = (int)(Math.random() * (shuffledpl.size()));
            Song randvalue = shuffledpl.get(randindex);
            shuffledpl.set(randindex, shuffledpl.get(i));
            shuffledpl.set(i, randvalue);
        }
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
     * REQUIRES: The parameter to be of type Song object
     * MODIFIES: this
     * EFFECTS: The method checks if the Song object is in the queue,
     *          then removes it if found.
     */
/*
    @Override
    public void removeSong(Song song) {
        if (this.queue.contains(song)) {
            this.queue.remove(song);
        }
    }
/*
    @Override
    public void removeSong(String name) {
        for (Song s: this.queue) {
            if (s.getTitle().equals(name)) {
                this.queue.remove(s);
            }
        }
    }
*/
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

    public String viewQueueSize() {
        return "There is " + this.queue.size() + " song(s) in Queue";
    }

    /*
     * EFFECTS: All the songs in queue will be returned in string representation
     */

    public String viewQueue() {
        String desc = "Song(s) in Queue: ";
        for (Song s: this.queue) {
            desc += s.description() + ", ";
        }
        desc = desc.substring(0, desc.length() - 2);
        return desc;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("queue", songsToQueueJson());
        return json;
    }

    private JSONArray songsToQueueJson() {
        JSONArray jsonArray = new JSONArray();

        for (Song song: queue) {
            jsonArray.put(song.toJson());
        }

        return jsonArray;
    }
}
