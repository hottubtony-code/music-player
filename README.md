# music-player
Java music player assignment

## Music Credits

The following tracks are by Kevin MacLeod (https://incompetech.com):

- Thief in the Night
- Maccary Bay
- Gonna Start
- Dub Eastern
- Mandeville
- Montego

These six tracks are licensed under Creative Commons:
By Attribution 4.0 License
https://creativecommons.org/licenses/by/4.0/

Changes made: Each track was shortened to approximately
30 seconds and converted from MP3 to WAV for this project.
## How to Run

Requirements: JDK 21, Maven, and VS Code with Java support.

1. Open the repository folder in VS Code.
2. Open music-app/src/main/java/com/example/SpotifyLikeAppExampleCode.java.
3. Click Run above the main method.
4. Enter commands in the terminal:
   - h: Home
   - s: Search by song title
   - l: List the library and select a song
   - p: Play the default song
   - q: Stop the music and quit

The library contains 11 songs. Selecting another song replaces
the song currently playing.

## Run the Test

From the repository folder, run:

mvn -f music-app/pom.xml test

The test checks that the JSON library contains at least 10 songs,
required text fields are present, and every referenced audio file
exists and has a format Java recognizes.