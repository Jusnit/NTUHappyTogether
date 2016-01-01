package com.example.user.ntuhappytogether;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.ParseObject;

import java.util.ArrayList;

public class JoinEventActivity extends Activity {
    public static final String tag = "JoinEventActivity";

    private EditText keyWord;
    private ListView eventList;
    private Button searchingButton;
   // private AdapterView<String> adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_join_event);
        setWidget();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_join_event, menu);
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

    private void setWidget(){
        keyWord = (EditText)findViewById(R.id.keyword);
        eventList = (ListView)findViewById(R.id.listView);
        searchingButton = (Button)findViewById(R.id.searching_button);
        searchingButton.setOnClickListener(new OnSearchingListener());
    }

    private class OnSearchingListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Log.i(tag,"onclick");
            String key_word= keyWord.getText().toString();
            if(key_word == null)  return;
            ArrayList<ParseObject> parseObjList = StarterApplication.getEventByKeyWord(key_word);
            String[] values = new String[parseObjList.size()];
            int count = 0;
            for(ParseObject temp : parseObjList){
                Log.i(tag,"parseObj is not null");
                values[count++] = temp.getString("title");
            }
            ListAdapter adapter = new ArrayAdapter<String>(JoinEventActivity.this, android.R.layout.simple_list_item_checked ,values);
            eventList.setAdapter(adapter);

        }
    }

}
