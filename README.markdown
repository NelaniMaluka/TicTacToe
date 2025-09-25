# Tic-Tac-Toe Java Application

A desktop-based Tic-Tac-Toe game built in **Java** using **Swing** for the GUI. The game features multiple board sizes and difficulty levels with a smooth and interactive user interface.

---

## Table of Contents
- [Features](#features)
- [Demo](#demo)
- [Installation](#installation)
- [Usage](#usage)
- [Game Modes](#game-modes)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

---

## Features
- Play Tic-Tac-Toe with 3x3, 4x4, and 5x5 board sizes.
- Three difficulty levels: Easy, Medium, and Hard.
- Interactive GUI with **Swing**.
- Automatic win detection and game reset functionality.
- Visual notifications for game status (winner or draw).

---

## Demo
<img src="https://github.com/NelaniMaluka/TicTacToe/blob/main/readme%20images/1.png" alt="Home Screen" width="400"/>
<img src="https://github.com/NelaniMaluka/TicTacToe/blob/main/readme%20images/2.png" alt="3 Tile win" width="400"/>
<img src="https://github.com/NelaniMaluka/TicTacToe/blob/main/readme%20images/3.png" alt="Tie" width="400"/>
<img src="https://github.com/NelaniMaluka/TicTacToe/blob/main/readme%20images/4.png" alt="4 Tile win" width="400"/>
<img src="https://github.com/NelaniMaluka/TicTacToe/blob/main/readme%20images/5.png" alt="5 Tile win" width="400"/>

---

## Installation

1. **Clone the repository**:
   ```bash
   git clone git clone https://github.com/NelaniMaluka/TicTacToe.git
   ```
2. Navigate to the project directory:
   ```bash
   cd tic-tac-toe-java
   ```
3. Compile the Java files:
   ```bash
   javac -d bin src/main/java/org/example/*.java
   ```
4. Run the application:
   ```bash
   java -cp bin org.example.TicTacToe
   ```
5. Make sure you have **Java 11 or higher** installed.

---

## Usage
1. Select the board size (default is 3x3).
2. Choose a difficulty level (Easy, Medium, Hard).  
   *Note: Hard mode is limited to 3x3 due to computational resources.*
3. Take turns clicking on the cells to place your mark (X or O).
4. The game will automatically detect a winner or a draw.
5. Reset the game anytime using the reset button.

---

## Game Modes
- **Easy**: Random moves by AI.
- **Medium**: AI attempts simple strategies.
- **Hard**: AI uses advanced logic (Minimax algorithm) for optimal play.

---

## Development
- Built with **Java Swing** for the GUI.
- Core game logic separated from UI for easier maintenance.
- AI implemented using **Minimax algorithm** with configurable depth for difficulty levels.

---

## Contributing
1. Fork the repository.
2. Create your feature branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m "Add new feature"`.
4. Push to the branch: `git push origin feature-name`.
5. Create a Pull Request.

---

## License
This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.
