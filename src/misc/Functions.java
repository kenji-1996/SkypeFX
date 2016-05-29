package misc;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import objects.Contact;
import com.skype.Skype;
import com.skype.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by kenji on 4/04/2016.
 */
public class Functions {

    public static ArrayList names = new ArrayList();

    public static ObservableList<Contact> returnContacts(int type) {
        ObservableList<Contact> observableList = null;
        ArrayList<Contact> userList = new ArrayList<>();
        try {
            switch(type) {
                case 0://show Offline
                    for (User t : Skype.getContactList().getAllFriends()) {
                        //if(t.isAuthorized() && !t.isBlocked()) {
                            Contact c = new Contact(t.getStatus().toString(),t.getId(),t.getDisplayName(),t.getFullName());
                            userList.add(c);
                        //}
                    }
                    break;
                case 1://Show both offline and blocked
                    for (User t : Skype.getContactList().getAllFriends()) {
                        if(t.isAuthorized()) {
                            Contact c = new Contact(t.getStatus().toString(),t.getId(),t.getDisplayName(),t.getFullName());
                            userList.add(c);
                        }
                    }
                    break;
                case 2://Show blocked
                    for (User t : Skype.getContactList().getAllFriends()) {
                        if(t.isAuthorized() && !t.getStatus().toString().equalsIgnoreCase("offline")) {
                            Contact c = new Contact(t.getStatus().toString(),t.getId(),t.getDisplayName(),t.getFullName());
                            userList.add(c);
                        }
                    }
                    break;
                case 3://Add normal only
                    for (User t : Skype.getContactList().getAllFriends()) {
                        if(t.isAuthorized() && !t.getStatus().toString().equalsIgnoreCase("offline") && !t.isBlocked()) {
                            Contact c = new Contact(t.getStatus().toString(),t.getId(),t.getDisplayName(),t.getFullName());
                            userList.add(c);
                        }
                    }
                    break;
            }
        } catch (Exception ex) {  }
        observableList = FXCollections.observableArrayList(userList);
        return observableList;
    }

    public static void buildNameArray(String input) {
        names.add(input);
    }

    public static String formatText(String input, User u) throws Exception{
        String result = input;
        try { input = input.replace("@id",u.getId()); } catch (Exception ex) {  }
        try { input = input.replace("@display",u.getDisplayName()); } catch (Exception ex) {  }
        try { input = input.replace("@name",u.getFullName()); } catch (Exception ex) {  }
        try { input = input.replace("@country", u.getCountry()); } catch (Exception ex) {  }
        try { input = input.replace("@birthday",u.getBirthDay().toString()); } catch (Exception ex) {  }
        return input;
    }

    public static String dialogReturn(String title, String context) {
        String returnValue = null;
        TextInputDialog delayDialog = new TextInputDialog(context);
        delayDialog.setTitle(title);
        Stage stage = (Stage) delayDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("images/SKYPE.png".toString()));
        Optional<String> delay = delayDialog.showAndWait();
        if(delay.isPresent()) {
            returnValue = delay.get();
        }
        return returnValue;
    }

    public static void alertDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,content);
        alert.setTitle(title);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("images/SKYPE.png"));
        Optional<ButtonType> result = alert.showAndWait();
    }
}
