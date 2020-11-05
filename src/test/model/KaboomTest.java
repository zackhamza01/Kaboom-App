package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KaboomTest {
    Song song1;
    Song song2;
    Song song3;

    @BeforeEach
    void runBefore() {
        song1 = new Song("Over", "Drake");
        song2 = new Song("Lose Yourself", "Eminem");
        song3 = new Song("Look Back at It", "A Boogie");
    }

    @Test
    void testSongClass() {
        assertEquals("Over", song1.getTitle());
        assertEquals("Drake", song1.getArtist());
        assertEquals("Over by Drake", song1.description());
    }

    @Test
    void testPlaylistNameAndSize() {
        Playlist p = new Playlist("Rap");
        assertEquals("Rap", p.getPlaylistName());
        p.addSong(song1);
        p.addSong(song2);
        p.addSong(song3);
        assertEquals("Rap has 3 song(s)", p.viewPlaylistSize());
        //p.removeSong(song3);
        //assertEquals("Rap has 2 song(s)", p.viewPlaylistSize());
        //p.addSong("Myron", "Lil Uzi");
        //assertEquals("Rap has 3 song(s)", p.viewPlaylistSize());

    }

    @Test
    void testRenamePlaylist() {
        Playlist p = new Playlist("Memories");
        assertEquals("Memories", p.getPlaylistName());
        p.rename("Nostalgia");
        assertEquals("Nostalgia", p.getPlaylistName());
    }

    @Test
    void testPlaylistViewSongs() {
        Playlist p = new Playlist("Hip-Hop");
        p.addSong(song1);
        p.addSong(song2);
        p.addSong(song3);
        assertEquals("Song(s) in Hip-Hop: Over by Drake, Lose Yourself by Eminem, Look Back at It by A Boogie", p.viewPlaylist());
    }

    @Test
    void testAddPlaylistToQueue() {
        Queue q = new Queue();
        Playlist p = new Playlist("Oldies");
        p.addSong(song1);
        p.addSong(song2);
        p.addSong(song3);
        q.addPlaylistToQueue(p);
        assertEquals("There is 3 song(s) in Queue", q.viewQueueSize());
        assertEquals("Song(s) in Queue: Over by Drake, Lose Yourself by Eminem, Look Back at It by A Boogie", q.viewQueue());
    }

    @Test
    void testAddShuffledPlaylistToQueue() {
        Queue q = new Queue();
        Playlist p = new Playlist("Country");
        p.addSong(song1);
        p.addSong(song2);
        p.addSong(song3);
        q.addShuffledPlaylist(p);
        assertTrue(q.viewQueue().contains("Song(s) in Queue: "));
    }

    @Test
    void testAddPlaylistAndSongToQueue() {
        Queue q = new Queue();
        Playlist p = new Playlist("Arabic");
        p.addSong(song1);
        p.addSong(song2);
        q.addPlaylistToQueue(p);
        q.addSong("Dreaming", "Smallpools");
        assertEquals("Song(s) in Queue: Over by Drake, Lose Yourself by Eminem, Dreaming by Smallpools", q.viewQueue());
    }
/*
    @Test
    void testRemoveSongFromQueue() {
        Queue q = new Queue();
        q.addSong(song1);
        q.addSong(song2);
        q.addSong(song3);
        assertEquals("Songs in Queue: Over by Drake, Lose Yourself by Eminem, Look Back at It by A Boogie", q.viewQueue());
        q.removeSong(song3);
        assertEquals("Songs in Queue: Over by Drake, Lose Yourself by Eminem", q.viewQueue());
    }
*/
    @Test
    void testQueuePlay() {
        Queue q = new Queue();
        q.addSong(song1);
        q.addSong(song2);
        q.addSong(song3);
        assertEquals("There is 3 song(s) in Queue", q.viewQueueSize());
        q.play();
        assertEquals("There is 2 song(s) in Queue", q.viewQueueSize());
    }

    @Test
    void testAddPlaylistToLibrary() {
        Library lib = new Library();
        Playlist p = new Playlist("Rap");
        Playlist p2 = new Playlist("French Songs");
        Playlist p3 = new Playlist("Oldies");
        lib.addPlaylistToLibrary(p);
        lib.addPlaylistToLibrary(p2);
        lib.addPlaylistToLibrary(p3);
        p.addSong(song1);
        p.addSong(song2);
        p.addSong(song3);
        p2.addSong("Angela", "Hatik");
        p2.addSong("C'est la vie", "Khaled");
        p3.addSong("What is Love?", "Haddaway");
        p3.addSong("Grenade", "Bruno Mars");
        assertEquals("Playlist(s) in Library: Rap, French Songs, Oldies", lib.showPlaylists());
        assertEquals("Song(s) in French Songs: Angela by Hatik, C'est la vie by Khaled", lib.getLibrary().get(1).viewPlaylist());
    }
/*
    @Test
    void testDeletePlaylistFromLibrary() {
        Library lib = new Library();
        Playlist p = new Playlist("Rap");
        Playlist p2 = new Playlist("French Songs");
        Playlist p3 = new Playlist("Oldies");
        lib.addPlaylistToLibrary(p);
        lib.addPlaylistToLibrary(p2);
        lib.addPlaylistToLibrary(p3);
        assertEquals("Playlist(s) in Library: Rap, French Songs, Oldies", lib.showPlaylists());
        lib.deletePlaylist(p3);
        assertEquals("Playlist(s) in Library: Rap, French Songs", lib.showPlaylists());
    }
*/
    @Test
    void testShuffledPlaylist() {
        Library lib = new Library();
        Queue queue = new Queue();
        Playlist playlist = new Playlist("p1");
        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.addSong(song3);
        playlist.addSong("Delilah", "Some Guy");
        playlist.addSong("All Star", "Big Mouth");
        lib.addPlaylistToLibrary(playlist);
        queue.addShuffledPlaylist(playlist);
        assertNotEquals(playlist.viewPlaylist(), queue.viewQueue());

    }


}