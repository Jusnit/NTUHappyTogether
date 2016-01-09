package com.example.user.ntuhappytogether;

import com.parse.ParseObject;

import java.util.ArrayList;

import ParseUtil.ParseFunction;

/**
 * Created by user on 2016/1/10.
 */
public class HostEvent implements  Query{


    @Override
    public ArrayList<ParseObject> query() {
        ParseFunction pf = new ParseFunction();
        return pf.getMyEvent();
    }
}
