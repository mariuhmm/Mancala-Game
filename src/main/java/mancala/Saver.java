package mancala;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Saver {
    public void saveObject(Serializable toSave, String filename) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("assets/" + filename))) {
            objectOut.writeObject(toSave);
            System.out.println("Game saved!");
        } catch (IOException e) {
            //e.printStackTrace(); 
        }
    }

    public Serializable loadObject(String filename) {
        Serializable loadedObject = null;
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("assets/" + filename))) {
            loadedObject = (Serializable) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
           // e.printStackTrace();
        }
        return loadedObject;
    }
}
