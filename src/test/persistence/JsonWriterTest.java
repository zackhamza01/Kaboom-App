package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Library lib = new Library();
            Queue queue = new Queue();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json", "./data/my\0illegal:fileName2.json");
            writer.open();
            fail("Test failed. IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyLibraryAndQueue() {
        try {
            Library library = new Library();
            Queue queue = new Queue();
            JsonWriter writer = new JsonWriter("./data/testEmptyLibrary.json", "./data/testEmptyQueue.json");
            writer.open();
            writer.write(library);
            writer.write(queue);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyLibrary.json", "./data/testEmptyQueue.json");
            library = reader.readLibrary();
            queue = reader.readQueue();
            assertEquals(0, library.getLibrary().size());
            assertEquals(0, queue.getQueue().size());
        } catch (IOException e) {
            fail("Test failed. Exception should not have been thrown.");
        }

    }

    @Test
    void testWriterGeneralLibraryAndQueue() {
        try {
            Library library = new Library();
            library.addPlaylistToLibrary(new Playlist("Rap"));
            library.addPlaylistToLibrary(new Playlist("Oldies"));
            Queue queue = new Queue();
            queue.addSong("Replay", "Iyaz");
            JsonWriter writer = new JsonWriter("./data/testGeneralLibrary.json", "./data/testGeneralQueue.json");
            writer.open();
            writer.write(library);
            writer.write(queue);
            writer.close();

            JsonReader reader = new JsonReader("./data/testGeneralLibrary.json", "./data/testGeneralQueue.json");
            library = reader.readLibrary();
            queue = reader.readQueue();
            assertEquals(1, queue.getQueue().size());
            assertEquals(2, library.getLibrary().size());
            assertEquals("Rap", library.getLibrary().get(0).getPlaylistName());
            assertEquals("Oldies", library.getLibrary().get(1).getPlaylistName());
        } catch (IOException e) {
            fail("Test failed. Exception should not have been thrown.");
        }
    }

}
