package com.example.user.ntuhappytogether;

import android.view.View;

/**
 * Created by user on 2016/1/10.
 */
public class ParticipantMode {
    private static final String tag = "Participant";
    private View view;
    public  ParticipantMode(View v){
        this.view = v;
    }
    public void display(){
        if(view.VISIBLE == View.INVISIBLE){
            view.setVisibility(View.VISIBLE);
        }
    }
}
