package loginregister;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

/**
 * Created by user on 2016/1/6.
 */
public class EmailValidation implements TextValidation{

    private Context context;
    public EmailValidation(Context context){
        this.context = context;
    }
    @Override
    public boolean checkValidity(String email) {
        if(!isEmailValid(email)){
            showError();
            return false;
        }
        return true;
    }

    @Override
    public void showError() {
        Toast t1 = Toast.makeText(context, "Email格式錯誤!\n請使用ntu信箱", Toast.LENGTH_LONG);
        t1.show();
    }

    private boolean isEmailValid(CharSequence email) {
        String emailString = email.toString();
        String[] split = emailString.split("@");

        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && split[1].equals("ntu.edu.tw"));
    }
}
