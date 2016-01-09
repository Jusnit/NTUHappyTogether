package com.example.user.ntuhappytogether;

import com.parse.ParseObject;

import java.util.ArrayList;

import ParseUtil.ParseFunction;

/**
 * Created by user on 2016/1/6.
 */
public class EventQueryByType implements Query{

    private String keyword;
    public EventQueryByType(String keyword){
        this.keyword = keyword;
    }

    @Override
    public ArrayList<ParseObject> query() {
        return new ParseFunction().queryType(this.keyword);
    }
}
