package tarleton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class GroceryList {
    private ListView<String> grocList = new ListView<String>();
    private ObservableList<String> gListContents = FXCollections.observableArrayList();
    String priceGetter;
    private int x;
    private float gListTot = 0;
    
    GroceryList() {
        this.gListContents = FXCollections.observableArrayList();
    }
    
    public ListView<String> clearList() {
        gListContents.clear();
        return getList();
    }

    public ListView<String> addItem(ObservableList<String> item) {
        gListContents.addAll(item);
        return getList();
    }
    
    public ListView<String> removeItem(ObservableList<String> item) {
        gListContents.removeAll(item);
        return getList();
    }
    
    public ListView<String> getList() {        
        grocList.setItems(gListContents);
        return grocList;
    }
    
    public ObservableList<String> getListObs() {        
        return gListContents;
    }
    
    public float getTotal() {
        gListTot = 0;
        int listSize = gListContents.size();
        for (int y = 0; y<listSize; y++) {
            priceGetter = gListContents.get(y);
            x = 0;
            while (!priceGetter.substring(0, x).contains("$")) {
                x++;
            }
            gListTot = gListTot + Float.valueOf(priceGetter.substring(x, priceGetter.length()-1));
        }
        return gListTot;
    }
}
