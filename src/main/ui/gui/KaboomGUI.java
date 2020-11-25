package ui.gui;

import model.Library;
import model.Queue;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class KaboomGUI extends JFrame {
    //fields
    public final int width = 500;
    public final int height = 500;
    private static final String JSONLIBRARY_STORE = "./data/library.json";
    private static final String JSONQUEUE_STORE = "./data/queue.json";
    private static final String MUSIC_FILE = "C:/Users/ahamz/Desktop/College Stuffz/UBC/CPSC 210/project_j9v0z/data";
    private Library lib;
    private Queue queue;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JSplitPane splitPane;
    private JPanel welcomePanel;
    private JPanel mainPanel;

    // MODIFIES: this
    // EFFECTS: Constructor of Kaboom GUI, responsible for the main menu and music
    public KaboomGUI() {
        initialize();
        music("./data/elevatormusic.wav");

        setTitle("Kaboom");
        splitPane = new JSplitPane();
        getContentPane().add(splitPane);

        welcomePanel = new JPanel();
        mainPanel = new JPanel();

        setPanels();
        createWelcomeLabel();
        createButtons();

        initializeSplitPlane(splitPane, welcomePanel, mainPanel);


        confirmSave();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,600);
        setUndecorated(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes Library, Queue, JsonReader, and JsonWriter
    private void initialize() {
        this.lib = new Library();
        this.queue = new Queue();
        this.jsonReader = new JsonReader(JSONLIBRARY_STORE, JSONQUEUE_STORE);
        this.jsonWriter = new JsonWriter(JSONLIBRARY_STORE, JSONQUEUE_STORE);

        confirmLoad();
    }

    // MODIFIES: this
    // EFFECTS: Modifies the panels for the main menu
    private void setPanels() {
        welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        welcomePanel.setBackground(new Color(60,90,50));
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.setBackground(new Color(219, 199, 80));
    }

    // MODIFIES: this
    // EFFECTS: Creates the welcome label on the main menu
    private void createWelcomeLabel() {
        JLabel welcomelabel = new JLabel("Welcome to Kaboom! Enjoy!");
        welcomelabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomelabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomelabel);
    }

    // MODIFIES: this
    // EFFECTS: Creates the Library and Queue buttons on the main menu
    private void createButtons() {
        JButton libButton = new JButton("Access Library");
        libButtonListener(libButton);
        JButton queueButton = new JButton("Access Queue");
        queueButtonListener(queueButton);
        mainPanel.add(libButton);
        mainPanel.add(queueButton);
    }

    // EFFECTS: ActionListener for the Library Button; if pressed, takes user to Library menu
    private void libButtonListener(JButton libButton) {
        libButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryGUI(lib, queue);
            }
        });
    }

    // EFFECTS: ActionListener for the Queue Button; if pressed, takes user to Queue menu
    private void queueButtonListener(JButton queueButton) {
        queueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QueueGUI(queue);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Initializes the split plane in the main menu
    private void initializeSplitPlane(JSplitPane splitPane, JPanel libPanel, JPanel queuePanel) {
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(200);
        splitPane.setTopComponent(libPanel);
        splitPane.setBottomComponent(queuePanel);
        add(splitPane);
    }

    // EFFECTS: Asks user if they want to load their data or not
    private void confirmLoad() {
        int confirmed = JOptionPane.showConfirmDialog(null,
                "Do you want to load your saved data?", "Load Data",
                JOptionPane.YES_NO_OPTION);

        if (confirmed == JOptionPane.YES_OPTION) {
            try {
                this.lib = jsonReader.readLibrary();
                this.queue = jsonReader.readQueue();
                JOptionPane.showMessageDialog(null, "Progress loaded successfully");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to load data");
            }
        }
    }

    // EFFECTS: Asks user if they want to save their current data or not
    public void confirmSave() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Do you want to save your progress?", "Save Data",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    try {
                        KaboomGUI.this.jsonWriter.open();
                        KaboomGUI.this.jsonWriter.write(lib);
                        KaboomGUI.this.jsonWriter.write(queue);
                        KaboomGUI.this.jsonWriter.close();
                        JOptionPane.showMessageDialog(null, "Progress saved successfully");
                    } catch (FileNotFoundException x) {
                        JOptionPane.showMessageDialog(null, "Unable to save data");
                    }
                }
                System.exit(0);
            }
        });
    }

    // EFFECTS: Creates the music path for the GUI
    private void music(String filepath) {
        try {
            File musicPath = new File(filepath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                JOptionPane.showMessageDialog(null, "Error: Unable to run music");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: Unable to run music");
        }
    }

}