package javafx;

import com.skype.Skype;
import com.skype.User;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import objects.Contact;

/**
 * Created by kenji on 13/04/2016.
 */
public class Tables {

    public static void getFriendsTable(ContextMenu contextMenu, Scene scene, TableView<Contact> table, ObservableList<Contact> data) {
        TableColumn statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("status"));
        statusCol.setCellFactory(new Callback<TableColumn<Contact, String>, TableCell<Contact, String>>() {
            @Override
            public TableCell<Contact, String> call(TableColumn<Contact, String> p) {
                return new TableCell<Contact, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        //System.out.println(item);
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        String current = getString();
                        try {
                            ImageView imageIcon = new ImageView("/images/" + current.toUpperCase() + ".png");
                            setGraphic(imageIcon);
                        } catch (Exception ex) {  }
                    }
                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };
            }
        });
        TableColumn idCol = new TableColumn("Username");
        idCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("id"));
        idCol.setCellFactory(new Callback<TableColumn<Contact, String>, TableCell<Contact, String>>() {
            @Override
            public TableCell<Contact, String> call(TableColumn<Contact, String> p) {
                return new TableCell<Contact, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        //System.out.println(item);
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        String current = getString();
                        try {
                            User u = Skype.getUser(current);

                            if(u.isBlocked()) { setTextFill(Color.RED); }else{ setTextFill(Color.BLACK); }
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }
                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };
            }
        });
        TableColumn nameCol = new TableColumn("Name");
        TableColumn subNameFull = new TableColumn("Current");
        subNameFull.setCellValueFactory(new PropertyValueFactory<Contact, String>("full"));
        TableColumn subNameDisplay = new TableColumn("Given");
        subNameDisplay.setCellValueFactory(new PropertyValueFactory<Contact, String>("display"));
        nameCol.getColumns().addAll(subNameDisplay,subNameFull);
        table.setItems(data);
        statusCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        idCol.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        subNameDisplay.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        subNameFull.prefWidthProperty().bind(nameCol.widthProperty().multiply(0.6));
        table.getColumns().addAll(statusCol, idCol, nameCol);
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefWidth(570);
        vbox.setPadding(new Insets(40, 0, 0, 10));//X1,y1,x2,Y2
        vbox.getChildren().addAll(table);
        table.setContextMenu(contextMenu);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
    }
}
