package ui.components;

import intefaces.CellValueValidator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import model.Cell;

public class CellComponent extends JTextField {

    private static final long serialVersionUID = 1L;

    private static final Color COLOR_SELECCIONADO = new Color(180, 210, 255);
    private static final Color COLOR_FILA_COLUMNA = new Color(220, 235, 255);
    private static final Color COLOR_NO_EDITABLE = new Color(230, 230, 230);
    private static final Color COLOR_EDITABLE = Color.WHITE;
    private static final Color COLOR_TEXTO_NORMAL = Color.BLACK;
    private static final Color COLOR_TEXTO_EDITABLE = Color.BLACK;
    private static final Color COLOR_TEXTO_VALOR_IGUAL = Color.BLUE;
    private static final Color COLOR_TEXTO_INCORRECTO = Color.RED;
    private static final Font FUENTE = new Font("SansSerif", Font.BOLD, 20);
    private static final Dimension TAMANO_CELDA = new Dimension(50, 50);

    private Cell cell;
    private boolean selected = false;
    private boolean sameValue = false;
    private boolean highlightRowCol = false;  
    private CellValueValidator validator;
    private boolean isValueCorrect = true;

    public void setValidator(CellValueValidator validator) {
        this.validator = validator;
    }

    public CellComponent(Cell cell) {
        setCell(cell);
        configureComponent();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        if (cell == null) throw new IllegalArgumentException("Cell cannot be null");
        this.cell = cell;
        updateFromCell();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaintVisualState();
    }

    public void setSameValue(boolean sameValue) {
        this.sameValue = sameValue;
        repaintVisualState();
    }

    public void setHighlightRowCol(boolean highlight) {
        this.highlightRowCol = highlight;
        repaintVisualState();
    }

    public void updateFromCell() {
        setText(cell.isEmpty() ? "" : String.valueOf(cell.getValue()));
        setEditable(cell.isEditable());
        repaintVisualState();
    }

    private void configureComponent() {
        setHorizontalAlignment(CENTER);
        setFont(FUENTE);
        setBorder(new LineBorder(Color.GRAY, 1));
        setPreferredSize(TAMANO_CELDA);
        setMargin(new Insets(0, 0, 0, 0));
        setOpaque(true);
        setHighlighter(null); // No permitir selección de texto
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        configureDocumentFilter();
        configureMouseListeners();

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && cell.isEditable()) {
                    showNumberSelectorPopup(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void configureDocumentFilter() {
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;

                if (string.length() == 1 && cell.isValidValue(Character.getNumericValue(string.charAt(0)))) {
                    fb.remove(0, fb.getDocument().getLength());
                    fb.insertString(0, string, attr);

                    if (cell.trySetValueFromString(string)) {
                        if (validator != null) {
                            isValueCorrect = validator.isCorrectValue(cell.getRow(), cell.getCol(), cell.getValue());
                        } else {
                            isValueCorrect = true;
                        }
                    } else {
                        isValueCorrect = true;
                    }

                    repaintVisualState();
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) return;

                if (text.length() == 1 && cell.isValidValue(Character.getNumericValue(text.charAt(0)))) {
                    fb.remove(0, fb.getDocument().getLength());
                    fb.insertString(0, text, attrs);

                    if (cell.trySetValueFromString(text)) {
                        if (validator != null) {
                            isValueCorrect = validator.isCorrectValue(cell.getRow(), cell.getCol(), cell.getValue());
                        } else {
                            isValueCorrect = true;
                        }
                    } else {
                        isValueCorrect = true;
                    }

                    repaintVisualState();
                } else if (text.isEmpty()) {
                    fb.remove(0, fb.getDocument().getLength());
                    cell.clear();
                    isValueCorrect = true;
                    repaintVisualState();
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                fb.remove(offset, length);
                if (fb.getDocument().getLength() == 0) {
                    cell.clear();
                    isValueCorrect = true;
                    repaintVisualState();
                }
            }
        });
    }

    private void configureMouseListeners() {
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!selected && !highlightRowCol) {
                    setBackground(COLOR_FILA_COLUMNA);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (!selected && !highlightRowCol) {
                    setBackground(cell.isEditable() ? COLOR_EDITABLE : COLOR_NO_EDITABLE);
                }
            }

            public void mousePressed(MouseEvent e) {
                Container parent = getParent();
                while (parent != null && !(parent instanceof BoardPanel)) {
                    parent = parent.getParent();
                }
                if (parent != null) {
                    ((BoardPanel) parent).handleCellSelection(CellComponent.this);
                }
            }
        });
    }

    private void showNumberSelectorPopup(Component invoker, int x, int y) {
        NumberSelectorPopup popup = new NumberSelectorPopup(new NumberSelectorPopup.NumberSelectionListener() {
        	public void numberSelected(int number) {
        	    if (cell.trySetValue(number)) {
        	        setText(String.valueOf(number));

        	        if (validator != null) {
        	            isValueCorrect = validator.isCorrectValue(cell.getRow(), cell.getCol(), number);
        	        } else {
        	            isValueCorrect = true;
        	        }

        	        repaintVisualState();
        	    }
        	}
        });

        popup.show(this, this.getWidth(), this.getHeight());
    }

    private void repaintVisualState() {
        if (selected) {
            setBackground(COLOR_SELECCIONADO);
        } else if (highlightRowCol) {
            setBackground(COLOR_FILA_COLUMNA);
        } else {
            setBackground(cell.isEditable() ? COLOR_EDITABLE : COLOR_NO_EDITABLE);
        }

        if (!isValueCorrect) {
            setForeground(COLOR_TEXTO_INCORRECTO);
        } else if (sameValue && !selected) {
            setForeground(COLOR_TEXTO_VALOR_IGUAL);
        } else {
            setForeground(cell.isEditable() ? COLOR_TEXTO_EDITABLE : COLOR_TEXTO_NORMAL);
        }
    }
}
