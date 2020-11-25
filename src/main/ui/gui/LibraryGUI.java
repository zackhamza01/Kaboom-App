package ui.gui;

import model.Library;
import model.Playlist;
import model.Queue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI extends JFrame {
    // fields
    private Library lib;
    private Queue queue;
    private JSplitPane splitPane;
    private JPanel playlistsInfo;
    private PlaylistPanel playlistButtonsPanel;
    private JLabel playlistinfoLabel;

    // MODIFIES: this
    // EFFECTS: Creates a Library menu for the user
    public LibraryGUI(Library lib, Queue queue) {
        this.lib = lib;
        this.queue = queue;
        setTitle("Library");

        splitPane = new JSplitPane();
        getContentPane().add(splitPane);

        playlistsInfo = new JPanel();
        playlistButtonsPanel = new PlaylistPanel(lib, queue);

        initializePanels();

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(playlistsInfo);
        splitPane.setBottomComponent(playlistButtonsPanel);

        add(splitPane);

        setLocationRelativeTo(null);
        setSize(600,600);
        setUndecorated(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the panels in the Library menu
    private void initializePanels() {
        playlistsInfo.setLayout(new FlowLayout(FlowLayout.CENTER));
        playlistsInfo.setBackground(new Color(65, 135, 61));
        createLabel();
        createButtons();
    }

    // MODIFIES: this
    // EFFECTS: Creates the playlist status label for the Library menu
    private void createLabel() {
        if (lib.getLibrary().isEmpty()) {
            playlistinfoLabel = new JLabel("There are no playlists in your library! Try creating one!");
        } else {
            playlistinfoLabel = new JLabel(lib.showPlaylists());
        }
        playlistinfoLabel.setFont(new Font("Serif", Font.PLAIN, 13));
        playlistinfoLabel.setForeground(Color.WHITE);
        playlistsInfo.add(playlistinfoLabel);
    }

    // MODIFIES: this
    // EFFECTS: Creates the buttons for the Library menu
    private void createButtons() {
        JButton createplaylistbutton = new JButton("Create Playlist");
        playlistButtonListener(createplaylistbutton);
        playlistsInfo.add(createplaylistbutton);
    }

    // EFFECTS: ActionListener for the Create Playlist button; if pressed, asks user to enter name for a new playlist
    private void playlistButtonListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playlistname = JOptionPane.showInputDialog("Enter the name of the playlist: ");
                lib.addPlaylistToLibrary(new Playlist(playlistname));
                JOptionPane.showMessageDialog(null, playlistname + " has successfully been added to your library!");
            }
        });
    }



}
