package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// This class is using similar formatting to the JsonSerializationDemo on Github

public class JsonReader {
    private String librarySource;
    private String queueSource;

    // EFFECTS: constructs reader to read from source files
    public JsonReader(String librarySource, String queueSource) {
        this.librarySource = librarySource;
        this.queueSource = queueSource;
    }

    // EFFECTS: Reads library from file and returns it
    // throws IOException if an error occurs reading data from file
    public Library readLibrary() throws IOException {
        String jsonData = readFile(librarySource);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: Reads queue from file and returns it
    // throws IOException if an error occurs reading data from file
    public Queue readQueue() throws IOException {
        String jsonData = readFile(queueSource);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseQueue(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Parses library from JSON object and returns it

    private Library parseLibrary(JSONObject jsonObject) {
        Library library = new Library();
        JSONArray jsonLibrary = jsonObject.getJSONArray("library");
        for (Object jsonObj : jsonLibrary) {
            library.addPlaylistToLibrary(parsePlayList(((JSONObject) jsonObj)));
        }
        return library;
    }

    // EFFECTS: Parses songs from JSON Object and adds them to playlist

    private Playlist parsePlayList(JSONObject jsonObject) {
        Playlist temp = new Playlist(jsonObject.getString("name"));
        JSONArray jsonSongsArray = jsonObject.getJSONArray("playlist");
        for (Object jsonSong : jsonSongsArray) {
            JSONObject tempSong = (JSONObject) jsonSong;
            temp.addSong(new Song(tempSong.getString("title"), tempSong.getString("artist")));
        }
        return temp;
    }

    // EFFECTS: Parses queue from JSON Object and returns it

    private Queue parseQueue(JSONObject jsonObject) {
        Queue queue = new Queue();
        addSongs(queue, jsonObject);
        return queue;
    }

    // MODIFIES: queue
    // EFFECTS: Parses songs from JSON Object and adds to queue

    private void addSongs(Queue queue, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("queue");
        for (Object json : jsonArray) {
            JSONObject nextSong = (JSONObject) json;
            addSong(queue, nextSong);
        }
    }

    // MODIFIES: queue
    // EFFECTS: Parses song from JSON Object and adds it to queue

    private void addSong(Queue queue, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String artist = jsonObject.getString("artist");
        Song song = new Song(title, artist);
        queue.addSong(song);
    }

}
