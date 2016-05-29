package misc;

import com.skype.Call;
import com.skype.Friend;
import com.skype.User;
import javafx.SkypeFX;
import javafx.collections.ObservableList;
import objects.Animation;
import com.skype.Skype;
import objects.Contact;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenji on 12/04/2016.
 */
public class Threads {
    public static boolean stopFunction = false;

    public static void animateMood() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(SkypeFX.moodAnimation.isEnabled()) {
                    for (int i = 0; i < SkypeFX.moodAnimation.getInputs().size(); i++) {
                        try { Skype.getProfile().setMoodMessage(SkypeFX.moodAnimation.getInputs().get(i).toString()); } catch (Exception ex) {  }
                        try { Thread.sleep(SkypeFX.moodAnimation.getDelay() * 1000); } catch (InterruptedException e) { System.out.println("got interrupted!"); }
                    }
                }
            }
        };
        new Thread(r).start();
    }

    public static void animateScreen() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(SkypeFX.screenAnimation.isEnabled()) {
                    try {
                        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                        BufferedImage capture = new Robot().createScreenCapture(screenRect);
                            /*File f = new File("currentScreen.bmp");
                            ImageIO.write(capture, "bmp", f);*/
                        Skype.getProfile().setAvatar(capture);
                    } catch (Exception ex) { ex.printStackTrace(); }
                    try { Thread.sleep(SkypeFX.screenAnimation.getDelay() * 1000); } catch (InterruptedException e) { System.out.println("got interrupted!"); }
                }
            }
        };
        new Thread(r).start();
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static void animateName() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(SkypeFX.nameAnimation.isEnabled()) {
                    for (int i = 0; i < SkypeFX.nameAnimation.getInputs().size(); i++) {
                        try { Skype.getProfile().setFullName(SkypeFX.nameAnimation.getInputs().get(i).toString()); } catch (Exception ex) {  }
                        try { Thread.sleep(SkypeFX.nameAnimation.getDelay() * 1000); } catch (InterruptedException e) { System.out.println("got interrupted!"); }
                    }
                }
            }
        };
        new Thread(r).start();
    }

    public static void messageUser(String id,String message,int amount,String delay) {
        System.out.println("Start thread");
        Runnable r = new Runnable() {
            public void run() {
                int i;
                try {
                    long longDelay = Long.parseLong(delay);
                    longDelay = longDelay * 1000;
                    User s = Skype.getUser(id);
                    System.out.println("Got user " + s.getId());
                    for(i = 0; i < amount; i++) {
                        Skype.chat(id).send(Functions.formatText(message,s));
                        if(longDelay > 0) {
                            try { Thread.sleep(longDelay); } catch (InterruptedException e) { System.out.println("got interrupted!"); }
                        }
                        if(stopFunction) {
                            i = amount;
                        }
                    }
                } catch (Exception ex) { ex.printStackTrace(); }

            }
        };
        new Thread(r).start();

    }

    public static void messageUser(ObservableList<Contact> list, String message, int amount,String delay) {
        Runnable r = new Runnable() {
            public void run() {
                int i;
                try {
                    long longDelay = Long.parseLong(delay);
                    longDelay = longDelay * 1000;
                    List<User> userList = new ArrayList<>();
                    for(Contact c: list) {
                        User s = Skype.getUser(c.getId());
                        userList.add(s);
                    }
                    for(User u : userList) {
                        for (i = 0; i < (stopFunction? 0 : amount); i++) {
                            Skype.chat(u.getId()).send(Functions.formatText(message,u));
                            if(longDelay > 0) {
                                try { Thread.sleep(longDelay); } catch (InterruptedException e) { System.out.println("got interrupted!"); }
                            }
                            if(stopFunction) {
                                i = amount;
                            }
                        }
                    }
                } catch (Exception ex) {  }
            }
        };
        new Thread(r).start();
    }

    public static void callSpam(String user,int amount) {
        Runnable r = new Runnable() {
            public void run() {
                try {for(Call z: Skype.getAllActiveCalls()) { z.cancel(); } } catch(Exception ex) { ex.printStackTrace(); }
                for(int i = 0; i < amount; i++) {

                    Call c = null;
                    try {
                        c = Skype.call(user);
                    } catch (Exception ex) { ex.printStackTrace();
                    }

                    try {
                        System.out.println(c.getStatus().toString());
                        //if(c.getStatus().toString())
                        if(c.getStatus().equals(Call.Status.RINGING)) {
                            try { Thread.sleep(1500); } catch (InterruptedException e) { System.out.println("got interrupted!");}
                            //c.finish();
                        }
                    } catch (Exception ex) { ex.printStackTrace();
                    }
                }
            }
        };
        new Thread(r).start();
    }
}
