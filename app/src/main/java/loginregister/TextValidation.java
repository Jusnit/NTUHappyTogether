package loginregister;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by user on 2016/1/1.
 */
public class TextValidation {

    public boolean checkValidity(String nameOrPassword) {

        final int MIN_LENGTH = 6;
        final int MAX_LENGTH = 15;

        if (nameOrPassword.length() < MIN_LENGTH || nameOrPassword.length() >= MAX_LENGTH)
            return false;
        return true;
    }


    public boolean isEmailValid(CharSequence email) {
        String emailString = email.toString();
        String[] split = emailString.split("@");

        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && split[1].equals("ntu.edu.tw"));
    }
}
