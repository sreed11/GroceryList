package tarleton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MasterList {

    private ListView<String> mastList = new ListView<String>();
    private ObservableList<String> mListContents = FXCollections.observableArrayList();
    private ObservableList<String> mListOptions = FXCollections.observableArrayList();
    private FileReader file;
    private BufferedReader read;
    private String readLine;
    String favLoc = new File(".").getAbsolutePath();
    
    
    static FavoritesList fList = new FavoritesList();

    MasterList() {
    }

    public ListView<String> setList(Number selected) throws IOException {
        int selectedInt = selected.intValue();
        switch (selectedInt) {
            case 0:
                mListContents = getLists("All");
                break;
            case 1:
                mListContents = getLists("Favorites");
                break;
            case 2:
                mListContents = getLists("Meat");
                break;
            case 3:
                mListContents = getLists("Fruits");
                break;
            case 4:
                mListContents = getLists("Vegetables");
                break;
            case 5:
                mListContents = getLists("Condiments");
                break;
            case 6:
                mListContents = getLists("Cheeses");
                break;
            case 7:
                mListContents = getLists("Dairy");
                break;
            case 8:
                mListContents = getLists("Breads");
                break;
            case 9:
                mListContents = getLists("Snacks");
                break;
            case 10:
                mListContents = getLists("Canned");
                break;
            case 11:
                mListContents = getLists("SeaFood");
                break;
            case 12:
                mListContents = getLists("Frozen");
                break;
            case 13:
                mListContents = getLists("Personal");
                break;
            case 14:
                mListContents = getLists("Cleaning");
                break;
            case 15:
                mListContents = getLists("Office");
                break;
            case 16:
                mListContents = getLists("Medicine");
                break;
            case 17:
                mListContents = getLists("Carcinogen");
                break;
            case 18:
                mListContents = getLists("Kitchen");
                break;
            case 19:
                mListContents = getLists("Pets");
                break;
            case 20:
                mListContents = getLists("Baby");
                break;
            case 21:
                mListContents = getLists("Beverage");
                break;
            case 22:
                mListContents = getLists("Spices");
                break;
            case 23:
                mListContents = getLists("Other");
                break;
        }
        return getList();
    }

    public ListView<String> getList() {
        mastList.setItems(mListContents.sorted());
        return mastList;
    }
    
    public void addFavorite(String item) throws IOException {
        fList.addItem(item);
    }
    
    public void removeFavorite(ObservableList<String> item, int size) throws IOException {
        fList.removeItem(item, size);
        setList(1);
    }

    public ObservableList<String> getListOptions() throws FileNotFoundException, IOException {
        try {
        file = new FileReader(favLoc + "\\Categories.txt");
        read = new BufferedReader(file);
        while ((readLine = read.readLine()) != null) {
            mListOptions.add(readLine);
        }
        file.close();
        read.close();
        } catch (FileNotFoundException e) {
            DirectoryChooser findFiles = new DirectoryChooser();
            findFiles.setTitle("Please Select Directory That Holds Downloaded .txt Files");
            findFiles.setInitialDirectory(new File("."));
            FlowPane p = new FlowPane();
            Scene scene = new Scene(p, 300, 300);
            Stage test = new Stage();
            test.setScene(scene);
            File selectedFile = findFiles.showDialog(test);
            favLoc = selectedFile.getAbsolutePath();
            getListOptions();
        } catch (IOException e) {
            System.out.println("error!");
        }
        return mListOptions;
    }
    
    public ObservableList<String> getLists(String cat) throws FileNotFoundException, IOException {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
        file = new FileReader(favLoc + "\\" + cat + ".txt");
        read = new BufferedReader(file);
        while ((readLine = read.readLine()) != null) {
            list.add(readLine);
        }
        file.close();
        read.close();
        } catch (FileNotFoundException e) {
           DirectoryChooser findFiles = new DirectoryChooser();
            findFiles.setTitle("Please Select Directory That Holds Downloaded .txt Files");
            findFiles.setInitialDirectory(new File("."));
            FlowPane p = new FlowPane();
            Scene scene = new Scene(p, 300, 300);
            Stage test = new Stage();
            test.setScene(scene);
            File selectedFile = findFiles.showDialog(test);
            favLoc = selectedFile.getAbsolutePath();
            return getLists(cat);
        } catch (IOException e) {
            System.out.println("error!");
        }
        return list;
    }
}
