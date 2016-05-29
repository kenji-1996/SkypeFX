package misc;

import com.skype.*;
import javafx.SkypeFX;

/**
 * Created by kenji on 21/04/2016.
 */
public class Listeners {
    public void callListener() throws Exception {
        Skype.addCallMonitorListener(new CallMonitorListener() {
            @Override
            public void callMonitor(Call call, Call.Status status) throws SkypeException {
                switch (SkypeFX.currentCallSettings) {
                    case SkypeFX.declineCall:
                        call.finish();
                        break;
                }
            }
        });
    }
}
