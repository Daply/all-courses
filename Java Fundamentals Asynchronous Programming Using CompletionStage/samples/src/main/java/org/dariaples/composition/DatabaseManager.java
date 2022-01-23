package org.dariaples.composition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Imitation of database manager to read users
 */
public class DatabaseManager {

  private final String path = "src//org//dariaples//composition//users.txt";
  private Set<Long> userIds = new HashSet<>();
  private final UserParser userParser = new UserParser();

  public DatabaseManager() {
    this.userIds = LongStream.range(1, 11).boxed().collect(Collectors.toSet());
  }

  public List<Long> getUserIds() {
    return userIds.stream().collect(Collectors.toList());
  }

  public List<User> selectAllUsers() {
    List<User> users = new ArrayList<>();
    try {
      File usersFile = new File(path);
      Scanner fileReader = new Scanner(usersFile);
      while (fileReader.hasNextLine()) {
        String data = fileReader.nextLine();
        users.add(userParser.parseUser(data));
      }
      fileReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return users;
  }

  public List<User> selectUsers(List<Long> ids) {
    List<User> users = new ArrayList<>();
    try {
      File usersFile = new File(path);
      Scanner fileReader = new Scanner(usersFile);
      while (fileReader.hasNextLine()) {
        String data = fileReader.nextLine();
        User user = userParser.parseUser(data);
        if (ids.contains(user.getId())) {
          users.add(user);
        }
      }
      fileReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return users;
  }

  public void insertUsers(List<User> users) {

  }
}
