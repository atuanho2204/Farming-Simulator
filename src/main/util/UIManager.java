package main.util;

import main.util.customEvents.ForceUIUpdate;
import main.util.customEvents.ForceUIUpdateListener;
import java.util.ArrayList;

public class UIManager {
    private static UIManager instance = null;
    private ArrayList<ForceUIUpdateListener> listeners;

    private UIManager() {
        this.listeners = new ArrayList<>();
    }

    public static UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    public void addListener(ForceUIUpdateListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ForceUIUpdateListener listener) {
        this.listeners.remove(listener);
    }

    public void pushUIUpdate() {
        try {
            int nullListenerCount = 0;
            ForceUIUpdate update = new ForceUIUpdate();
            for (ForceUIUpdateListener listener : instance.listeners) {
                if (listener == null) {
                    nullListenerCount++;
                    System.out.println("We have a null UI listener...removing it");
                } else {
                    listener.handleForcedUIUpdate(update);
                }
            }
            for (int i = 0; i < nullListenerCount; i++) {
                instance.listeners.remove(null);
            }
        } catch (Exception e) {
            System.out.println("Error forcing UI update: " + e.getMessage());
        }
    }
}
