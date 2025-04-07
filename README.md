# COMP-2522-202510-Term-Project-Zombie-Survival
> **Project Title:** Rot 'N' Run   
> **Developer:** Kanon Nishiyama  
> **Music:** Carxofa (on itch.io) [[Link]](https://carxofamusic.itch.io/dark-and-dystopian-music-pack-lite)

## Project Description

Welcome to **_Rot 'N' Run_**, an arcade-style zombie-survival game created using 
Java and libGDX.  

This game was created as part of a term project for COMP 2522 at British Columbia 
Institute of Technology (BCIT).

The goal was to create a fun and unique single-player desktop game with UI/UX implemented 
using Java and libGDX.

## How to Run

To run this application, simply **Run** the `Lwjgl3Launcher.java` file
found in the `lwjgl3/src/main/java/io/github/ZombieSurvival/lwjgl3` directory.

## How to Play  

> Main Menu
> - **Start** a game by **Clicking** on a difficulty option
> - When you run the game for the first time, only easy mode will be available
> - **Unlock** harder modes by playing previous difficulties and getting a score higher then 100
> - You are able to quit this game by clicking on the exit button

> Game Controls
> - **Move** the player character using `WASD` or the `Arrow Keys`
> - Overtime, both zombies and useful items will be added onto the playing field
> - **Avoid** zombies while **Collecting** items that either heal your character or increase your score
> - Once your `Stamina` hits 0, your game will end, showing you your final score
> - If your `HP` hits 0, your game is over, and the scores for that game are lost
> - Your goal in this game is to increase your score and **Survive** as long as you can


## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## Game Classes

- `Screens`
  - `GameOverScreen`: the game over screen.
  - `GameScreen`: the playable game screen.
  - `MainMenuScreen`: the main menu screen.
  - `ResultScreen`: the result screen.
- `Sprites`
  - `Difficulty`: enum of different game difficulty options.
  - `Enemy`: an enemy entity.
  - `Entity`: entity that appears in the game screen.
  - `EntityComparator`: compares entities by their y coordinate.
  - `Generate`: creates new entities based on preset values.
  - `Item`: an item entity.
  - `ItemType`: enum of different item types.
  - `Player`: a player entity.
- `RotNRun`: the running game class.


