package logic;

import java.util.Random;

import model.Board;
import model.Cell;

public class BoardGenerator {

    private Board board;
    private Random random;

    public BoardGenerator() {
        board = new Board();
        random = new Random();
    }

    public Board generateCompleteBoard() {
        board = new Board();
        if (fillBoard(0, 0)) {
            // Después de generar, fija todas las celdas como no editables (fijas)
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    Cell cell = board.getCell(r, c);
                    cell.setEditable(false);
                }
            }
            return board;
        } else {
            throw new RuntimeException("No se pudo generar un tablero válido");
        }
    }

    private boolean fillBoard(int row, int col) {
        if (row == 9) return true;

        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col + 1) % 9;

        Cell cell = board.getCell(row, col);
        if (cell.getValue() != 0) {
            return fillBoard(nextRow, nextCol);
        }

        int[] numbers = generateRandomNumbers();

        for (int num : numbers) {
            if (isValidPlacement(row, col, num)) {
                // Usa trySetValue para mantener consistencia con la lógica de Cell
                if (cell.trySetValue(num)) {
                    if (fillBoard(nextRow, nextCol)) return true;
                    cell.clear();
                }
            }
        }

        return false;
    }

    private int[] generateRandomNumbers() {
        int[] nums = new int[9];
        for (int i = 0; i < 9; i++) nums[i] = i + 1;
        for (int i = nums.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        return nums;
    }

    private boolean isValidPlacement(int row, int col, int num) {
        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(row, col, num);
    }

    private boolean isInRow(int row, int num) {
        for (int c = 0; c < 9; c++) {
            if (board.getCell(row, c).getValue() == num) return true;
        }
        return false;
    }

    private boolean isInCol(int col, int num) {
        for (int r = 0; r < 9; r++) {
            if (board.getCell(r, col).getValue() == num) return true;
        }
        return false;
    }

    private boolean isInBox(int row, int col, int num) {
        int boxStartRow = row - row % 3;
        int boxStartCol = col - col % 3;
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartCol; c < boxStartCol + 3; c++) {
                if (board.getCell(r, c).getValue() == num) return true;
            }
        }
        return false;
    }

    /**
     * Método para "remover" números y crear un puzzle.
     * Las celdas removidas quedan editables para que el jugador pueda modificarlas.
     * 
     * @param holes número de celdas vacías que quieres dejar
     */
    public void removeNumbers(int holes) {
        if (holes < 0 || holes > 81) {
            throw new IllegalArgumentException("Holes must be between 0 and 81");
        }

        int removed = 0;
        while (removed < holes) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            Cell cell = board.getCell(row, col);
            if (cell.getValue() != 0) {
                cell.clear();
                cell.setEditable(true);
                removed++;
            }
        }
    }

    public Board getBoard() {
        return board;
    }
}
