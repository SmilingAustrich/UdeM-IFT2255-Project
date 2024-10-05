public class AuthenticationService {

    public static boolean login(String username, String password) {


        if ("resident".equals(username) && "password".equals(password)) {
            return true;
        } else return "intervenant".equals(username) && "password".equals(password);
    }
}
