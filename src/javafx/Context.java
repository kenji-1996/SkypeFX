package javafx;

import com.skype.Skype;
import com.skype.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import misc.Functions;
import misc.Threads;
import objects.Contact;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by kenji on 13/04/2016.
 */
public class Context {

    public static MenuItem messageUserItem(TableView<Contact> table) {
        MenuItem message = new MenuItem("Message user");
        ImageView messageIcon = new ImageView("/images/message.png");
        message.setGraphic(messageIcon);
        message.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Dialog dialog = new Dialog();
                    dialog.setTitle("Message User");
                    ImageView messageIcon = new ImageView("/images/message.png");
                    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("images/SKYPE.png"));
                    dialog.setHeaderText("Please enter the message and amount \n" +
                            "of times to send (or click title bar 'X' for cancel).");
                    dialog.setResizable(true);

                    Label label1 = new Label("Message: ");
                    Label label2 = new Label("Amount: ");
                    Label label3 = new Label("Delay: ");
                    TextField text1 = new TextField("Hello @name");
                    TextField text2 = new TextField("1");
                    TextField text3 = new TextField("0");

                    GridPane grid = new GridPane();
                    grid.add(label1, 1, 1);
                    grid.add(text1, 2, 1);
                    grid.add(label2, 1, 2);
                    grid.add(text2, 2, 2);
                    grid.add(label3,1,3);
                    grid.add(text3,2,3);
                    dialog.getDialogPane().setContent(grid);

                    ButtonType buttonTypeOk = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                    dialog.setResultConverter(new Callback<ButtonType, ArrayList>() {
                        @Override
                        public ArrayList call(ButtonType b) {
                            ArrayList temp = new ArrayList();
                            if (b == buttonTypeOk) {
                                temp.add(text1.getText());
                                temp.add(text2.getText());
                                temp.add(text3.getText());
                                return temp;
                            }

                            return null;
                        }
                    });
                    Optional<ArrayList> result = dialog.showAndWait();
                    String message = null;
                    int amount = 0;
                    String delay = null;
                    if (result.isPresent()) {
                        message = result.get().get(0).toString();
                        amount = Integer.parseInt(result.get().get(1).toString());
                        delay = result.get().get(2).toString();
                    }
                    if(table.getSelectionModel().getSelectedCells().size() > 1) {
                        Threads.messageUser(table.getSelectionModel().getSelectedItems(),message,amount,delay);
                    }else{
                        //new Threads.messageUserThread(table.getSelectionModel().getSelectedItem().getId(),message,delay,amount).start();
                        Threads.messageUser(table.getSelectionModel().getSelectedItem().getId(),message,amount,delay);
                    }
                } catch (Exception ex) {  }
            }
        });
        return message;
    }

    public static MenuItem addContact() {
        MenuItem addContact = new MenuItem("Add contact");
        ImageView addContactIcon = new ImageView("/images/phone.png");
        addContact.setGraphic(addContactIcon);
        addContact.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Skype.getContactList().addFriend(Functions.dialogReturn("Add contact", "add new contact"),"I would like to add you as a friend.");
                } catch (Exception ex) {ex.printStackTrace();}
            }
        });
        return addContact;
    }

    public static MenuItem callUserItem(TableView<Contact> table) {
        MenuItem call = new MenuItem("Call user");
        ImageView messageIcon = new ImageView("/images/phone.png");
        call.setGraphic(messageIcon);
        call.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(table.getSelectionModel().getSelectedCells().size() > 1) {
                        Functions.alertDialog("Call User","Can only call 1 user at a time.");
                    }else {
                        int amount = Integer.parseInt(Functions.dialogReturn("Call user", "Amount of times to call"));
                        Functions.alertDialog("Skype API Problem", "Depending on your skype version call spam wont work.");
                        Threads.callSpam(table.getSelectionModel().getSelectedItem().getId(),amount);
                    }

                } catch (Exception ex) { System.out.println("Only whole numbers are accepted"); }
            }
        });
        return call;
    }

    public static MenuItem blockUserItem(TableView<Contact> table, ObservableList<Contact> data) {
        MenuItem block = new MenuItem("Block user");
        ImageView blockIcon = new ImageView("/images/block-icon.png");
        block.setGraphic(blockIcon);
        block.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(table.getSelectionModel().getSelectedCells().size() > 1) {
                        for(Contact c: table.getSelectionModel().getSelectedItems()) {
                            User u = Skype.getUser(c.getId());
                            u.setBlocked(true);
                        }
                        data.removeAll(data);
                        table.setItems(Functions.returnContacts(SkypeFX.OFFLINE_NORMAL));
                    }else {
                        Skype.getUser(table.getSelectionModel().getSelectedItem().getId()).setBlocked(true);
                        data.removeAll(data);
                        table.setItems(Functions.returnContacts(SkypeFX.OFFLINE_NORMAL));
                    }
                } catch(Exception ex) { ex.printStackTrace(); }
            }
        });
        return block;
    }

    public static MenuItem unblockUserItem(TableView<Contact> table, ObservableList<Contact> data) {
        MenuItem unblock = new MenuItem("Unblock user");
        ImageView unblockIcon = new ImageView("/images/tick.png");
        unblock.setGraphic(unblockIcon);
        unblock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(table.getSelectionModel().getSelectedCells().size() > 1) {
                        for(Contact c: table.getSelectionModel().getSelectedItems()) {
                            User u = Skype.getUser(c.getId());
                            u.setBlocked(false);
                        }
                        data.removeAll(data);
                        table.setItems(Functions.returnContacts(SkypeFX.BLOCKED_NORMAL));
                    }else {
                        Skype.getUser(table.getSelectionModel().getSelectedItem().getId()).setBlocked(false);
                        data.removeAll(data);
                        table.setItems(Functions.returnContacts(SkypeFX.BLOCKED_NORMAL));
                    }
                } catch(Exception ex) { ex.printStackTrace(); }
            }
        });
        return unblock;
    }
}
