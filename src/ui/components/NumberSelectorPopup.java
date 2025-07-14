package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NumberSelectorPopup extends JPopupMenu {

    private static final long serialVersionUID = 1L;
    private static final int POPUP_SIZE = 150; // un poco más grande para más espacio
    private static final int BUTTON_SIZE = POPUP_SIZE / 3;

    public interface NumberSelectionListener {
        void numberSelected(int number);
    }

    public NumberSelectorPopup(final NumberSelectionListener listener) {
        setLayout(new GridLayout(3, 3, 2, 2));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setPreferredSize(new Dimension(POPUP_SIZE, POPUP_SIZE));

        for (int i = 1; i <= 9; i++) {
            final int num = i;
            JButton btn = new JButton(String.valueOf(i));
            btn.setFocusable(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 18));

            // Forzar tamaños mínimo, máximo y preferido iguales
            Dimension buttonSize = new Dimension(BUTTON_SIZE, BUTTON_SIZE);
            btn.setPreferredSize(buttonSize);
            btn.setMinimumSize(buttonSize);
            btn.setMaximumSize(buttonSize);

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    listener.numberSelected(num);
                    setVisible(false);
                }
            });

            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.setPreferredSize(buttonSize);
            wrapper.setMinimumSize(buttonSize);
            wrapper.setMaximumSize(buttonSize);
            wrapper.add(btn);
            add(wrapper);
        }
    }
}

