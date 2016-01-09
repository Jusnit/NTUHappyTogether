package loginregister;

import ParseUtil.ParseFunction;

/**
 * Created by user on 2016/1/1.
 */
public class Register {
    public void register(String name,String pwd,String email,String nickname){
        ParseFunction.signUp(name,pwd,email,nickname);
    }
}
