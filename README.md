# Sudoku Game - Java Desktop Application

A classic Sudoku game built using **Java 8**, developed in **Eclipse IDE**, and implemented with the **Java Swing** GUI toolkit. This desktop application allows users to play Sudoku puzzles in a simple and user-friendly interface.

---

## 🧩 Features

- 9x9 Sudoku grid with editable cells
- Input validation (only digits 1–9 allowed)
- Basic puzzle generation (static or random)
- Highlighting of selected cells or rows/columns
- Light and responsive interface with Java Swing
- Ready to run on any system with Java 8 installed

---

## 📁 Project Structure

```
SudokuProject/
├── src/
│   ├── app/           # Runner
│   ├── logic/         # Game logic: Board generator, Validators
│   ├── model/         # Game entities: Board, Cell
│   ├── sources/       # Media sources: Videos, Images
│   ├── ui/            # Swing components and windows: MainWindow, CellComponent
│   └── util/          # Utilities: PuzzleLoader, Timer, etc.
├── bin/               # Compiled .class files (ignored by Git)
├── .gitignore
├── README.md
└── LICENSE
```

---

## 🚀 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/Br4voCode/Sudoku-Game-in-Java.git
   ```

2. **Open the project in Eclipse**
   - Go to `File > Open Projects from File System...`
   - Select the project folder

3. **Run the main class**
   - Locate `SudokuApp.java` inside the `src/app/` directory
   - Right-click > `Run As > Java Application`

---

## 🖥️ Requirements

- Java SE Development Kit **8** or later
- Eclipse IDE (or any Java IDE)
- Operating System: Windows, macOS, or Linux

---

## 📌 TODO / Future Improvements

- Add difficulty levels (Easy, Medium, Hard)
- Implement Sudoku solver
- Add timer and score tracking
- Sound effects and visual themes
- Export/import puzzles

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---

## 🤝 Contributions

Contributions are welcome! Feel free to fork this repository and submit a pull request with your improvements or bug fixes.
