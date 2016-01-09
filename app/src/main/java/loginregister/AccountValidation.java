package loginregister;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by user on 2016/1/6.
 */
public class AccountValidation implements  TextValidation{
    private Context context;
    public AccountValidation(Context context){
        this.context = context;
    }

    @Override
    public void showError() {
        Toast t1 = Toast.makeText(context, "帳號格式錯誤!\n只能輸入正確長度的英文及數字", Toast.LENGTH_LONG);
        t1.show();
        return;
    }

    @Override
    public boolean checkValidity(String account) {
        final int MIN_LENGTH = 4;
        final int MAX_LENGTH = 10;

        if (account.length() < MIN_LENGTH || account.length() >= MAX_LENGTH) {
            showError();
            return false;
        }
        char[] tempArray = account.toCharArray();
        for (int i = 0; i < tempArray.length; i++) {
            if ((tempArray[i] < 48
                    || (tempArray[i] > 57 && tempArray[i] < 65)
                    || (tempArray[i] > 90 && tempArray[i] < 97) || tempArray[i] > 122)
                    && tempArray[i] != 95) {
                showError();
                return false;
            }
        }
        return true;
    }
}
