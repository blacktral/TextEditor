package ua.dnu.prokhodets_k_v.laba4;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {


        JFrame frame = new JFrame("Редактор тексту");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null);

        int shortcutKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Вiдкрити");
        JMenuItem save = new JMenuItem("Зберегти");
        JMenuItem close = new JMenuItem("Закрити");
        file.add(open);
        file.add(save);
        file.addSeparator();
        file.add(close);
        menubar.add(file);

        JMenu edit = new JMenu("Правка");
        JMenuItem copy = new JMenuItem("Копіювати");
        JMenuItem paste = new JMenuItem("Вставити");
        JMenuItem cut = new JMenuItem("Вирізати");
        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.addSeparator();

        JMenu styleMenu = new JMenu("Стиль");
        JMenuItem boldStyle = new JMenuItem("Жирний");
        JMenuItem italicStyle = new JMenuItem("Курсив");
        JMenuItem underlineStyle = new JMenuItem("Підкреслений");
        JMenuItem normalStyle = new JMenuItem("Нормальний");
        styleMenu.add(boldStyle);
        styleMenu.add(italicStyle);
        styleMenu.add(underlineStyle);
        styleMenu.add(normalStyle);
        edit.add(styleMenu);
        menubar.add(edit);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));


        frame.setJMenuBar(menubar);

        JTextPane textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        StyledDocument doc = textPane.getStyledDocument();

        JToolBar toolBar = new JToolBar("Форматування");
        toolBar.setFloatable(false);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontBox = new JComboBox<>(fonts);
        fontBox.setMaximumSize(new Dimension(150, 25));
        toolBar.add(new JLabel("Шрифт: "));
        toolBar.add(fontBox);

        JTextField sizeField = new JTextField("14", 3);
        sizeField.setMaximumSize(new Dimension(50, 25));
        toolBar.add(new JLabel("  Розмір: "));
        toolBar.add(sizeField);

        JButton colorButton = new JButton("Колір");
        toolBar.add(Box.createHorizontalStrut(10));
        toolBar.add(colorButton);

        frame.add(toolBar, BorderLayout.NORTH);


        boldStyle.addActionListener(e -> {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setBold(attrs, true);
            textPane.setCharacterAttributes(attrs, false);
        });

        italicStyle.addActionListener(e -> {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setItalic(attrs, true);
            textPane.setCharacterAttributes(attrs, false);
        });

        underlineStyle.addActionListener(e -> {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setUnderline(attrs, true);
            textPane.setCharacterAttributes(attrs, false);
        });

        normalStyle.addActionListener(e -> {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setBold(attrs, false);
            StyleConstants.setItalic(attrs, false);
            StyleConstants.setUnderline(attrs, false);
            textPane.setCharacterAttributes(attrs, false);
        });

        copy.addActionListener(e -> textPane.copy());
        paste.addActionListener(e -> textPane.paste());
        cut.addActionListener(e -> textPane.cut());

        fontBox.addActionListener(e -> {
            String selectedFont = (String) fontBox.getSelectedItem();
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attrs, selectedFont);
            textPane.setCharacterAttributes(attrs, false);
        });

        sizeField.addActionListener(e -> {
            try {
                int newSize = Integer.parseInt(sizeField.getText().trim());
                if (newSize <= 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Розмір шрифту повинен бути більше нуля!",
                            "Помилка",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    Font currentFont = textPane.getFont();
                    textPane.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), newSize));
                }
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Введіть ціле число!",
                        "Помилка",
                        JOptionPane.ERROR_MESSAGE);
            }
        });


        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(frame, "Вибір кольору тексту", Color.BLACK);
            if (newColor != null) {
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                StyleConstants.setForeground(attrs, newColor);
                textPane.setCharacterAttributes(attrs, false);
            }
        });




        JFileChooser fc = new JFileChooser(new File("d:\\"));
        open.addActionListener(e -> {
            int result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
                    textPane.read(br, null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        save.addActionListener(e -> {
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                    fw.write(textPane.getText());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                }
            }
        });

        close.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }
}

