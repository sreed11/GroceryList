package tarleton;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class PrintingTask extends SwingWorker<Object,Object> {
    private JTextArea list = new JTextArea();
    
    public PrintingTask(String data) {
        list.setText(data);
        this.execute();
    }
    
    public static void print(String list) {
        new PrintingTask(list);
    }
    
    protected Object doInBackground() {
        try {
            list.print(new MessageFormat(""), new MessageFormat(""), true, null, null, true);
                    }
        catch (PrinterException ex) {
            //Cant print
        }
        return null;
    }
}
