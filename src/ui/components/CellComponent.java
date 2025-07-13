package ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import model.Cell;

public class CellComponent extends JTextField {

    private static final long serialVersionUID = 1L;

    private Cell cell;

    public CellComponent(Cell cell) {
        setCell(cell);
        configureComponent();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("Cell cannot be null");
        }
        this.cell = cell;
        updateFromCell();
    }

    private void configureComponent() {
        setHorizontalAlignment(JTextField.CENTER);
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setBorder(new LineBorder(Color.GRAY, 1));
        setPreferredSize(new Dimension(50, 50));
        setMargin(new Insets(0, 0, 0, 0));
        setOpaque(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!cell.isEditable()) {
                    e.consume();
                    return;
                }

                char ch = e.getKeyChar();
                if (!Character.isDigit(ch) || ch == '0') {
                    e.consume(); // only allow digits 1-9
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String text = getText().trim();
                if (text.length() == 1 && Character.isDigit(text.charAt(0))) {
                    cell.setValue(Integer.parseInt(text));
                } else if (text.isEmpty()) {
                    cell.setValue(0);
                } else {
                    setText(""); // clean invalid input
                    cell.setValue(0);
                }
            }
        });
    }

    public void updateFromCell() {
        setText(cell.getValue() == 0 ? "" : String.valueOf(cell.getValue()));
        setEditable(cell.isEditable());
        setForeground(cell.isEditable() ? Color.BLUE : Color.BLACK);
        setBackground(cell.isEditable() ? Color.WHITE : new Color(230, 230, 230));
    }
}

