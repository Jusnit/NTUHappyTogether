package loginregister;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by user on 2016/1/1.
 */
public interface TextValidation {

    public boolean checkValidity(String nameOrPassword);

    public void showError();

}
