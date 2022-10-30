package application.persistence;

import application.User;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.util.HashSet;

public class UserSerialize implements UserPersistence, ObjectSerialize
{
    // Path of save folder in documents, e.g., (For Windows) "../My Documents/FlashcardManager/saves/"
    private final String saveFolder = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()
            .concat(File.separatorChar + "FlashcardManager" + File.separatorChar + "saves");

    /**
     * Stores the userList in a "saves" folder in a user's document folder through serialization.
     * Creates a "saves" folder and "UserList.txt" file if none are found.
     * @param userList HashSet<User> userList to be stored by serializing
     */
    @Override
    public void save(HashSet<User> userList)
    {
        // Point to the userList.txt file in the save folder and if it doesn't exist create it
        File saveFile = new File(saveFolder.concat(File.separatorChar + "userList.txt"));
        if (!saveFile.exists())
        {
            createSaveFile();
        }

        try
        {
            // Write the HashSet<User> into the userList.txt file
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(saveFile.toPath()));
            outputStream.writeObject(userList);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Checks if a new save file can be created. If successful, indicates there is no saved userList.
     * The method will then return an empty list. Otherwise, read from the byte stream if it exists in the file.
     * @return a HashSet<User> that contains a list of User objects
     */
    @Override
    public HashSet<User> retrieve()
    {
        // If the creation of a save file in the save folder was successful,
        // that indicates there is no saved data. Therefore, return an empty userList.
        if (createSaveFile())
        {
            return new HashSet<>();
        }

        // Point to a userList text file that should exist (createSaveFile() returned false)
        File saveFile = new File(saveFolder.concat(File.separatorChar + "userList.txt"));

        // Double-check the existence of the save file and confirm it actually has data
        if (saveFile.exists() && saveFile.length() != 0)
        {
            try
            {
                // Read the data from the file and return the HashSet<User> userList data
                ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(saveFile.toPath()));
                return (HashSet<User>) inputStream.readObject();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return new HashSet<>();
    }

    /**
     * Creates a Folder/File structure in the Application package if none is found
     * Saves to ../My Documents/FlashcardManager/saves/userList.txt. Does not overwrite.
     * @return true if file was created; false if file already exists
     */
    @Override
    public boolean createSaveFile()
    {
        // Attempt to create the directory "FlashcardManager/saves"
        // Cannot combine with .createNewFile statement because method creates files instead
        File file = new File(saveFolder);
        file.mkdirs();

        // Adjust file pointer to the actual .txt file (Which stores the userList database)
        file = new File(saveFolder.concat(File.separatorChar + "userList.txt"));

        // Return true if userList.txt was created through the createNewFile() method
        boolean success = false;

        try
        {
            success = file.createNewFile();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return success;
    }
}
