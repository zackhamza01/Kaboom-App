package ui;

import model.Library;
import model.Queue;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class KaboomGraphicApp {
    //fields for GUI
    private static final String JSONLIBRARY_STORE = "./data/library.json";
    private static final String JSONQUEUE_STORE = "./data/queue.json";
    private static final String MUSIC_FILE = "C:/Users/ahamz/Desktop/College Stuffz/UBC/CPSC 210/project_j9v0z/data";
    private Library lib;
    private Queue queue;
    private JFrame mainframe;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //Constructor

    public KaboomGraphicApp() {
        constructFrame();
        runMainMenu();
    }

    //MODIFIES: this
    //EFFECTS: Creates the main JFrame for the GUI
    private void constructFrame() {
        mainframe = new JFrame("Kaboom Music App");
        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainframe.setSize(500,300);
        mainframe.setVisible(true);
        music();
    }

    //MODIFIES: this
    //EFFECTS: Plays the background music
    private void music() {
        InputStream in = getClass().getClassLoader().getResourceAsStream(MUSIC_FILE);
        try {
            AudioStream audio = new AudioStream(in);
            audio.close();
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            System.out.println("File not Found");
        }
    }


    //MODIFIES: this
    //EFFECTS: Runs the main menu of the GUI
    private void runMainMenu() {
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomelabel = new JLabel("Welcome to Kaboom! Select one of the following:");
        mainpanel.add(welcomelabel);
        addButtonsToMainMenu(mainpanel);
        mainframe.add(mainpanel);
        //mainframe.pack();
    }


    //EFFECTS: Helper method to add the necessary buttons to the main menu
    private void addButtonsToMainMenu(JPanel panel) {
        JButton libbutton = new JButton("Library");
        JButton queuebutton = new JButton("Queue");
        JButton savebutton = new JButton("Save");
        JButton loadbutton = new JButton("Load");
        panel.add(libbutton);
        panel.add(queuebutton);
        panel.add(savebutton);
        panel.add(loadbutton);
        mainMenuActionListeners(libbutton, queuebutton, savebutton, loadbutton);
    }

    //EFFECTS: Another helper method to access the ActionListener's to the buttons in the main menu
    private void mainMenuActionListeners(JButton libbutton, JButton queuebutton,
                                        JButton savebutton, JButton loadbutton) {
        libActionListener(libbutton);
        queueActionListener(queuebutton);
        saveActionListener(savebutton);
        loadActionListener(loadbutton);
    }

    // MODIFIES: this
    // EFFECTS: ActionListener for when Library button is pressed
    private void libActionListener(JButton libbutton) {
        libbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                libbutton.setBackground(Color.GREEN);
                JPanel libpanel = new JPanel();
                JLabel liblabel = new JLabel("Library: Select the following buttons");
                JButton plbutton = new JButton("Create playlist");
                libpanel.add(plbutton);
                mainframe.add(libpanel);
            }
        });
    }

    //  MODIFIES: this
    //  EFFECTS: ActionListener for when Queue button is pressed
    private void queueActionListener(JButton queuebutton) {
        queuebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queuebutton.setBackground(Color.GREEN);
                JPanel queuepanel = new JPanel();
                JLabel queuelabel = new JLabel("Library: Select the following buttons");
                JButton plbutton = new JButton("Add song to queue");
                queuepanel.add(plbutton);
                mainframe.add(queuepanel);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: ActionListener for when Save button is pressed
    private void saveActionListener(JButton savebutton) {
        savebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
                JLabel savelabel = new JLabel("Successfuly saved!");
                mainframe.add(savelabel);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: ActionListener for when Load button is pressed
    private void loadActionListener(JButton loadbutton) {
        loadbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
                JLabel loadlabel = new JLabel("Successfuly loaded!");
                mainframe.add(loadlabel);
            }
        });
    }

 /*   public void libMenu() {
        mainframe.removeAll();
        JPanel libpanel = new JPanel();
        libpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton plbutton = new JButton("Playlist");
        libpanel.add(plbutton);
        mainframe.add(libpanel);
    }
*/
    // MODIFIES: this
    // EFFECTS: saves the music to library and queue file
    private void save() {
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
    private void load() {
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
