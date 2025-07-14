package util;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class FontScaler {

    /**
     * Aplica escalado automático de fuente a cualquier componente según el tamaño de la ventana contenedora.
     *
     * @param component El componente con texto (JLabel, JButton, JTextField, etc.)
     * @param frame     La ventana principal (JFrame)
     * @param factor    El factor de escala (por ejemplo, 0.03f)
     */
    public static void attachToFrame(final JComponent component, final JFrame frame, final float factor) {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = frame.getSize();
                int base = Math.min(size.width, size.height);
                int newFontSize = Math.max(10, Math.round(base * factor));
                component.setFont(component.getFont().deriveFont((float) newFontSize));
            }
        });
    }
}

