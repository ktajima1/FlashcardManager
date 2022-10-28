package application;

import java.io.IOException;
import java.util.HashSet;

public interface UserPersistence
{
    void save(HashSet<User> user);
    HashSet<User> retrieve();
    boolean createSaveFile() throws IOException;
}
