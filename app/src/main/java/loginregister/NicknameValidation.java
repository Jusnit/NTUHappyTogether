package loginregister;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 2016/1/6.
 */
public class NicknameValidation implements TextValidation{
    private Context context;
    public NicknameValidation(Context context){
        this.context = context;
    }

    @Override
    public boolean checkValidity(String nickname) {
        final int MIN_LENGTH = 1;
        final int MAX_LENGTH = 8;

        if (nickname.length() < MIN_LENGTH || nickname.length() >= MAX_LENGTH) {
            showError();
            return false;
        }
        return true;
    }


    @Override
    public void showError() {
        Toast t1 = Toast.makeText(context, "暱稱輸入有誤!", Toast.LENGTH_LONG);
        t1.show();
        return;
    }
}
