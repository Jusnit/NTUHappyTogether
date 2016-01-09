package com.example.user.ntuhappytogether;

import account.Account;

/**
 * Created by user on 2016/1/10.
 */
public class Event {
    int state;
    Account account;
    public Event(int state,Account account){
        this.state = state;
        this.account = account;
    }
}
