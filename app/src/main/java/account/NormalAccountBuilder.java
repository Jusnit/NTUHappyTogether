package account;

import com.parse.ParseUser;

import ParseUtil.ParseFunction;

/**
 * Created by user on 2016/1/2.
 */
public class NormalAccountBuilder implements AccountBuilder{
    @Override
    public Account buildAccount(String name,String pwd,String email,String nickname){
        ParseFunction.signUp(name, pwd,email,nickname);
        return new Account(name,pwd,email);

    }
}
