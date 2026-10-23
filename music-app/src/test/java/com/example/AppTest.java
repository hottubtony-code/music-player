package com.example;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;

import javax.sound.sampled.AudioSystem;

import org.junit.Test;

import com.google.gson.Gson;

public class AppTest {

    @Test
    public void libraryHasAtLeastTenPlayableSongs() throws Exception {
        File folder = new File("src/main/java/com/example");

        if (!folder.isDirectory()) {
            folder = new File("music-app/src/main/java/com/example");
        }

        Song[] songs;

        try (FileReader reader = new FileReader(new File(folder, "audio-library.json"))) {
            songs = new Gson().fromJson(reader, Song[].class);
        }

        assertNotNull("The library must load.", songs);
        assertTrue("The library needs at least 10 songs.",
                songs.length >= 10);

        for (Song song : songs) {
            assertNotNull("Song title is missing.", song.name());
            assertNotNull("Artist is missing.", song.artist());
            assertNotNull("Genre is missing.", song.genre());
            assertNotNull("Filename is missing.", song.fileName());

            File audio = new File(
                    new File(folder, "wav"), song.fileName());

            assertTrue("Missing audio: " + song.fileName(),
                    audio.isFile());

            AudioSystem.getAudioFileFormat(audio);
        }
    }
}