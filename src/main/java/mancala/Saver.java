package mancala;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Saver {
    public void saveObject(Serializable toSave, String filename) {
        Path currentDirectory = Paths.get(System.getProperty("user.dir"));
        String folderName = "assets";
        Path assetsFolderPath = currentDirectory.resolve(folderName);

        // check if the assets folder exists, if not create it
        if (!Files.exists(assetsFolderPath)) {
            try {
                Files.createDirectories(assetsFolderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // save the object
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(assetsFolderPath.resolve(filename).toString()))) {
            objectOut.writeObject(toSave);
            System.out.println("Game saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Serializable loadObject(String filename) {
        Serializable loadedObject = null;
        Path currentDirectory = Paths.get(System.getProperty("user.dir"));
        String folderName = "assets";
        Path assetsFolderPath = currentDirectory.resolve(folderName);
        Path filePath = assetsFolderPath.resolve(filename);

        // check if the folder exists
        if (Files.exists(filePath)) {
            try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filePath.toString()))) {
                loadedObject = (Serializable) objectIn.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }

        return loadedObject;
    }
}
