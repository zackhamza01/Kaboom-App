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

    public JsonWriter(String libraryDestination, String queueDestination) {
        this.libraryDestination = libraryDestination;
        this.queueDestination = queueDestination;
    }

    public void open() throws FileNotFoundException {
        libraryWriter = new PrintWriter(new File(libraryDestination));
        queueWriter = new PrintWriter(new File(queueDestination));
    }

    public void write(Library lib) {
        JSONObject json = lib.toJson();
        saveToLibraryFile(json.toString(TAB));
    }

    public void write(Queue queue) {
        JSONObject json = queue.toJson();
        saveToQueueFile(json.toString(TAB));
    }

    public void close() {
        libraryWriter.close();
        queueWriter.close();
    }


    private void saveToLibraryFile(String json) {
        libraryWriter.print(json);
    }

    private void saveToQueueFile(String json) {
        queueWriter.print(json);
    }
}
