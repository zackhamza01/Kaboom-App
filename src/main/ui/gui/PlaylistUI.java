package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaylistUI extends JFrame {
    // fields
    private Playlist playlist;
    private Queue queue;
    private JSplitPane splitPane;
    private JPanel songInfoPanel;
    private JPanel playlistButtonsPanel;
    private JLabel songInfoLabel;

    // MODIFIES: this
    // EFFECTS: Creates menu for a specific Playlist that is selected from PlaylistPanel
    public PlaylistUI(Playlist playlist, Queue queue) {
        this.playlist = playlist;
        this.queue = queue;
        setTitle(playlist.getPlaylistName());

        splitPane = new JSplitPane();
        getContentPane().add(splitPane);

        songInfoPanel = new JPanel();
        playlistButtonsPanel = new JPanel();

        initializePanels();

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(songInfoPanel);
        splitPane.setBottomComponent(playlistButtonsPanel);

        add(splitPane);

        setLocationRelativeTo(null);
        setSize(600,600);
        setUndecorated(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes panels for the Playlist menu
    private void initializePanels() {
        songInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        songInfoPanel.setBackground(new Color(74, 54, 106));

        playlistButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        playlistButtonsPanel.setBackground(new Color(154, 27, 68));
        createLabel();
        createButtons();
    }

    // MODIFIES: this
    // EFFECTS: Creates a Song status label in the Playlist menu
    private void createLabel() {
        if (playlist.getPlaylist().isEmpty()) {
            songInfoLabel = new JLabel("There are no songs in this playlist");
        } else {
            songInfoLabel = new JLabel(playlist.viewPlaylist());
        }
        songInfoLabel.setFont(new Font("Serif", Font.PLAIN, 13));
        songInfoLabel.setForeground(Color.WHITE);
        songInfoPanel.add(songInfoLabel);
    }

    // MODIFIES: this
    // EFFECTS: Creates the buttons in the Playlist menu
    private void createButtons() {
        JButton addsongbutton = new JButton("Add song");
        JButton renamebutton = new JButton("Rename");
        JButton addtoqueuebutton = new JButton("Add playlist to queue");
        JButton addshuffledtoqueuebutton = new JButton("Add shuffled playlist to queue");
        addSongListener(addsongbutton);
        renameListener(renamebutton);
        addToQueueListener(addtoqueuebutton);
        addShuffledToQueue(addshuffledtoqueuebutton);
        playlistButtonsPanel.add(addsongbutton);
        playlistButtonsPanel.add(renamebutton);
        playlistButtonsPanel.add(addtoqueuebutton);
        playlistButtonsPanel.add(addshuffledtoqueuebutton);
    }

    // MODIFIES: this
    // EFFECTS: ActionListener for Add Song button; if pressed it prompts user to create a Song
    private void addSongListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String songName = JOptionPane.showInputDialog("Enter the song name: ");
                String artist = JOptionPane.showInputDialog("Enter the artist's name: ");
                Song song = new Song(songName, artist);
                playlist.addSong(song);
                JOptionPane.showMessageDialog(null,
                        song.description() + " has been successfully added to " + playlist.getPlaylistName() + "!");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: ActionListener for Rename button; if pressed, prompts user to make a new name for Playlist
    private void renameListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newSongName = JOptionPane.showInputDialog("Enter the new playlist name: ");
                playlist.rename(newSongName);
                JOptionPane.showMessageDialog(null,
                        "This playlist has been renamed to " + playlist.getPlaylistName() + "!");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: ActionListener for AddToQueue button; if pressed, asks user if they want to add Playlist to Queue
    private void addToQueueListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Do you want to add this playlist to queue?");

                if (confirmed == JOptionPane.YES_OPTION) {
                    queue.addPlaylistToQueue(playlist);
                    JOptionPane.showMessageDialog(null,
                            "Succesfully added " + playlist.getPlaylistName() + " to queue!");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: ActionListener for Add Shuffled Playlist; asks user if they want to add a shuffled Playlist to Queue
    private void addShuffledToQueue(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Do you want to add a shuffled version of this playlist to queue?");

                if (confirmed == JOptionPane.YES_OPTION) {
                    queue.addShuffledPlaylist(playlist);
                    JOptionPane.showMessageDialog(null,
                            "Successfully added a shuffled version of " + playlist.getPlaylistName() + " to queue!");
                }
            }
        });
    }

}
