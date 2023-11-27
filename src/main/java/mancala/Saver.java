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
   /* default */ private Serializable loadedObject = null;
    private static String folderName = "assets";

    /**
     * Saves the object to a file.
     *
     * @param Serializable The serializable object.
     * @param String The file name.
     */
    public void saveObject(Serializable toSave, String filename) {
        final Path currentDir = Paths.get(System.getProperty("user.dir"));
        final Path assetsFolderPath = currentDir.resolve(folderName);

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
       //     System.out.println("Game saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the object from a file.
     *
     * @param String The file name.
     * @return The serializable object.
     */
    public Serializable loadObject(String filename) {
        final Path currentDir = Paths.get(System.getProperty("user.dir"));
        final Path assetsFolderPath = currentDir.resolve(folderName);
        final Path filePath = assetsFolderPath.resolve(filename);

        // check if the folder exists
        if (Files.exists(filePath)) {
            try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filePath.toString()))) {
                loadedObject = (Serializable) objectIn.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return loadedObject;
    }
}
