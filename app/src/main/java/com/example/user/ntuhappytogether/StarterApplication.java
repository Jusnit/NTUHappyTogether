package com.example.user.ntuhappytogether;

import android.app.Application;
import android.util.Log;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2015/12/24.
 */
public class StarterApplication extends Application {

    private static final String tag = "StarterApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

//    public static ArrayList<ParseObject> getEventByKeyWord(String key_word){
//        HashMap<String,String> querytitle = new HashMap();
//        querytitle.put("title", key_word);
//        final ArrayList<ParseObject>  parseObjectList = new ArrayList();
//        ParseCloud.callFunctionInBackground("query_title", querytitle, new FunctionCallback<ArrayList<ParseObject>>() {
//            public void done(ArrayList<ParseObject> result, ParseException e) {
//                if (e == null) {
//                    try {
//                        parseObjectList.addAll(result);
//                        Log.i(tag, "result.size()=" + result.size());
//                        //JSONObject jsonObj = new JSONObject(result);
//                        for (ParseObject temp : result) {
//
//                            ParseUser user = ParseUser.getCurrentUser();
//                            ParseRelation<ParseObject> relation = temp.getRelation("participant");
//                            relation.add(user);
//                            temp.saveInBackground();
//                            Log.i(tag, temp.getString("title"));
//                            //Log.i(tag,result.get("comment").toString());
//                        }
//
//
//
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                    Log.i(tag, result + ":hellofunction");
//
//                }
//            }
//        });
//        return parseObjectList;
//    }
}
