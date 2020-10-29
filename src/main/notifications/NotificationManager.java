package main.notifications;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class NotificationManager {
    private static NotificationManager instance = new NotificationManager();
    private String lastNotification;
    private PropertyChangeSupport changeSupport;

    private NotificationManager() {
        lastNotification = "Welcome to the Totally accurate farming Simulator!";
        changeSupport = new PropertyChangeSupport(this);
    }

    public void subscribeToNotifications(PropertyChangeListener l) {
        changeSupport.addPropertyChangeListener(l);
        System.out.println(changeSupport.getPropertyChangeListeners()[0].getClass());
    }

    public void addNotification(String notification) {
        changeSupport.firePropertyChange(new PropertyChangeEvent(
                this, "lastNotification", lastNotification, notification));
        lastNotification = notification;
    }

    public static NotificationManager getInstance() {
        return instance;
    }
}
