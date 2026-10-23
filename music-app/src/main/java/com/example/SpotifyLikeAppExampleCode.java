package com.example;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

// declares a class for the app
public class SpotifyLikeAppExampleCode {

  // the current audio clip
  private static Clip audioClip;

  /*
   *** IMPORTANT NOTE FOR ALL STUDENTS ***
   * 
   * This next line of code is a "path" that students will need to change in order
   * to play music on their
   * computer. The current path is for my laptop, not yours.
   * 
   * If students who do not understand whre files are located on their computer or
   * how paths work on their computer,
   * should immediately complete the extra credit on "Folders and Directories" in
   * the canvas modules.
   * 
   * Knowing how paths work is fundamental knowledge for using a computer as a
   * technical person.
   * 
   * The play function in this example code plays one song. Once student change
   * the directoryPath variable properly,
   * one of the songs should play. Students should implement their own code for
   * all the functionality in the assignment.
   * 
   * Students can use, and find their own music. You do not have to use or listen
   * to my example music! Have fun!
   * 
   * Students who do not know what a path is on their computers, and how to use a
   * path, are often unable complete
   * this assignment succesfullly. If this is your situation, please complete the
   * extra credit on Folders Path,
   * and Directories. Also, please do google and watch youtube videos if that is
   * helpful too.
   * 
   * Thank you! -Gabriel
   * 
   */

  private static String directoryPath = new File("music-app/src/main/java/com/example").isDirectory()
      ? "music-app/src/main/java/com/example"
      : "src/main/java/com/example";

  // "main" makes this class a java app that can be executed
  public static void main(final String[] args) {
    // reading audio library from json file
    Song[] library = readAudioLibrary();

    // create a scanner for user input
    Scanner input = new Scanner(System.in);

    String userInput = "";
    while (!userInput.equals("q")) {
      menu();

      // get input
      userInput = input.nextLine();

      // accept upper or lower case commands
      userInput = userInput.toLowerCase();

      // do something
      handleMenu(userInput, library, input);
    }

    // close the scanner
    input.close();
  }

  /*
   * displays the menu for the app
   */
  public static void menu() {
    System.out.println("---- SpotifyLikeApp ----");
    System.out.println("[H]ome");
    System.out.println("[S]earch by title");
    System.out.println("[L]ibrary");
    System.out.println("[P]lay");
    System.out.println("[Q]uit");

    System.out.println("");
    System.out.print("Enter a choice (h, s, l, p, or q): ");
  }

  /*
   * handles the user input for the app
   */
  public static void handleMenu(String userInput, Song[] library, Scanner input) {
    switch (userInput) {
      case "h":
        System.out.println("-->Home<--");
        System.out.println("Welcome to your music player!");
        System.out.println("Songs in your library: " + library.length);
        System.out.println("Choose s to search or l to browse your library.");
        System.out.println();
        break;
      case "s":
        System.out.print("Enter part of a song title: ");
        String search = input.nextLine().trim().toLowerCase();
        boolean found = false;

        if (search.isEmpty()) {
          System.out.println("Please enter a title.");
          break;
        }

        for (int i = 0; i < library.length; i++) {
          if (library[i].name().toLowerCase().contains(search)) {
            System.out.println((i + 1) + ". "
                + library[i].name() + " - "
                + library[i].artist());
            found = true;
          }
        }

        if (!found) {
          System.out.println("No matching songs found.");
        } else {
          System.out.print("Enter a result's song number, or 0 to return: ");
          try {
            int selection = Integer.parseInt(input.nextLine().trim());

            if (selection >= 1 && selection <= library.length
                && library[selection - 1].name()
                    .toLowerCase().contains(search)) {
              System.out.println("Now playing: "
                  + library[selection - 1].name() + " - "
                  + library[selection - 1].artist());
              play(library, selection - 1);
            } else if (selection != 0) {
              System.out.println("Please choose a song from the search results.");
            }
          } catch (NumberFormatException e) {
            System.out.println("Please enter a whole number.");
          }
        }
        break;
      case "l":
        System.out.println("-->Library<--");
        for (int i = 0; i < library.length; i++) {
          System.out.println((i + 1) + ". "
              + library[i].name() + " - "
              + library[i].artist());
        }
        System.out.print("Enter a song number, or 0 to return: ");
        try {
          int choice = Integer.parseInt(input.nextLine().trim());

          if (choice >= 1 && choice <= library.length) {
            System.out.println("Now playing: "
                + library[choice - 1].name() + " - "
                + library[choice - 1].artist());
            play(library, choice - 1);
          } else if (choice != 0) {
            System.out.println("That song number is not in the library.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Please enter a whole number.");
        }
        break;
      case "p":
        System.out.println("-->Play<--");
        play(library);
        break;
      case "q":
        System.out.println("-->Quit<--");
        if (audioClip != null) {
          audioClip.stop();
          audioClip.close();
          audioClip = null;
        }
        break;
      default:
        break;
    }
  }

  /*
   * plays an audio file
   */
  public static void play(Song[] library) {
    play(library, 3);
  }

  public static void play(Song[] library, int i) {
    // open the audio file

    // get the filePath and open a audio file
    final String filename = library[i].fileName();
    final String filePath = directoryPath + "/wav/" + filename;
    final File file = new File(filePath);

    // stop the current song from playing, before playing the next one
    if (audioClip != null) {
      audioClip.close();
    }

    try {
      // create clip
      audioClip = AudioSystem.getClip();

      // get input stream
      final AudioInputStream in = AudioSystem.getAudioInputStream(file);

      audioClip.open(in);
      audioClip.setMicrosecondPosition(0);
      audioClip.loop(Clip.LOOP_CONTINUOUSLY);
      System.out.println("Title: " + library[i].name());
      System.out.println("Artist: " + library[i].artist());

      if (library[i].year() == 0) {
        System.out.println("Year: Unknown");
      } else {
        System.out.println("Year: " + library[i].year());
      }

      System.out.println("Genre: " + library[i].genre());
      System.out.println("File path: " + filePath);
      System.out.println();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // read the audio library of music
  public static Song[] readAudioLibrary() {
    // get the file path
    final String jsonFileName = "audio-library.json";
    final String filePath = directoryPath + "/" + jsonFileName;

    Song[] library = null;
    try {
      System.out.println("Reading the file " + filePath);
      JsonReader reader = new JsonReader(new FileReader(filePath));
      library = new Gson().fromJson(reader, Song[].class);
    } catch (Exception e) {
      System.out.printf("ERROR: unable to read the file %s\n", filePath);
      System.out.println();
    }

    return library;
  }
}
