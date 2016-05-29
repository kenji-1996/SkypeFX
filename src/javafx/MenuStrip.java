package javafx;

import com.skype.Skype;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import misc.Functions;
import misc.Threads;
import objects.Contact;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by kenji on 13/04/2016.
 */
public class MenuStrip {

    public static RadioMenuItem showOfflineItem(TableView<Contact> table, ObservableList<Contact> data) {
        RadioMenuItem showOffline = new RadioMenuItem("Show offline");
        ImageView toggleIcon = new ImageView("/images/toggle.png");
        showOffline.setGraphic(toggleIcon);
        showOffline.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if(showOffline.isSelected()) {
                    data.removeAll(data);
                    table.setItems(Functions.returnContacts(SkypeFX.OFFLINE_NORMAL));
                }else{
                    data.removeAll(data);
                    table.setItems(Functions.returnContacts(SkypeFX.BLOCKED_NORMAL));
                }
            }
        });
        return showOffline;
    }

    public static MenuItem endThreads() {
        MenuItem endThreads = new MenuItem("End running functions");
        ImageView crossIcon = new ImageView("/images/cross.png");
        endThreads.setGraphic(crossIcon);
        endThreads.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                System.out.println("attempted to stop thread");
                Threads.stopFunction = true;
            }
        });
        return endThreads;
    }

    public static MenuItem setNameArray() {
        MenuItem setNames = new MenuItem("Set name array");
        ImageView setArray = new ImageView("/images/pencil.png");
        setNames.setGraphic(setArray);
        setNames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog arrayDialog = new TextInputDialog("Set name here");
                Stage stage = (Stage) arrayDialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("images/SKYPE.png"));
                arrayDialog.setTitle("Name modifier - Array");
                arrayDialog.setContentText("Enter 'done' when finished.");
                Optional<String> nameResult = arrayDialog.showAndWait();
                ArrayList namesList = new ArrayList();
                namesList.clear();
                if(nameResult.isPresent()) {
                    while (!(nameResult.get().equalsIgnoreCase("done"))) {
                        System.out.println(nameResult.get());
                        namesList.add(nameResult.get());
                        nameResult = arrayDialog.showAndWait();
                    }
                }
                SkypeFX.nameAnimation.setInputs(namesList);
            }
        });
        return setNames;
    }

    public static MenuItem setNameItem() {
        MenuItem setNameItem = new MenuItem("Set name");
        ImageView setNameIcon = new ImageView("/images/pencil.png");
        setNameItem.setGraphic(setNameIcon);
        setNameItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Skype.getProfile().setFullName(Functions.dialogReturn("Name setter","New name")); } catch (Exception ex) {  }
            }
        });
        return setNameItem;
    }

    public static MenuItem setNameDelay() {
        MenuItem setDelay = new MenuItem("Set animation delay");
        ImageView delayIcon = new ImageView("/images/delay.png");
        setDelay.setGraphic(delayIcon);
        setDelay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SkypeFX.nameAnimation.setDelay(Long.parseLong(Functions.dialogReturn("Name Settings","Enter the delay in seconds for name animation")));
            }
        });
        return setDelay;
    }

    public static RadioMenuItem toggleNameAnimationItem() {
        RadioMenuItem animationToggle = new RadioMenuItem("Enable name animation");
        ImageView setToggle = new ImageView("/images/toggle.png");
        animationToggle.setGraphic(setToggle);
        animationToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(animationToggle.isSelected()) {
                    if(SkypeFX.nameAnimation.getInputs() != null) {
                        SkypeFX.nameAnimation.setEnabled(true);
                        Threads.animateName();
                    }else{
                        Functions.alertDialog("Unable to process","Can only enable if names have been set.");
                        animationToggle.setSelected(false);
                    }
                }else{
                    SkypeFX.nameAnimation.setEnabled(false);
                }
            }
        });
        return animationToggle;
    }

    public static MenuItem setMoodItem() {
        MenuItem setMood = new MenuItem("Set mood");
        ImageView setMoodIcon = new ImageView("/images/pencil.png");
        setMood.setGraphic(setMoodIcon);
        setMood.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Skype.getProfile().setMoodMessage(Functions.dialogReturn("Mood setter","New mood")); } catch (Exception ex) {  }
            }
        });
        return setMood;
    }

    public static RadioMenuItem toggleMoodAnimation() {
        RadioMenuItem animationToggle = new RadioMenuItem("Enable mood animation");
        ImageView setToggle = new ImageView("/images/toggle.png");
        animationToggle.setGraphic(setToggle);
        animationToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(animationToggle.isSelected()) {
                    if(SkypeFX.moodAnimation.getInputs() != null) {
                        SkypeFX.moodAnimation.setEnabled(true);
                        Threads.animateMood();
                    }else{
                        Functions.alertDialog("Unable to process","Can only enable if mood(s) have been set.");
                        animationToggle.setSelected(false);
                    }
                }else{
                    SkypeFX.moodAnimation.setEnabled(false);
                }
            }
        });
        return animationToggle;
    }

    public static MenuItem setMoodDelay() {
        MenuItem setDelay = new MenuItem("Set animation delay");
        ImageView delayIcon = new ImageView("/images/delay.png");
        setDelay.setGraphic(delayIcon);
        setDelay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SkypeFX.moodAnimation.setDelay(Long.parseLong(Functions.dialogReturn("Mood Settings","Enter the delay in seconds for mood animation")));
            }
        });
        return setDelay;
    }

    public static MenuItem setMoodArray() {
        MenuItem setNames = new MenuItem("Set mood array");
        ImageView setArray = new ImageView("/images/pencil.png");
        setNames.setGraphic(setArray);
        setNames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog arrayDialog = new TextInputDialog("Set mood here");
                Stage stage = (Stage) arrayDialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("images/SKYPE.png"));
                arrayDialog.setTitle("Mood modifier - Array");
                arrayDialog.setContentText("Enter 'done' when finished.");
                Optional<String> nameResult = arrayDialog.showAndWait();
                ArrayList moodList = new ArrayList();
                moodList.clear();
                if(nameResult.isPresent()) {
                    while (!(nameResult.get().equalsIgnoreCase("done"))) {
                        System.out.println(nameResult.get());
                        moodList.add(nameResult.get());
                        nameResult = arrayDialog.showAndWait();
                    }
                }
                SkypeFX.moodAnimation.setInputs(moodList);
            }
        });
        return setNames;
    }

    public static RadioMenuItem toggleScreenShare() {
        RadioMenuItem screenShare = new RadioMenuItem("Toggle screen share");
        //Place holder
        ImageView screenShareIcon = new ImageView("/images/toggle.png");
        screenShare.setGraphic(screenShareIcon);
        screenShare.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(screenShare.isSelected()) {
                    if(SkypeFX.screenAnimation.getDelay() > 0) {
                        SkypeFX.screenAnimation.setEnabled(true);
                        //Start thread
                        Threads.animateScreen();
                    }else{
                        Functions.alertDialog("Unable to process","Can only enable if delay has been set.");
                        screenShare.setSelected(false);
                    }
                }else{
                    SkypeFX.screenAnimation.setEnabled(false);
                }
            }
        });
        return screenShare;
    }

    public static MenuItem setScreenDelay() {
        MenuItem setDelay = new MenuItem("Set screen delay");
        ImageView delayIcon = new ImageView("/images/delay.png");
        setDelay.setGraphic(delayIcon);
        setDelay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SkypeFX.screenAnimation.setDelay(Long.parseLong(Functions.dialogReturn("Screen Settings","Enter the delay in seconds for screen share")));
            }
        });
        return setDelay;
    }

    public static MenuItem callOptions() {
        MenuItem message = new MenuItem("Call Options");
        ImageView messageIcon = new ImageView("/images/phone.png");
        message.setGraphic(messageIcon);
        message.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Dialog dialog = new Dialog();
                    dialog.setTitle("Set default call options");
                    ImageView messageIcon = new ImageView("/images/phone.png");
                    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("images/SKYPE.png"));
                    dialog.setHeaderText("Select the combo box option \n" +
                            "you wish for skype to use.");
                    dialog.setResizable(true);
                    ObservableList<String> options =
                            FXCollections.observableArrayList(
                                    "User Input",
                                    "Auto Decline",
                                    "Auto Accept"
                            );
                    final ComboBox comboBox = new ComboBox(options);

                    GridPane grid = new GridPane();
                    grid.add(comboBox,1,1);
                    /*grid.add(label1, 1, 1);
                    grid.add(text1, 2, 1);
                    grid.add(label2, 1, 2);
                    grid.add(text2, 2, 2);
                    grid.add(label3,1,3);
                    grid.add(text3,2,3);*/
                    dialog.getDialogPane().setContent(grid);

                    ButtonType buttonTypeOk = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
                    dialog.setResultConverter(new Callback<ButtonType, ArrayList>() {
                        @Override
                        public ArrayList call(ButtonType b) {
                            ArrayList temp = new ArrayList();
                            if (b == buttonTypeOk) {
                                System.out.println((String)comboBox.getValue());
                                return temp;
                            }

                            return null;
                        }
                    });
                    Optional<String> result = dialog.showAndWait();
                    if(result.get().equalsIgnoreCase("user input")) {
                        SkypeFX.currentCallSettings = SkypeFX.userInput;
                    }else if(result.get().equalsIgnoreCase("auto decline")) {
                        SkypeFX.currentCallSettings = SkypeFX.declineCall;
                    }else if(result.get().equalsIgnoreCase("auto accept")) {
                        SkypeFX.currentCallSettings = SkypeFX.acceptCall;
                    }else{

                    }
                } catch (Exception ex) {  }
            }
        });
        return message;
    }
}
