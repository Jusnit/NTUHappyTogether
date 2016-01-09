package com.example.user.ntuhappytogether;

/**
 * Created by user on 2016/1/10.
 */
public class EventList {
    private Event[] searchEvent;

    public EventList(Event[ ] list){
        this.searchEvent = list;
    }
    public Event[] getList(){
        return searchEvent;
    }

}
