package com.g1t11.socialmagnet.data;

/**
 * Handles user authentication and session management.
 */
public class Session {
  /**
   * Currently logged-in user's name.
   * TODO Replace with <code>User</code> model.
   */
  private String username = null;

  /**
   * Get the currently logged-in user's name.
   * If no user is logged in, return "anonymous".
   */
  public String getUsername() {
    if (username == null)
      return "anonymous";
    return username;
  }

  /**
   * Verify login information against the database.
   * @throws WrongPasswordException Wrong password given.
   * @throws UserNotFoundException User does not exist in the database.
   */
  public void login() throws WrongPasswordException, UserNotFoundException {
  }
}
