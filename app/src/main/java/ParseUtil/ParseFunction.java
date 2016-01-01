package ParseUtil;

import android.util.Log;

import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2016/1/1.
 */
public class ParseFunction {
    private static final String tag = "ParseFunction";

    public static void createEvent(String title,String context,int limit){
        HashMap<String,Object> create = new HashMap();
        create.put("title",title);
        create.put("context", context);
        create.put("limit", limit);
        ParseCloud.callFunctionInBackground("create", create, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    Log.i(tag,"create:"+result);
                }
                else
                    Log.i(tag, "create Exception:"+e.getMessage());
            }
        });
    }

    public static ArrayList<ParseObject> queryEvent(String title){
        HashMap<String,String> querytitle = new HashMap();
        querytitle.put("title", title);
        final ArrayList<ParseObject> parseObjList = new ArrayList();
        ParseCloud.callFunctionInBackground("query_title", querytitle, new FunctionCallback<ArrayList<ParseObject>>() {
            public void done(ArrayList<ParseObject> result, ParseException e) {
                if (e == null) {
                    try {
                        parseObjList.addAll(result);
                        Log.i(tag, "result.size()=" + result.size());
                        //JSONObject jsonObj = new JSONObject(result);
                        for (ParseObject temp : result) {

                            ParseUser user = ParseUser.getCurrentUser();
                            ParseRelation<ParseObject> relation = temp.getRelation("participant");
                            relation.add(user);
                            temp.saveInBackground();
                            Log.i(tag, "title:" + temp.getString("title"));
                            //Log.i(tag,result.get("comment").toString());
                        }


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    Log.i(tag, result + ":hellofunction");

                }
                else{
                    Log.i(tag,"query Exception"+e.toString());
                }
            }
        });

        return parseObjList;
    }

    public static void joinEvent (ParseUser user,String eventId) {
        HashMap<String, String> joinMap = new HashMap();
        joinMap.put("objectId", eventId);
        joinMap.put("userId",user.getObjectId());
        ParseCloud.callFunctionInBackground("join", joinMap, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    if(result == null){Log.i(tag,"result is null");}
                    else {
                        Log.i(tag, "Join:" + result);
                    }

                }else{
                    Log.i(tag, "join Exception:"+e.getMessage());
                }
            }
        });
    }

    public static void exitEvent(ParseUser user,String eventId) {
        HashMap<String, String> exitMap = new HashMap();
        exitMap.put("objectId", eventId);
        exitMap.put("userId", user.getObjectId());
        ParseCloud.callFunctionInBackground("exit", exitMap, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    if (result == null) {
                        Log.i(tag, "result is null");
                    } else
                        Log.i(tag, "Exit:" + result);


                }
            }
        });
    }

    public static void signUp(String name,String password,String email){
        ParseUser user = new ParseUser();
        user.setUsername(name);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("signUp:", "register complete");
                } else {
                    Log.i("signUp fail:", e.toString());
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }

    public static void login(String name, String password) {
        ParseUser.logInInBackground(name, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Log.i(tag,"login:"+"login success");

                } else {
                    Log.i(tag,"login:"+e.toString());
                }
            }
        });
    }
}
