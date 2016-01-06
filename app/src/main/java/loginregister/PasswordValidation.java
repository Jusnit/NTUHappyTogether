package loginregister;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 2016/1/6.
 */
public class PasswordValidation implements TextValidation{

    private Context context;
    public PasswordValidation(Context context){
        this.context = context;
    }

    @Override
    public boolean checkValidity(String password) {

        final int MIN_LENGTH = 6;
        final int MAX_LENGTH = 15;

        if (password.length() < MIN_LENGTH || password.length() >= MAX_LENGTH) {
            showError();
            return false;
        }
        return true;
    }

    @Override
    public void showError() {
        Toast t1 = Toast.makeText(context, "密碼長度錯誤!", Toast.LENGTH_LONG);
        t1.show();
        return;
    }
}
