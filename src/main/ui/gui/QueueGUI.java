package ui.gui;

import model.Queue;
import model.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueGUI extends JFrame {
    // fields
    private Queue queue;
    private JSplitPane splitPane;
    private JPanel queueInfoPanel;
    private JPanel queueButtonsPanel;
    private JLabel queueInfoLabel;

    // MODIFIES: this
    // EFFECTS: Creates the Queue menu for user
    public QueueGUI(Queue queue) {
        this.queue = queue;
        setTitle("Queue");

        splitPane = new JSplitPane();
        getContentPane().add(splitPane);

        queueInfoPanel = new JPanel();
        queueButtonsPanel = new JPanel();

        queueInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        queueButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        initializePanels();

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(queueInfoPanel);
        splitPane.setBottomComponent(queueButtonsPanel);

        add(splitPane);

        setLocationRelativeTo(null);
        setSize(600,600);
        setUndecorated(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the panels in the Queue menu
    private void initializePanels() {
        queueInfoPanel.setBackground(new Color(236, 125, 51));
        queueButtonsPanel.setBackground(new Color(93, 118, 161));
        createLabelForPanel(queueInfoPanel);
        createButtonsForPanel(queueButtonsPanel);
    }

    // MODIFIES: this
    // EFFECTS: Creates the Queue status label for the Queue menu
    private void createLabelForPanel(JPanel queueInfoPanel) {
        if (queue.getQueue().isEmpty()) {
            queueInfoLabel = new JLabel("Queue is empty. Try adding some songs!");
        } else {
            queueInfoLabel = new JLabel(queue.viewQueue());
        }
        queueInfoPanel.add(queueInfoLabel);
    }

    // MODIFIES: this
    // EFFECTS: Creates the buttons for the Queue menu
    private void createButtonsForPanel(JPanel queueButtonPanel) {
        JButton playbutton = new JButton("Play");
        JButton addsongbutton = new JButton("Add song");
        playButtonActionListener(playbutton);
        addSongButtonActionListener(addsongbutton);
        queueButtonPanel.add(playbutton);
        queueButtonPanel.add(addsongbutton);
    }

    // MODIFIES: this
    // EFFECTS: ActionListener for the Play button; if pressed, the first song is played in the Queue
    private void playButtonActionListener(JButton playbutton) {
        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (queue.getQueue().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Queue is empty! Cannot play song!");
                } else {
                    JOptionPane.showMessageDialog(null, queue.getQueue().get(0).description() + " is playing!");
                    JOptionPane.showMessageDialog(null, queue.getQueue().get(0).description() + " is done playing!");
                    queue.play();
                }
            }
        });
    }

    // EFFECTS: ActionListener for Add Song button; if pressed, prompts user to create a new Song
    private void addSongButtonActionListener(JButton addSongButton) {
        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String songName = JOptionPane.showInputDialog("Enter the song name: ");
                String artist = JOptionPane.showInputDialog("Enter the artist's name: ");
                Song song = new Song(songName, artist);
                queue.addSong(song);
                JOptionPane.showMessageDialog(null, song.description() + " has been successfully added to queue!");
            }
        });
    }

}
