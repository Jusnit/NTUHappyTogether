package com.example.user.ntuhappytogether;

import ParseUtil.ParseFunction;

/**
 * Created by user on 2016/1/10.
 */
public class Exit implements ModifyBehavior {

    @Override
    public void modify(String userId, String eventId,String m) {
        ParseFunction.exitEvent(userId,eventId);
    }
}
