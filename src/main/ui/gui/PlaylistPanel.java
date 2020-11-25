package ui.gui;

import model.Library;
import model.Playlist;
import model.Queue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaylistPanel extends JPanel {
    // fields
    private Library lib;
    private Queue queue;

    // MODIFIES: this
    // EFFECTS: Creates a panel to specifically make buttons for every current playlist
    public PlaylistPanel(Library lib, Queue queue) {
        this.lib = lib;
        this.queue = queue;

        setupPanel();
        createButtons();
    }

    // EFFECTS: Sets up the layout for the panel
    private void setupPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(new Color(83, 156, 133));
    }

    // EFFECTS: Creates the buttons for every playlist in Library
    private void createButtons() {
        for (Playlist p: lib.getLibrary()) {
            JButton playlistbutton = new JButton(p.getPlaylistName());
            buttonListener(playlistbutton, p);
            add(playlistbutton);
        }
    }

    // EFFECTS: ActionListener for each Playlist button, if pressed, initializes the PlaylistUI class
    private void buttonListener(JButton button, Playlist playlist) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlaylistUI(playlist, queue);
            }
        });
    }
}
