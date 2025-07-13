package app;

import javax.swing.SwingUtilities;

import ui.views.MainWindow;

public class SudokuApp {

    public static void main(String[] args) {
        // Run GUI creation on the Swing event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainWindow window = new MainWindow();
                window.setVisible(true);
            }
        });
    }
}


