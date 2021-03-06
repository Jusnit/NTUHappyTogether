package fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.ntuhappytogether.Query;
import com.example.user.ntuhappytogether.EventQueryByTitle;
import com.example.user.ntuhappytogether.EventQueryByType;
import com.example.user.ntuhappytogether.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import ParseUtil.ParseFunction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchEvent extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String tag = "SearchEvent";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText keyWord;
    private ListView eventList;
    private Button searchingButton;

    private TextView eat,sport,other;

    private ArrayList<ParseObject> eventObjList;
    private List<ParseObject> participantList;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchEvent newInstance(String param1, String param2) {
        SearchEvent fragment = new SearchEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(tag, "onActivityCreated");
        eventObjList = new ArrayList();
        participantList = new ArrayList();
        setWidget();

    }
    private void setWidget(){
        eat = (TextView)getActivity().findViewById(R.id.textView3);
        sport = (TextView)getActivity().findViewById(R.id.textView4);
        other = (TextView)getActivity().findViewById(R.id.textView5);
        eat.setOnClickListener(new OnEatListener());
        sport.setOnClickListener(new OnSportListener());
        other.setOnClickListener(new OnOtherListener());

        keyWord = (EditText)getActivity().findViewById(R.id.keyword);
        eventList = (ListView)getActivity().findViewById(R.id.listView);
        searchingButton = (Button)getActivity().findViewById(R.id.searching_button);
        searchingButton.setOnClickListener(new OnSearchingListener());
    }


    private class OnSearchingListener implements View.OnClickListener{

            @Override
            public void onClick(View v){
                Log.i(tag, "onclick");
                final String  key_word= keyWord.getText().toString();
                if(key_word == null)  return;
                boolean flag = false;
                new Thread((new queryRunnable(key_word,flag,new EventQueryByTitle(key_word)))).start();
            }
        }
    private class OnEatListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Log.i(tag, "onclick");
//            final String  key_word= keyWord.getText().toString();
            final String  key_word= ((TextView)v).getText().toString();
            if(key_word == null)  return;
            boolean flag = false;
            new Thread((new queryRunnable(key_word,flag,new EventQueryByType(key_word)))).start();
        }
    }
    private class OnSportListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Log.i(tag, "onclick");
//            final String  key_word= keyWord.getText().toString();
            final String  key_word= ((TextView)v).getText().toString();
            if(key_word == null)  return;
            boolean flag = false;
            new Thread((new queryRunnable(key_word,flag,new EventQueryByType(key_word)))).start();
        }
    }
    private class OnOtherListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Log.i(tag, "onclick");
            final String  key_word= ((TextView)v).getText().toString();
            if(key_word == null)  return;
            boolean flag = false;
            new Thread((new queryRunnable(key_word,flag,new EventQueryByType(key_word)))).start();
        }
    }


        class queryRunnable implements Runnable{
            private String keyword;
            private Query query;
            ProgressDialog pd;
            queryRunnable(String keyword,boolean flag,Query query){
                this.keyword = keyword;
                this.query = query;
            }
            @Override
            public void run() {
                ParseFunction parseFun = new ParseFunction();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd = new ProgressDialog(getActivity());
                        pd.setTitle("請稍後");
                        pd.setMessage("擷取資料中...");
                        pd.setCancelable(false);
                        pd.setCanceledOnTouchOutside(false);
                        pd.show();
                    }
                });
//                eventObjList = parseFun.queryEvent(this.keyword);
                eventObjList = query.query();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
//            String[] values = new String[parseObjList.size()];
//            int count = 0;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] values = new String[eventObjList.size()];
                        int count = 0;
                        for (ParseObject temp : eventObjList) {
                            Log.i(tag, "parseObj is not null");
                            Log.i(tag, "host rating="+temp.getNumber("hostrate").toString());

                            values[count++] = temp.getString("title");
                        }
                        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_simple_listview, values);
                        eventList.setAdapter(adapter);

                        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.i(tag,"position="+position);
                                final View item = LayoutInflater.from(getActivity()).inflate(R.layout.event_info, null);
                                final int pos = position;
                                TextView event = (TextView)item.findViewById(R.id.event_discribe);
                                TextView limit = (TextView)item.findViewById(R.id.limit_describe);
                                TextView type = (TextView)item.findViewById(R.id.eventtype_describe);
                                TextView rate = (TextView)item.findViewById(R.id.host_score);
                                TextView current = (TextView)item.findViewById(R.id.currentPeople);
                                TextView hostname = (TextView)item.findViewById(R.id.hostname);
                                TextView starttime = (TextView)item.findViewById(R.id.start_time);

                                ParseRelation<ParseObject> relation = (eventObjList.get(pos)).getRelation("participant");
                                ParseObject outtemp  = ParseObject.create("class");
                                try {
                                    participantList = relation.getQuery().find();
                                    for(ParseObject innertemp : participantList){
                                        Log.i(tag,"Participant List:"+innertemp.getString("nickname"));
                                        if(innertemp.getObjectId().equals(eventObjList.get(pos).getString("host"))){
                                            hostname.setText(innertemp.getString("nickname"));
                                            outtemp = innertemp;
                                            break;

                                        }


                                    }


                                }catch(ParseException e){Log.i(tag,"relationQuery Problem:"+e.toString());}
                                //hostname.setText(outtemp.getString("name"));

                                participantList.remove(outtemp);
                                Spinner joinspinner = (Spinner)item.findViewById(R.id.join_spinner);
                                String[] values = new String[participantList.size()];
                                for(int i = 0;i < participantList.size(); i++){
                                    values[i] = participantList.get(i).getString("username");
                                }
                                ArrayAdapter<String> adp3 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,values);
                                adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                joinspinner.setAdapter(adp3);

                                starttime.setText(eventObjList.get(pos).getString("time_str"));

                                type.setText(eventObjList.get(pos).getString("type"));
                                current.setText(""+eventObjList.get(pos).getNumber("counter"));
                                rate.setText(""+eventObjList.get(pos).getNumber("hostrate")+"讚");
                                TextView eventDetail = (TextView)item.findViewById(R.id.eventdetail);
                                event.setText(eventObjList.get(pos).getString("title"));
                                limit.setText(""+eventObjList.get(pos).getNumber("limit"));
                                //TODO: set type
                                eventDetail.setText(eventObjList.get(pos).getString("context"));
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(R.string.eventinfo)
                                        .setView(item)
                                        .setPositiveButton(R.string.join, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
//                                            TextView event = (EditText)item.findViewById(R.id.event_discribe);
//                                            TextView limit = (EditText)item.findViewById(R.id.limit_describe);
//                                            TextView type = (EditText)item.findViewById(R.id.eventtype_describe);
//                                            TextView eventDetail = (EditText)item.findViewById(R.id.eventdetail);
//                                            event.setText(eventObjList.get(pos).getString("title"));
//                                            limit.setText(""+eventObjList.get(pos).getNumber("limit"));
//                                            //TODO: set type
//                                            eventDetail.setText(eventObjList.get(pos).getString("context"));
                                                ParseFunction.joinEvent(ParseUser.getCurrentUser().getObjectId(),eventObjList.get(pos).getObjectId());
                                                Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
                                                Log.i(tag, "eventId=" + eventObjList.get(pos).getObjectId());

                                            }
                                        })
                                        .show();
                            }
                        });
                    }
                });

            }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
