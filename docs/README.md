# TeamJusticeLeague_Fall2024

This project is a Java application structured using the MVC (Model-View-Controller) architecture.

## Project Structure

```
TeamJusticeLeague_Fall2024
├── src
│   ├── controller
│   │   ├── CommandParser.java
│   │   └── GameController.java
│   ├── model
│   │   ├── data
│   │   │   ├── items.txt
│   │   │   ├── monsters.txt
│   │   │   ├── puzzles.txt
│   │   │   └── rooms.txt
│   │   ├── Inventory.java
│   │   ├── Item.java
│   │   ├── Monster.java
│   │   ├── Player.java
│   │   ├── Puzzle.java
│   │   └── Room.java
│   ├── view
│   │   ├── ConsoleUI.java
│   │   └── Menu.java
│   └── utils
│       ├── GameUtils.java
│       └── RandomGenerator.java
├── tests
│   ├── Gametest.java
│   ├── MonsterTest.java
│   ├── PlayerTest.java
│   └── PuzzleTest.java
├── assets
│   └── placeholder.txt
├── build
│   └── placeholder.txt
├── docs
│   ├── README.md
│   └── SRS (Updated Monsters) v2.docx
└── .gitignore
```

## Getting Started

To get started with this project, clone the repository and navigate to the project directory.

```bash
git clone <repository-url>
cd TeamJusticeLeague_Fall2024
```

## Usage

This project is currently under development. The following components are being implemented:
- Core game logic in `GameController.java`
- Command parsing in `CommandParser.java`
- Inventory and item management
- Room, puzzle, and monster interactions

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.
