package account;

import com.parse.ParseUser;

/**
 * Created by user on 2016/1/2.
 */
public interface AccountBuilder {
    public Account buildAccount(String name,String pwd,String email,String nickname);
}
