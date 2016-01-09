package com.example.user.ntuhappytogether;

import com.parse.ParseObject;

import java.util.ArrayList;

import ParseUtil.ParseFunction;

/**
 * Created by user on 2016/1/6.
 */
public class EventQueryByTitle implements Query{
    private String keyword;
    public EventQueryByTitle(String keyword){
    this.keyword = keyword;
    }

    @Override
    public ArrayList<ParseObject> query() {
        return new ParseFunction().queryEvent(this.keyword);
    }
}
