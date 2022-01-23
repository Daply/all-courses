package org.dariaples.composition;

public class UserParser {

  public User parseUser(String userAsString) {
    String [] userProperties = userAsString.split(";");
    return new User(Long.parseLong(userProperties[0]), userProperties[1], userProperties[2]);
  }
}
