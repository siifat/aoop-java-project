package lms.util;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ShowDesktopNotification {
    public static void show(String title, String content) {
        Notifications notifications = Notifications.create();
        notifications.title(title);
        notifications.text(content);
        notifications.hideAfter(Duration.seconds(5));
        notifications.show();
    }
}
