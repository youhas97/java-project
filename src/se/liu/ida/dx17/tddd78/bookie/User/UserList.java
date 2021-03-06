package se.liu.ida.dx17.tddd78.bookie.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Presents users in form of a list.
 */
public final class UserList
{
    /*
    An object is to be added to existingUsers every time a
    User is created. existingUsers has to be initialized only once
    so that it is a list of the currently existing users.
      */
    private static List<User> existingUsers = new ArrayList<>();

    /*
    UserList acts like a singleton class, and only one
    object is to exist of the class.
     */
    private static final UserList INSTANCE = new UserList();

    /*
    Makes it possible to make a pointer to the existing UserList object.
     */
    public static UserList getInstance() {return INSTANCE;}

    private UserList() {}

    public static void addUser(User user) {
	existingUsers.add(user);
	//sorts users on the fly.
	existingUsers.sort(new UserComparator());
    }

    public static boolean userExists(User user) {
	for (User users : existingUsers) {
	    if (users.getName().equals(user.getName())) {
		throw new IllegalArgumentException("This user already exist!");
	    }
	}
	System.out.println(existingUsers);
	System.out.println("Finns inte");
	return false;
    }

    public void deleteUser(User user) {
	if (existingUsers.contains(user)) {
	    existingUsers.remove(user);
	} else {
	    throw new IllegalArgumentException("This user does not exist!");
	}
    }

    public List<User> getExistingUsers() {
	return existingUsers;
    }
}
