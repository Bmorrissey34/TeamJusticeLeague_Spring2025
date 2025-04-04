# TeamJusticeLeague_Spring2025

This project is a Java console-based game structured using the MVC (Model-View-Controller) architecture. The game features text-based interactions, where players explore rooms, solve puzzles, and engage in combat with monsters.

## Project Structure

```
TeamJusticeLeague_Spring2025
├── src
│   ├── controller
│   │   ├── CommandParser.java       # Handles user input and command parsing
│   │   └── GameController.java      # Manages the main game flow
│   ├── model
│   │   ├── data
│   │   │   ├── items.txt            # Data file for game items
│   │   │   ├── monsters.txt         # Data file for monsters
│   │   │   ├── puzzles.txt          # Data file for puzzles
│   │   │   └── rooms.txt            # Data file for rooms
│   │   ├── Inventory.java           # Represents the player's inventory
│   │   ├── Item.java                # Represents an item in the game
│   │   ├── Monster.java             # Represents a monster
│   │   ├── Player.java              # Represents the player
│   │   ├── Puzzle.java              # Represents a puzzle
│   │   └── Room.java                # Represents a room
│   ├── view
│   │   ├── ConsoleUI.java           # Handles text-based input/output
│   │   └── Menu.java                # Displays menus and options
│   └── utils
│       ├── GameUtils.java           # Utility methods for game logic
│       └── RandomGenerator.java     # Generates random values for gameplay
├── tests
│   ├── Gametest.java                # Unit tests for game logic
│   ├── MonsterTest.java             # Unit tests for Monster class
│   ├── PlayerTest.java              # Unit tests for Player class
│   └── PuzzleTest.java              # Unit tests for Puzzle class
├── assets
│   └── placeholder.txt              # Placeholder for additional assets
├── build
│   └── placeholder.txt              # Placeholder for build artifacts
├── docs
│   ├── README.md                    # Project documentation
│   └── SRS (Updated Monsters) v2.docx # Software Requirements Specification
└── .gitignore                       # Git ignore file
```

## Getting Started

To get started with this project, clone the repository and navigate to the project directory.

```bash
git clone <repository-url>
cd TeamJusticeLeague_Spring2025
```

### Running the Game

1. Compile the project:
   ```bash
   javac -d build src/**/*.java
   ```

2. Run the game:
   ```bash
   java -cp build main.Main
   ```

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A terminal or command prompt to run the game

## Features

- **Text-Based Exploration**: Navigate through rooms and interact with the environment.
- **Puzzles**: Solve puzzles to progress through the game.
- **Combat System**: Engage in turn-based combat with monsters.
- **Inventory Management**: Collect and use items to aid in your journey.
- **Command Parsing**: Enter commands to interact with the game world.

## Development Status

This project is currently under development. The following components are being implemented:
- Core game logic in `GameController.java`
- Command parsing in `CommandParser.java`
- Inventory and item management
- Room, puzzle, and monster interactions

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes. Ensure your code follows the project's coding standards and includes appropriate documentation.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.
