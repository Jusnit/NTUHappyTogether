package account;

/**
 * Created by user on 2016/1/2.
 */
public class Account {

    private static String username;
    private static String password;
    private static String email;

    public Account(String name,String pwd,String email){
        setUsername(name);
        setPassword(pwd);
        setEmail(email);
    }
    public void setUsername(String name){
        username = name;
    }

    public void setPassword(String pwd){
        password = pwd;
    }

    public void setEmail(String mail){
        email = mail;
    }
}
