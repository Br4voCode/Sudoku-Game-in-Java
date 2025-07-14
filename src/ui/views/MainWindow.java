package ui.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.SudokuController;
import ui.components.BoardPanel;
import util.FontScaler;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private SudokuController controller;

    public MainWindow() {
        super("Sudoku Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        controller = new SudokuController();
        BoardPanel boardPanel = controller.getBoardPanel();

        JPanel container = new JPanel(new GridBagLayout());
        container.add(boardPanel);  // BoardPanel ocupa el centro, y mantiene proporción
        add(container, BorderLayout.CENTER);
        pack(); // Calcula el tamaño ideal en función del contenido

        // Establece el tamaño mínimo deseado (por ejemplo, 500x500)
        setMinimumSize(new Dimension(500, 500));

        setLocationRelativeTo(null); // Centra en pantalla
        applyFontScaling(boardPanel);

        controller.generateNewPuzzle(55);
    }
    
    private void applyFontScaling(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JComponent) {
                if (comp instanceof JLabel || comp instanceof JTextField || comp instanceof JButton) {
                    FontScaler.attachToFrame((JComponent) comp, this, 0.035f);
                }
                if (comp instanceof Container) {
                    applyFontScaling((Container) comp); // recursivo
                }
            }
        }
    }
}


