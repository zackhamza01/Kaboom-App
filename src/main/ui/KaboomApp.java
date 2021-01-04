package ui;

import exception.InvalidNameException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

//Kaboom Application
public class KaboomApp {
    private static final String JSONLIBRARY_STORE = "./data/library.json";
    private static final String JSONQUEUE_STORE = "./data/queue.json";
    private Library lib;
    private Queue queue;
    private Song song1;
    private Song song2;
    private Song song3;
    private Scanner input;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Kaboom application
    public KaboomApp() throws FileNotFoundException {
        runKaboom();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runKaboom() {
        System.out.println("Welcome to the Kaboom App! Hope you enjoy :)");
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            appStatus();
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }


        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user

    private void displayMenu() {
        System.out.println("\nSelect from the following: ");
        System.out.println("\tlibrary -> Access your library");
        System.out.println("\tqueue -> Access your queue");
        System.out.println("\tsave -> save music database to file");
        System.out.println("\tload -> load music database to file");
        System.out.println("\tquit -> quit the application");
    }

    private void displayLibraryMenu() {
        System.out.println("\tcreate -> Create a playlist in your library");
        System.out.println("\trename -> Rename a playlist in your library");
        System.out.println("\taddsong -> Add songs to a playlist in your library");
        System.out.println("\tview -> View the songs in a playlist");
        System.out.println("\tdeleteplaylist -> Delete a playlist from library");
        System.out.println("\tdeletesong -> Delete a song from playlist in library");
    }

    private void displayQueueMenu() {
        System.out.println("\taddplaylist -> Add a playlist from your library to your queue");
        System.out.println("\taddshuffledplaylist -> Add a shuffled playlist from your library to your queue");
        System.out.println("\taddsong -> Add a song to your queue");
        System.out.println("\tplay -> Play first song from queue");
        System.out.println("\tdeletesong -> Delete a song from queue");
    }


    private void processCommand(String command)  {
        if (command.equals("library")) {
            displayLibraryMenu();
            command = input.nextLine();
            command.toLowerCase();
            processLibraryCommand(command);
        } else if (command.equals("queue")) {
            displayQueueMenu();
            command = input.nextLine();
            command.toLowerCase();
            processQueueCommand(command);
        } else if (command.equals("save")) {
            saveMusic();
        } else if (command.equals("load")) {
            loadMusic();
        } else {
            System.out.println("Selection not valid. Try again.");
        }

    }

    private void processLibraryCommand(String command) {
        if (command.equals("create")) {
            createPlaylist();
        } else if (command.equals("rename")) {
            renamePlaylist();
        } else if (command.equals("addsong")) {
            addSongToPlaylist();
        } else if (command.equals("view")) {
            songInPlaylist();
        } else if (command.equals("deleteplaylist")) {
            deleteLibraryPlaylist();
        } else if (command.equals("deletesong")) {
            deleteSongLibrary();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void processQueueCommand(String command) {
        if (command.equals("addplaylist")) {
            addPlaylistToQueue();
        } else if (command.equals("addshuffledplaylist")) {
            addShuffledPlaylistToQueue();
        } else if (command.equals("addsong")) {
            addSong(queue);
        } else if (command.equals("play")) {
            play();
        } else if (command.equals("deletesong")) {
            deleteSongQueue();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes Library, Queue, and sample Songs
    private void init() {
        lib = new Library();
        queue = new Queue();
        song1 = new Song("AfterHours", "The Weeknd");
        song2 = new Song("Africa", "Toto");
        song3 = new Song("Lose Yourself", "Eminem");
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSONLIBRARY_STORE, JSONQUEUE_STORE);
        jsonReader = new JsonReader(JSONLIBRARY_STORE, JSONQUEUE_STORE);
    }

    // MODIFIES: this
    // EFFECTS: creates a playlist in Library

    private void createPlaylist() {
        System.out.println("Enter the name of this playlist: ");
        String name = input.nextLine();

        while (name.length() == 0) {
            System.out.println("Name cannot be empty! Try again: ");
            name = input.nextLine();
        }
        System.out.println("Playlist successfully added to your library!");
    }

    // MODIFIES: this
    // EFFECTS: renames a playlist

    private void renamePlaylist() {
        Scanner input = new Scanner(System.in);
        System.out.println("Pick a playlist from your library: ");
        for (int i = 0; i < lib.getLibrary().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
        }
        int plselection = input.nextInt() - 1;
        System.out.println("Rename " + selectPlaylist(plselection).getPlaylistName() + " to: ");
        input = new Scanner(System.in);
        String name2 = input.nextLine();
        boolean runAgain = true;
        while (runAgain) {
            try {
                lib.getLibrary().get(plselection).rename(name2);
                runAgain = false;
            } catch (InvalidNameException e) {
                System.out.println("Name cannot be empty. Try another name!");
                name2 = input.nextLine();
            }
        }

        System.out.println("Successfully renamed the playlist!");
    }

    // MODIFIES: this
    // EFFECTS: Adds a song to a playlist of the user's choice
    private void addSongToPlaylist() {
        if (lib.getLibrary().isEmpty()) {
            System.out.println("There are no playlists in your library...");
        } else if (lib.getLibrary().size() == 1) {
            addSong(lib.getLibrary().get(0));
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
        if (index >= 0) {
            return lib.getLibrary().get(index);
        } else {
            return null;
        }

    }

    private Song selectSongInPlaylist(int index, Playlist pl) {
        if (index >= 0) {
            return pl.getPlaylist().get(index);
        } else {
            return null;
        }
    }

    private Song selectSongInQueue(int index) {
        if (index >= 0) {
            return queue.getQueue().get(index);
        } else {
            return null;
        }
    }

    // REQUIRES: User to enter a valid selection
    // MODIFIES: this
    // EFFECTS: Adds a song either to a playlist or the queue,
    //            depending on the parameter

    private void addSong(Playlist p) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nSelect from these songs to add: ");
        System.out.println("\t1 for -> " + song1.description());
        System.out.println("\t2 for -> " + song2.description());
        System.out.println("\t3 for -> " + song3.description());
        System.out.println("\tCustom -> A song of your choice");
        String songselection = input.nextLine();
        p.addSong(songSelection(songselection));
        System.out.println("Song has been added to " + p.getPlaylistName() + "!");
    }

    private void addSong(Queue q) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nSelect from these songs to add: ");
        System.out.println("\t1 for -> " + song1.description());
        System.out.println("\t2 for -> " + song2.description());
        System.out.println("\t3 for -> " + song3.description());
        System.out.println("\tCustom -> A song of your choice");
        String songselection = input.nextLine();
        q.addSong(songSelection(songselection));
        System.out.println("Song has been added to queue!");
    }

    // EFFECTS: Helper method to process song selection based off user input

    private Song songSelection(String command) {
        if (command.equals("1")) {
            return song1;
        } else if (command.equals("2")) {
            return song2;
        } else if (command.equals("3")) {
            return song3;
        } else if (command.toLowerCase().equals("custom")) {
            return custom();
        } else {
            System.out.println("No valid selection...");
            return null;
        }
    }

    private Song custom() {
        input = new Scanner(System.in);
        System.out.println("Name of the song: ");
        String name = input.nextLine();
        while (name.length() == 0) {
            System.out.println("Song name cannot be empty! Try again: ");
            name = input.nextLine();
        }
        System.out.println("Artist name: ");
        String artist = input.nextLine();
        while (artist.length() == 0) {
            System.out.println("Artist name cannot be empty! Try again: ");
            artist = input.nextLine();
        }
        return new Song(name, artist);
    }

    // REQUIRES: User to enter an appropriate number for playlist and at least one playlist in library
    // MODIFIES: this
    // EFFECTS: Copies every song from a playlist and adds it to the queue

    private void addPlaylistToQueue() {
        int plselection = playlistSelection();
        queue.addPlaylistToQueue(selectPlaylist(plselection));
        System.out.println(selectPlaylist(plselection).getPlaylistName() + " has been added to queue");
    }
    // REQUIRES: User to enter an appropriate number for playlist and at least one playlist in library
    // MODIFIES: this
    // EFFECTS: Same as the previous method, but the songs in the playlist are now shufflled

    private void addShuffledPlaylistToQueue() {
        int plselection = playlistSelection();
        queue.addShuffledPlaylist(selectPlaylist(plselection));
        String playlistname = selectPlaylist(plselection).getPlaylistName();
        System.out.println("Shuffled version of " + playlistname + " has been added to the queue");
    }

    // EFFECTS: Prints out the status on the number of playlists in library
    //          and number of songs in the queue. Also prints out
    //          the playlist names in library and song names in queue

    private void appStatus() {
        if (lib.getLibrary().isEmpty()) {
            System.out.println("\nThere are no playlists in library.");
        } else {
            System.out.println("\nThere is " + lib.getLibrary().size() + " playlist(s) in your library.");
            System.out.println(lib.showPlaylists());
        }
        if (queue.getQueue().isEmpty()) {
            System.out.println("The queue is empty.");
        } else {
            System.out.println(queue.viewQueueSize());
            System.out.println(queue.viewQueue());
        }
    }

    // EFFECTS: Gives a representation of the songs in a playlist

    private void songInPlaylist() {
        int plselection = playlistSelection();
        System.out.println(selectPlaylist(plselection).viewPlaylist());
    }

    // MODIFIES: this
    // EFFECTS: Removes the first song in the queue and updates the queue

    private void play() {
        if (queue.getQueue().size() == 0) {
            System.out.println("There are no songs to play in queue.");
        } else {
            String firstsong = queue.getQueue().get(0).description();
            System.out.println("Playing " + firstsong + "...");
            queue.play();
            System.out.println("Done playing " + firstsong + "!");
        }
    }

    private void deleteLibraryPlaylist() {
        int num = playlistSelection();
        String plname = selectPlaylist(num).getPlaylistName();
        lib.deletePlaylist(selectPlaylist(num));
        System.out.println(plname + " has been successfully deleted from your library!");
    }

    private void deleteSongLibrary() {
        Scanner input = new Scanner(System.in);
        int num = playlistSelection();
        Playlist temp = selectPlaylist(num);
        System.out.println("Select a song from " + temp.getPlaylistName() + ":");
        for (int i = 0; i < temp.getPlaylist().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + temp.getPlaylist().get(i).description());
        }
        int num2 = input.nextInt() - 1;
        String songdesc = selectSongInPlaylist(num2, temp).description();
        temp.removeSong(selectSongInPlaylist(num2, temp));
        System.out.println(songdesc + " has been successfully deleted from " + temp.getPlaylistName() + "!");
    }

    private void deleteSongQueue() {
        Scanner input = new Scanner(System.in);
        System.out.println("Select a song from queue:");
        for (int i = 0; i < queue.getQueue().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + queue.getQueue().get(i).description());
        }
        int index = input.nextInt() - 1;
        String songdesc = selectSongInQueue(index).description();
        queue.removeSong(selectSongInQueue(index));
        System.out.println(songdesc + " has been successfully removed from queue!");
    }

    private int playlistSelection() {
        Scanner input = new Scanner(System.in);
        if (lib.getLibrary().isEmpty()) {
            System.out.println("Library has no playlists! Try creating one.");
            return -1;
        } else {
            System.out.println("Select a playlist from your library: ");
            for (int i = 0; i < lib.getLibrary().size(); i++) {
                System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
            }
            int num = input.nextInt() - 1;
            return num;
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the music to library and queue file
    private void saveMusic() {
        try {
            jsonWriter.open();
            jsonWriter.write(lib);
            System.out.println("Saved library to " + JSONLIBRARY_STORE);
            jsonWriter.write(queue);
            System.out.println("Saved queue to " + JSONQUEUE_STORE);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads music from library and queue file

    private void loadMusic() {
        try {
            lib = jsonReader.readLibrary();
            System.out.println("Loaded library from " + JSONLIBRARY_STORE);
            queue = jsonReader.readQueue();
            System.out.println("Loaded queue from " + JSONQUEUE_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write file");
        }
    }

}
