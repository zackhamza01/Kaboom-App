package ui;

import model.*;
import java.util.Scanner;

public class KaboomApp {
    private Library lib;
    private Queue queue;
    private Song song1;
    private Song song2;
    private Song song3;
    private Song song4;
    private Song song5;
    private Scanner input;

    public KaboomApp() {
        runKaboom();
    }

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
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void init() {
        lib = new Library();
        queue = new Queue();
        song1 = new Song("AfterHours", "The Weeknd");
        song2 = new Song("Perfect", "Ed Sheeran");
        song3 = new Song("Lose Yourself", "Eminem");
        song4 = new Song("Africa", "Toto");
        song5 = new Song("Forever", "Chris Brown");
        input = new Scanner(System.in);
    }

    private void displayMenu() {
        System.out.println("\nSelect from the following: ");
        System.out.println("\tcreateplaylist -> Create a playlist in your library");
        System.out.println("\trenameplaylist -> Rename a playlist in your library");
        System.out.println("\taddsongtoplaylist -> Add songs to a playlist in your library");
        System.out.println("\taddplaylisttoqueue -> Add a playlist from your library to your queue");
        System.out.println("\taddshuffledplaylisttoqueue -> Add a shuffled playlist from your library to your queue");
        System.out.println("\taddsongtoqueue -> Add a song to your queue");
        System.out.println("\tviewplaylist -> View the songs in a playlist");
        System.out.println("\tquit -> quit the application");
    }

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

    private Playlist selectPlaylist(int index) {
        return lib.getLibrary().get(index);
    }

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

    private void addShuffledPlaylistToQueue() {
        Scanner input2 = new Scanner(System.in);
        System.out.println("Pick a playlist from your library to add to queue: ");
        for (int i = 0; i < lib.getLibrary().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
        }
        int plselection = input.nextInt() - 1;
        queue.addShuffledPlaylist(selectPlaylist(plselection));
        String playlistname = selectPlaylist(plselection).getPlaylistName();
        System.out.println("Shuffled version of " + playlistname + " has been added to the queue");
    }

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

    private void songInPlaylist() {
        Scanner input2 = new Scanner(System.in);
        System.out.println("Pick a playlist from your library: ");
        for (int i = 0; i < lib.getLibrary().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + lib.getLibrary().get(i).getPlaylistName());
        }
        int plselection = input2.nextInt() - 1;
        System.out.println(selectPlaylist(plselection).viewPlaylist());
    }




}
