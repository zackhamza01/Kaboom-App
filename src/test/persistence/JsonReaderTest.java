package persistence;

import model.Library;
import model.Queue;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/notFile.json", "./data/notFile2.json");
        try {
            Library library = reader.readLibrary();
            Queue queue = reader.readQueue();
            fail("Test failed, IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyLibraryAndQueue() {
        // This test uses the already written testEmptyLibrary and testEmptyLibrary json files from the JsonWriterTest
        // Test out JsonWriterTest first before running these tests
        JsonReader reader = new JsonReader("./data/testEmptyLibrary.json", "./data/testEmptyQueue.json");
        try {
            Library library = reader.readLibrary();
            Queue queue = reader.readQueue();
            assertEquals(0, library.getLibrary().size());
            assertEquals(0, queue.getQueue().size());
        } catch (IOException e) {
            fail("Test failed. Could not read file");
        }
    }

    @Test
    void testReaderGeneralLibraryAndQueue() {
        JsonReader reader = new JsonReader("./data/testGeneralLibrary.json", "./data/testGeneralQueue.json");
        try {
            Library library = reader.readLibrary();
            Queue queue = reader.readQueue();
            assertEquals(1, queue.getQueue().size());
            assertEquals(2, library.getLibrary().size());
            assertEquals("Rap", library.getLibrary().get(0).getPlaylistName());
            assertEquals("Oldies", library.getLibrary().get(1).getPlaylistName());
        } catch (IOException e) {
            fail("Test failed. Could not read file");
        }
    }

}
