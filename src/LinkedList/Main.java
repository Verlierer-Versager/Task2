package LinkedList;

import ru.vsu.cs.util.SwingUtils;

import java.text.ParseException;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MyForm().setVisible(true);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
