package tarleton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ListProjectIteration2 extends Application {

    static GroceryList gcList;
    static ListView<String> grocList;
    static MasterList mList;
    static ListView<String> mastList;
    static TextField totalPriceResult = new TextField();
    static ObservableList<String> tempList = FXCollections.observableArrayList();
    HBox buttonBox = new HBox();
    GridPane listGPane = new GridPane();

    @Override
    public void start(Stage primaryStage) throws IOException, StringIndexOutOfBoundsException {
        totalPriceResult.setEditable(false);
//Setup GridPane for lists
        listGPane.setVgap(10);
        listGPane.setVgap(10);

//Get Master list and label
        listGPane.add(getLabel("Choose from Master List:"), 0, 2);
        mList = new MasterList();
        mastList = mList.getList();
        mastList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listGPane.add(mastList, 0, 3);

//Get Grocery List and label
        listGPane.add(getLabel("Current Grocery List:"), 1, 2);
        gcList = new GroceryList();
        grocList = gcList.getList();
        grocList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listGPane.add(grocList, 1, 3);
//Get categories list and action
        ChoiceBox categoriesCB = new ChoiceBox(mList.getListOptions());
        categoriesCB.getSelectionModel().selectFirst();
        buttonBox.getChildren().add(categoriesCB);
        mList.setList(0);
        //Set action for categories Choices
        categoriesCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                try {
                    mList.setList(new_value);
                } catch (IOException ex) {
                    Logger.getLogger(ListProjectIteration2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//Get add items to grocery list button and action
        getAddGrocBut();
//Get remove items from grocery button and action
        getRemGrocBut();

//Get add items to favorites button and action
        getAddFavBut();
//Get remove items from favorites button and action
        getRemFavBut();
//Get save list button and action
        getSaveListBut(primaryStage);
//Get load list button and action
        getLoadListBut(primaryStage);
//Get clear list button and action
        getClearListBut(primaryStage);
//Get print list button and ation
        getPrintListBut(primaryStage);
//Get total price text
        totalPriceResult.setText("Total price: $" + gcList.getTotal());
        listGPane.add(totalPriceResult, 0, 4);

//Set up interface
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setCenter(listGPane);
        root.setBottom(buttonBox);
        Scene scene = new Scene(root, 825, 400);
        //set title for GUI box
        primaryStage.setTitle("Grocery List Program");
        primaryStage.setScene(scene);
        //show stage created with scene and title loaded
        primaryStage.show();
    }

    private void updateGrocList() {
        grocList = gcList.getList();
        totalPriceResult.setText("Total price: $" + String.format("%.2f", gcList.getTotal()));
    }

    private Label getLabel(String text) {
        Label label = new Label();
        label.setText(text);
        return label;
    }

    private TextField getTextField(String text) {
        //create text area named text
        TextField textF = new TextField();
        textF.setText(text);
        return textF;
    }

    private Button getButton(String text) {
        Button butt1 = new Button();
        butt1.setText(text);
        return butt1;
    }

    private void getAddGrocBut() {
        Button addItemGButt = getButton("Add to List");
        buttonBox.getChildren().add(addItemGButt);

        //set add item button action
        addItemGButt.setOnAction((ActionEvent event) -> {
            if (!mastList.getSelectionModel().getSelectedItem().isEmpty()) {
                tempList = mastList.getSelectionModel().getSelectedItems();
                gcList.addItem(tempList);
                updateGrocList();
            }
        });
    }

    private void getRemGrocBut() {
        Button remItemGButt = getButton("Remove from List");
        buttonBox.getChildren().add(remItemGButt);
        //set remove item button action
        remItemGButt.setOnAction((event) -> {
            if (!grocList.getSelectionModel().getSelectedItem().isEmpty()) {
                tempList = grocList.getSelectionModel().getSelectedItems();
                gcList.removeItem(tempList);
                updateGrocList();
            }
        });
    }

    private void getAddFavBut() {
        Button addItemFButt = getButton("Add to Favorites");
        buttonBox.getChildren().add(addItemFButt);

        //set add item button action
        addItemFButt.setOnAction((ActionEvent event) -> {
            if (!mastList.getSelectionModel().getSelectedItem().isEmpty()) {
                try {
                    tempList = mastList.getSelectionModel().getSelectedItems();
                    for (String s : tempList) {
                        mList.addFavorite(s);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ListProjectIteration2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void getRemFavBut() {
        Button remItemFButt = getButton("Remove from Favorites");
        buttonBox.getChildren().add(remItemFButt);
        //set remove item button action
        remItemFButt.setOnAction((event) -> {
            tempList = mastList.getSelectionModel().getSelectedItems();
            try {
                mList.removeFavorite(tempList, tempList.size());
            } catch (IOException ex) {
                Logger.getLogger(ListProjectIteration2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void getSaveListBut(Stage primaryStage) {
        Button saveListButt = getButton("Save Grocery List");
        listGPane.add(saveListButt, 3, 3);
        saveListButt.setOnAction((event) -> {
            try {
                FileChooser grocListF = new FileChooser();
                grocListF.setInitialDirectory(new File("."));
                grocListF.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
                grocListF.setInitialFileName("*.txt");
                File file = grocListF.showSaveDialog(primaryStage);
                FileWriter filew = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(filew);
                PrintWriter pw = new PrintWriter(bw);

                gcList.getListObs().forEach(lis -> pw.println(lis));
                bw.close();
                pw.close();
                filew.close();
            } catch (IOException ex) {
                Logger.getLogger(ListProjectIteration2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    String readLine;
    private void getLoadListBut(Stage primaryStage) throws FileNotFoundException, IOException {
        Button loadListButt = getButton("Load Grocery List");
        listGPane.add(loadListButt, 4, 3);
        
        loadListButt.setOnAction((event) -> {
            ObservableList<String> tempList2 = FXCollections.observableArrayList();
            try {
                FileChooser grocListF = new FileChooser();
                grocListF.setInitialDirectory(new File("."));
                File file = grocListF.showOpenDialog(primaryStage);
                FileReader fw = new FileReader(file);
                BufferedReader read = new BufferedReader(fw);
                gcList.clearList();
                tempList2.clear();
                while ((readLine = read.readLine()) != null) {
                    tempList2.add(readLine);
                }
                gcList.addItem(tempList2);
                totalPriceResult.setText("Total price: $" + String.format("%.2f", gcList.getTotal()));
                
                fw.close();
                read.close();
            } catch (IOException ex) {
                System.out.println("Error");
                Logger.getLogger(ListProjectIteration2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void getClearListBut(Stage primaryStage) {
        Button clearListButt = getButton("Clear Grocery List");
        listGPane.add(clearListButt, 3, 4);
        gcList.getTotal();
        clearListButt.setOnAction((event) -> {
            gcList.clearList();
            totalPriceResult.setText("Total price: $" + String.format("%.2f", gcList.getTotal()));
        });
    }

    private void getPrintListBut(Stage primaryStage) {
        StringBuilder printList = new StringBuilder();
        Button printListButt = getButton("Print Grocery List");
        listGPane.add(printListButt, 4, 4);
        printListButt.setOnAction((event) -> {
            gcList.getListObs().forEach(lis -> printList.append(lis + "\n"));
            PrintingTask.print(printList.toString());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
