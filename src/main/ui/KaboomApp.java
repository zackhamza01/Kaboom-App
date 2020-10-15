package ui;

import model.*;
import java.util.Scanner;

//Kaboom Application
public class KaboomApp {
    private Library lib;
    private Queue queue;
    private Song song1;
    private Song song2;
    private Song song3;
    private Song song4;
    private Song song5;
    private Scanner input;

    // EFFECTS: runs the Kaboom application
    public KaboomApp() {
        runKaboom();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runKaboom() {
        System.out.println("Welcome to the Kaboom App! Hope you enjoy!");
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
                appStatus();
            }


        }

        System.out.println("\nGoodbye!");
    }

    // REQUIRES: User to type in a valid response
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("createplaylist")) {
            createPlaylist();
        } else if (command.equals("renameplaylist")) {
            renamePlaylist();
        } else if (command.equals("addsongtoplaylist")) {
            addSongToPlaylist();
        } else if (command.equals("addplaylisttoqueue")) {
            addPlaylistToQueue();
        } else if (command.equals("addshuffledplaylisttoqueue")) {
            addShuffledPlaylistToQueue();
        } else if (command.equals("addsongtoqueue")) {
            addSong(queue);
        } else if (command.equals("viewplaylist")) {
            songInPlaylist();
        } else if (command.equals("play")) {
            play();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes Library, Queue, and sample Songs
    private void init() {
        lib = new Library();
        queue = new Queue();
        song1 = new Song("AfterHours", "The Weeknd");
        song2 = new Song("Perfect", "Ed Sheeran");
        song3 = new Song("Lose Yourself", "Eminem");
        song4 = new Song("Africa", "Toto");
        song5 = new Song("Beats", "Chris Brown ft. Rihanna");
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user

    private void displayMenu() {
        System.out.println("\nSelect from the following: ");
        System.out.println("\tcreateplaylist -> Create a playlist in your library");
        System.out.println("\trenameplaylist -> Rename a playlist in your library");
        System.out.println("\taddsongtoplaylist -> Add songs to a playlist in your library");
        System.out.println("\taddplaylisttoqueue -> Add a playlist from your library to your queue");
        System.out.println("\taddshuffledplaylisttoqueue -> Add a shuffled playlist from your library to your queue");
        System.out.println("\taddsongtoqueue -> Add a song to your queue");
        System.out.println("\tviewplaylist -> View the songs in a playlist");
        System.out.println("\tplay -> play first song from queue");
        System.out.println("\tquit -> quit the application");
    }

    // MODIFIES: this
    // EFFECTS: creates a playlist in Library

    private void createPlaylist() {
        System.out.println("Enter the name of this playlist: ");
        String name = input.nextLine();

        if (name.length() == 0) {
            System.out.println("Name cannot be empty...\n");
        } else {
            lib.addPlaylistToLibrary(new Playlist(name));
            System.out.println("Playlist successfully added to library!");
        }
    }

    // REQUIRES: Name of playlist to be renamed to not be empty
    // MODIFIES: this
    // EFFECTS: renames a playlist

    private void renamePlaylist() {
        Scanner input2 = new Scanner(System.in);
        System.out.println("Pick a playlist from your library: ");
        for (int i = 0; i < lib.getLibrary().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
        }
        int plselection = input.nextInt() - 1;
        System.out.println("Rename " + selectPlaylist(plselection).getPlaylistName() + " to: ");
        String name2 = input2.nextLine();
        lib.getLibrary().get(plselection).rename(name2);
    }

    // MODIFIES: this
    // EFFECTS: Adds a song to a playlist of the user's choice
    private void addSongToPlaylist() {
        if (lib.getLibrary().isEmpty()) {
            System.out.println("There are no playlists in your library...");
        } else if (lib.getLibrary().size() == 1) {
            addSong(lib.getLibrary().get(0));
            System.out.println("Song has been successfully added to " + lib.getLibrary().get(0).getPlaylistName());
        } else {
            System.out.println("Pick a playlist from your library: ");
            for (int i = 0; i < lib.getLibrary().size(); i++) {
                System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
            }
            int plselection = input.nextInt() - 1;
            addSong(selectPlaylist(plselection));
        }
    }

    // REQUIRES: Index to be a valid selection to a playlist in the given options
    // EFFECTS: Helper method to select a playlist based on user input

    private Playlist selectPlaylist(int index) {
        return lib.getLibrary().get(index);
    }

    // REQUIRES: User to enter a valid selection
    // MODIFIES: this
    // EFFECTS: Adds a song either to a playlist or the queue,
    //            depending on the parameter

    private void addSong(Playlist p) {
        Scanner input2 = new Scanner(System.in);
        System.out.println("\nSelect from these songs to add: ");
        System.out.println("\t1 for -> " + song1.description());
        System.out.println("\t2 for -> " + song2.description());
        System.out.println("\t3 for -> " + song3.description());
        System.out.println("\t4 for -> " + song4.description());
        System.out.println("\t5 for -> " + song5.description());
        System.out.println("\tcustom -> A song of your choice");
        String songselection = input2.nextLine();
        p.addSong(songSelection(songselection));
        System.out.println("Song has been successfully added to " + p.getPlaylistName());
    }

    private void addSong(Queue q) {
        Scanner input2 = new Scanner(System.in);
        System.out.println("\nSelect from these songs to add: ");
        System.out.println("\t1 for -> " + song1.description());
        System.out.println("\t2 for -> " + song2.description());
        System.out.println("\t3 for -> " + song3.description());
        System.out.println("\t4 for -> " + song4.description());
        System.out.println("\t5 for -> " + song5.description());
        System.out.println("\tcustom -> A song of your choice");
        String songselection = input2.nextLine();
        q.addSong(songSelection(songselection));
        System.out.println("Song has been added to queue");
    }

    // REQUIRES: User to put a valid selection
    // EFFECTS: Helper method to process song selection based off user input

    private Song songSelection(String command) {
        if (command.equals("1")) {
            return song1;
        } else if (command.equals("2")) {
            return song2;
        } else if (command.equals("3")) {
            return song3;
        } else if (command.equals("4")) {
            return song4;
        } else if (command.equals("5")) {
            return song5;
        } else if (command.equals("custom")) {
            Scanner console = new Scanner(System.in);
            System.out.println("Name of the song: ");
            String name = console.nextLine();
            System.out.println("Artist name: ");
            String artist = console.nextLine();
            return new Song(name, artist);
        } else {
            System.out.println("No valid selection...");
            return null;
        }
    }

    // REQUIRES: User to enter an appropriate number for playlist and at least one playlist in library
    // MODIFIES: this
    // EFFECTS: Copies every song from a playlist and adds it to the queue

    private void addPlaylistToQueue() {
        Scanner input2 = new Scanner(System.in);
        System.out.println("Pick a playlist from your library to add to queue: ");
        for (int i = 0; i < lib.getLibrary().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
        }
        int plselection = input2.nextInt() - 1;
        queue.addPlaylistToQueue(selectPlaylist(plselection));
        System.out.println(selectPlaylist(plselection).getPlaylistName() + " has been added to queue");
    }
    // REQUIRES: User to enter an appropriate number for playlist and at least one playlist in library
    // MODIFIES: this
    // EFFECTS: Same as the previous method, but the songs in the playlist are now shufflled

    private void addShuffledPlaylistToQueue() {
        Scanner input2 = new Scanner(System.in);
        System.out.println("Pick a playlist from your library to add to queue: ");
        for (int i = 0; i < lib.getLibrary().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
        }
        int plselection = input2.nextInt() - 1;
        queue.addShuffledPlaylist(selectPlaylist(plselection));
        String playlistname = selectPlaylist(plselection).getPlaylistName();
        System.out.println("Shuffled version of " + playlistname + " has been added to the queue");
    }

    // EFFECTS: Prints out the status on the number of playlists in library
    //          and number of songs in the queue. Also prints out
    //          the playlist names in library and song names in queue

    private void appStatus() {
        if (lib.getLibrary().isEmpty()) {
            System.out.println("There are no playlists in library");
        } else {
            System.out.println("There are " + lib.getLibrary().size() + " playlist(s) in your library");
            System.out.println(lib.showPlaylists());
        }
        if (queue.getQueue().isEmpty()) {
            System.out.println("The queue is empty");
        } else {
            System.out.println(queue.viewQueueSize());
            System.out.println(queue.viewQueue());
        }
    }

    // EFFECTS: Gives a representation of the songs in a playlist

    private void songInPlaylist() {
        Scanner input2 = new Scanner(System.in);
        System.out.println("Pick a playlist from your library: ");
        for (int i = 0; i < lib.getLibrary().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
        }
        int plselection = input2.nextInt() - 1;
        System.out.println(selectPlaylist(plselection).viewPlaylist());
    }

    // REQUIRES: At least one song in queue
    // MODIFIES: this
    // EFFECTS: Removes the first song in the queue and updates the queue

    private void play() {
        System.out.println("Playing first song in queue...");
        queue.play();
        System.out.println("First song has finished!");
    }

}
