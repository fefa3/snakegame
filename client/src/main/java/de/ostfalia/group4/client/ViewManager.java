package de.ostfalia.group4.client;

// Singleton
public class ViewManager {
    private static ViewManager instance;
    public static ViewManager getInstance() {
        if (null == instance) {
            instance = new ViewManager();
        }
        return instance;
    }
    // Konstruktor: Nur ViewManager kann ViewManager instanziieren.
    private ViewManager (){

    }
}

