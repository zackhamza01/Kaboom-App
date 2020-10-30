package persistence;

import model.Playlist;
import model.Library;
import model.Queue;
import org.json.JSONObject;

import java.io.*;

// This class is using similar formatting to the JsonSerializationDemo on Github

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter libraryWriter;
    private PrintWriter queueWriter;
    private String libraryDestination;
    private String queueDestination;

    // EFFECTS: Constructs writer to write to destination file
    public JsonWriter(String libraryDestination, String queueDestination) {
        this.libraryDestination = libraryDestination;
        this.queueDestination = queueDestination;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer, throws FileNotFoundException if destination file cannot
    // be opened for writing

    public void open() throws FileNotFoundException {
        libraryWriter = new PrintWriter(new File(libraryDestination));
        queueWriter = new PrintWriter(new File(queueDestination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of library to file

    public void write(Library lib) {
        JSONObject json = lib.toJson();
        saveToLibraryFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of queue to file

    public void write(Queue queue) {
        JSONObject json = queue.toJson();
        saveToQueueFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer

    public void close() {
        libraryWriter.close();
        queueWriter.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to library file
    private void saveToLibraryFile(String json) {
        libraryWriter.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes string to queue file

    private void saveToQueueFile(String json) {
        queueWriter.print(json);
    }
}
