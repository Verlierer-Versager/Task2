package LinkedList;

import ru.vsu.cs.util.ArrayUtils;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;

public class MyForm extends JFrame {
    private JPanel panelMain;
    private JTable table;
    private JButton executeButton;
    private JButton writeToFileButton;
    private JButton readFromFileButton;
    private JButton addButton;
    private JButton deleteButton;
    private JLabel writeValue;
    private JTextField textValue;
    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    LinkedList list = new LinkedList();

    private void chooseFunctionForValue(boolean state) {
        String value = textValue.getText();
        if (value.matches("^[0-9]+|-[0-9]+")) {
            int element = Integer.parseInt(value);
            if (state) {
                list.addElement(element);
            } else {
                list.deleteFirstElement(element);
                if (list.getSize() != 0) {
                    JTableUtils.writeArrayToJTable(table, list.toArray());
                } else {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                }
            }
            JTableUtils.writeArrayToJTable(table, list.toArray());
        } else if (value.equals("")) {
            SwingUtils.showInfoMessageBox("Вы не ввели значение!");
        } else {
            SwingUtils.showInfoMessageBox("Вы ввели неподходящее значение!");
        }
    }

    public MyForm() throws ParseException {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (list != null) {
                    list.sort();
                    JTableUtils.writeArrayToJTable(table, list.toArray());
                }
            }
        });

        readFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[] array = ArrayUtils.readIntArrayFromFile(fileChooserOpen.getSelectedFile().getPath());
                        JTableUtils.writeArrayToJTable(table, array);
                        list.clearAll();
                        list.fromArray(JTableUtils.readIntArrayFromJTable(table));
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        writeToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[] array = JTableUtils.readIntArrayFromJTable(table);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeArrayToFile(file, array);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chooseFunctionForValue(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chooseFunctionForValue(false);
            }
        });
    }
}
