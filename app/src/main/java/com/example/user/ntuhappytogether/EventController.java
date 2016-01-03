package com.example.user.ntuhappytogether;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fragment.AlreadyJoin;
import fragment.MyEventFragment;
import fragment.SearchEvent;

public class EventController extends Activity implements MyEventFragment.OnFragmentInteractionListener
        , SearchEvent.OnFragmentInteractionListener, AlreadyJoin.OnFragmentInteractionListener{

    private TextView searchEventTV,alreadyJoinTV,myEventTV;
    private Fragment searchFragment,alreadyJoinFragment,myEventFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_event_controller);
        searchFragment = new SearchEvent();
        getFragmentManager().beginTransaction().replace(R.id.fragment_frame, searchFragment)
                .commit();
        clickSearch();

        setWidgets();
    }

    private void clickSearch(){
        RelativeLayout r = (RelativeLayout)findViewById(R.id.searchrelative);
        r.setBackgroundColor(0xFF228A2D);
        RelativeLayout r2= (RelativeLayout)findViewById(R.id.alreadyelative);
        r2.setBackgroundColor(0xFF30D92D);
        RelativeLayout r3= (RelativeLayout)findViewById(R.id.myeventrelative);
        r3.setBackgroundColor(0xFF30D92D);

    }
    private void clickAlready(){
        RelativeLayout r = (RelativeLayout)findViewById(R.id.alreadyelative);
        r.setBackgroundColor(0xFF228A2D);
        RelativeLayout r2 = (RelativeLayout)findViewById(R.id.searchrelative);
        r2.setBackgroundColor(0xFF30D92D);
        RelativeLayout r3 = (RelativeLayout)findViewById(R.id.myeventrelative);
        r3.setBackgroundColor(0xFF30D92D);
    }
    private void clickMyEvent(){
        RelativeLayout r = (RelativeLayout)findViewById(R.id.myeventrelative);
        r.setBackgroundColor(0xFF228A2D);
        RelativeLayout r2 = (RelativeLayout)findViewById(R.id.alreadyelative);
        r2.setBackgroundColor(0xFF30D92D);
        RelativeLayout r3 = (RelativeLayout)findViewById(R.id.searchrelative);
        r3.setBackgroundColor(0xFF30D92D);
    }
    private void setWidgets(){
        searchFragment = new SearchEvent();
        alreadyJoinFragment = new AlreadyJoin();
        myEventFragment = new MyEventFragment();

        searchEventTV = (TextView)findViewById(R.id.serachevent_textview);
        searchEventTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSearch();
                getFragmentManager().beginTransaction().replace(R.id.fragment_frame, searchFragment)
                        .commit();
            }
        });
        alreadyJoinTV = (TextView)findViewById(R.id.already_join_textivew);
        alreadyJoinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAlready();
                getFragmentManager().beginTransaction().replace(R.id.fragment_frame, alreadyJoinFragment)
                        .commit();
            }
        });
        myEventTV= (TextView)findViewById(R.id.my_event_textview);
        myEventTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMyEvent();
                getFragmentManager().beginTransaction().replace(R.id.fragment_frame, myEventFragment)
                        .commit();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
