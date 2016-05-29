package javafx;

import javafx.collections.FXCollections;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import misc.Functions;
import objects.Animation;
import objects.Contact;
import com.skype.Skype;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SkypeFX extends Application {

    public static final int   OFFLINE_NORMAL = 0;
    public static final int   OFFLINE_BLOCKED = 1;
    public static final int   BLOCKED_NORMAL = 2;
    public static final int   NORMAL = 3;

    public static final int   userInput = 0;
    public static final int   declineCall = 1;
    public static final int   acceptCall = 2;

    public static int currentCallSettings = 0;

    public static Animation nameAnimation = new Animation();
    public static Animation moodAnimation = new Animation();
    public static Animation screenAnimation = new Animation();

    private TableView<Contact> table = new TableView<Contact>();
    private final ObservableList<Contact> data = Functions.returnContacts(BLOCKED_NORMAL);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Skype.setDaemon(true);
        Scene scene = new Scene(new Group());
        try { Skype.setDebug(true);stage.setTitle("SkypeFX - " + Skype.getProfile().getId()); } catch (Exception ex) {  }
        stage.setWidth(600);
        stage.setHeight(500);
        Image imageIcon = new Image("/images/SKYPE.png");
        stage.getIcons().add(imageIcon);
        table.setEditable(true);

        //Menu bar with options
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        Menu menuActions = new Menu("Settings");
        RadioMenuItem showOfflineItem = MenuStrip.showOfflineItem(table,data);
        MenuItem endThreads = MenuStrip.endThreads();
        menuActions.getItems().addAll(showOfflineItem,endThreads);
        Menu menuProfile = new Menu("Profile");
        //Name array
        Menu nameSettings = new Menu("Name options");
        ImageView nameIcon = new ImageView("/images/name.png");
        nameSettings.setGraphic(nameIcon);
        Menu anmiationDialog = new Menu("Name animation");
        ImageView animationIcon = new ImageView("/images/profile.png");
        anmiationDialog.setGraphic(animationIcon);
        MenuItem setNameDialog = MenuStrip.setNameItem();
        RadioMenuItem animationToggle = MenuStrip.toggleNameAnimationItem();
        MenuItem saveNames = new MenuItem("Save curent name settings");
        ImageView saveNamesIcon = new ImageView("/images/profile1.png");
        saveNames.setGraphic(saveNamesIcon);
        nameSettings.getItems().addAll(setNameDialog,anmiationDialog);
        MenuItem setDelay = MenuStrip.setNameDelay();
        MenuItem setNames = MenuStrip.setNameArray();
        anmiationDialog.getItems().addAll(animationToggle,setDelay,setNames,saveNames);
        menuProfile.getItems().add(nameSettings);
        //Mood array.
        Menu moodSettings = new Menu("Mood options");
        ImageView moodIcon = new ImageView("/images/name.png");
        moodSettings.setGraphic(moodIcon);
        Menu moodAnimation = new Menu("Mood animation");
        ImageView moodAnimationIcon = new ImageView("/images/profile.png");
        moodAnimation.setGraphic(moodAnimationIcon);
        MenuItem setMood = MenuStrip.setMoodItem();
        MenuItem setMoodDelay = MenuStrip.setMoodDelay();
        RadioMenuItem moodAnimationToggle = MenuStrip.toggleMoodAnimation();
        MenuItem setMoods = MenuStrip.setMoodArray();
        MenuItem saveMoods = new MenuItem("Save current mood settings");
        ImageView saveMoodIcon = new ImageView("/images/profile1.png");
        saveMoods.setGraphic(saveMoodIcon);
        moodAnimation.getItems().addAll(moodAnimationToggle,setMoodDelay,setMoods,saveMoods);
        moodSettings.getItems().addAll(setMood,moodAnimation);
        menuProfile.getItems().add(moodSettings);
        //Set screen
        Menu screenShare = new Menu("Screen Share");
        ImageView screenShareIcon = new ImageView("/images/screen.png");
        screenShare.setGraphic(screenShareIcon);
        RadioMenuItem screenShareToggle = MenuStrip.toggleScreenShare();
        MenuItem setScreenShareDelay = MenuStrip.setScreenDelay();
        screenShare.getItems().addAll(screenShareToggle,setScreenShareDelay);
        menuProfile.getItems().add(screenShare);
        //Call options
        MenuItem callOptions = MenuStrip.callOptions();
        menuProfile.getItems().add(callOptions);
        //Populate menu bar
        menuBar.getMenus().addAll(menuActions,menuProfile);

        //Context menu
        ContextMenu contextMenu = new ContextMenu();
        Menu interactMenu = new Menu("Skype interaction");
        ImageView interactionIcon = new ImageView("/images/interaction.png");
        interactMenu.setGraphic(interactionIcon);
        Menu contactMenu = new Menu("Contact");
        ImageView contactIcon = new ImageView("/images/contact.png");
        contactMenu.setGraphic(contactIcon);
        MenuItem messageMenuItem = Context.messageUserItem(table);
        MenuItem callMenuItem = Context.callUserItem(table);
        MenuItem blockMenuItem = Context.blockUserItem(table,data);
        MenuItem unblockMenuItem = Context.unblockUserItem(table,data);
        MenuItem addContactItem = Context.addContact();
        interactMenu.getItems().addAll(addContactItem,unblockMenuItem,blockMenuItem);
        contactMenu.getItems().addAll(messageMenuItem, callMenuItem);
        contextMenu.getItems().addAll(contactMenu,interactMenu);

        //Table settings
        Tables.getFriendsTable(contextMenu,scene,table,data);

        ((Group) scene.getRoot()).getChildren().addAll(menuBar);

        //setting scene and showing result
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }



}