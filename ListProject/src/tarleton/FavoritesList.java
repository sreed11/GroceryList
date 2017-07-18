package tarleton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FavoritesList {

    private ListView<String> favList = new ListView<String>();
    private ObservableList<String> fListContents = FXCollections.observableArrayList();
    private FileReader file;
    private BufferedReader read;
    private String readLine;
    String favLoc = new File(".").getAbsolutePath();
    FavoritesList() {
        initia();
    }

    public void initia() {
    try {
        file = new FileReader(favLoc + "\\Favorites.txt");
        read = new BufferedReader(file);
        while ((readLine = read.readLine()) != null) {
            fListContents.add(readLine);
        }
        file.close();
        read.close();
        } catch (FileNotFoundException e) {
            DirectoryChooser findFiles = new DirectoryChooser();
            findFiles.setTitle("Please Select Directory That Holds Downloaded Favorites.txt File");
            findFiles.setInitialDirectory(new File("."));
            FlowPane p = new FlowPane();
            Scene scene = new Scene(p, 300, 300);
            Stage test = new Stage();
            test.setScene(scene);
            File selectedFile = findFiles.showDialog(test);
            favLoc = selectedFile.getAbsolutePath();
            initia();
        } catch (IOException e) {
            System.out.println("error!");
        }
    }
    
    public ListView<String> addItem(String item) throws IOException {
        if (!this.fListContents.contains(item)) {    
            try (FileWriter filew = new FileWriter(favLoc + "\\Favorites.txt", true);
            BufferedWriter bw = new BufferedWriter(filew);
            PrintWriter pw = new PrintWriter(bw);)
            {
                pw.println(item);
                bw.close();
                pw.close();
                filew.close();
            } catch (IOException e) {
                System.out.println("Error");
            }
            this.fListContents.add(item);
        }
        return getList();
    }
    
    public ListView<String> removeItem(ObservableList item, int size) throws FileNotFoundException, IOException {
        for (int x = 0; x < size; x++) {
            if (fListContents.contains(item.get(x).toString())) {
                fListContents.remove(item.get(x).toString());
            }
        }
        try (FileWriter filew = new FileWriter(favLoc + "\\Favorites.txt");
        BufferedWriter bw = new BufferedWriter(filew);
        PrintWriter pw = new PrintWriter(bw);)
        {
        filew.write("");
        fListContents.forEach(ite -> {pw.println(ite);});
            pw.close();
            bw.close();
            filew.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
        favList.setItems(fListContents);
        return favList;
    }
    
    public ListView<String> getList() {  
        String readLine;
        fListContents.clear();
        try {
        FileReader file = new FileReader(favLoc + "\\Favorites.txt");
        BufferedReader read = new BufferedReader(file);
        while ((readLine = read.readLine()) != null) {
            this.fListContents.add(readLine);
        }
        file.close();
        read.close();
        } catch (FileNotFoundException e) {
            System.out.println("error");
        } catch (IOException e) {
            System.out.println("error!");
        }
        this.favList.setItems(this.fListContents);
        return this.favList;
    }    
}
