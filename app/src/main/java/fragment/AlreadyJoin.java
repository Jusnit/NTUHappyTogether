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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
 * {@link AlreadyJoin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlreadyJoin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlreadyJoin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String tag = "AlreadyJoin";
    private ArrayList<ParseObject> eventObjList;
    private ArrayList<ParseObject> finishObjList;
    private List<ParseObject> participantList;
    private ListView my_event_listview;
    private ListView finished_listview;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlreadyJoin.
     */
    // TODO: Rename and change types and number of parameters
    public static AlreadyJoin newInstance(String param1, String param2) {
        AlreadyJoin fragment = new AlreadyJoin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AlreadyJoin() {
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
        return inflater.inflate(R.layout.fragment_already_join, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        my_event_listview = (ListView)getActivity().findViewById(R.id.already_join_listview);
        eventObjList = new ArrayList();
        finishObjList = new ArrayList();
        participantList = new ArrayList();
        Log.i(tag,"AlreadyJoin's onActivityCreated.");
        new Thread((new queryRunnable())).start();
    }


    private void refresh(){
        finishObjList.clear();

        for(ParseObject event : eventObjList){
            if(event.getNumber("end").intValue() > 0){
                finishObjList.add(event);
            }
        }
        eventObjList.removeAll(finishObjList);
        String[] values = new String[eventObjList.size()];
        int count = 0;
        for (ParseObject temp : eventObjList) {
            Log.i(tag, "parseObj is not null");
            values[count++] = temp.getString("title");
        }
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_simple_listview, values);
        if(my_event_listview == null)
            my_event_listview = (ListView)getActivity().findViewById(R.id.already_join_listview);
        my_event_listview.setAdapter(adapter);

        my_event_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(tag, "position=" + position);
                final View item = LayoutInflater.from(getActivity()).inflate(R.layout.event_info, null);
                final int pos = position;
                TextView event = (TextView) item.findViewById(R.id.event_discribe);
                TextView limit = (TextView) item.findViewById(R.id.limit_describe);
                TextView type = (TextView) item.findViewById(R.id.eventtype_describe);
                TextView rate = (TextView)item.findViewById(R.id.host_score);
                TextView current = (TextView)item.findViewById(R.id.currentPeople);
                TextView hostname = (TextView)item.findViewById(R.id.hostname);
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
                type.setText(eventObjList.get(pos).getString("type"));

                current.setText(""+eventObjList.get(pos).getNumber("counter"));
                rate.setText(""+eventObjList.get(pos).getNumber("hostrate")+"讚");
                TextView eventDetail = (TextView) item.findViewById(R.id.eventdetail);
                event.setText(eventObjList.get(pos).getString("title"));
                limit.setText("" + eventObjList.get(pos).getNumber("limit"));
                //TODO: set type
                eventDetail.setText(eventObjList.get(pos).getString("context"));
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.eventinfo)
                        .setView(item)
                        .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
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
                                ParseFunction.exitEvent(ParseUser.getCurrentUser().getObjectId(), eventObjList.get(pos).getObjectId());
                                Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
                                Log.i(tag, "eventId=" + eventObjList.get(pos).getObjectId());

                            }
                        })
                        .show();
            }
        });

        //====================
        String[] finishvalues = new String[finishObjList.size()];
        int countfinish = 0;
        for (ParseObject temp : finishObjList) {
            Log.i(tag, "finishparseObj is not null");
            finishvalues[countfinish++] = temp.getString("title");
        }
        ListAdapter adapterfinish = new ArrayAdapter<String>(getActivity(), R.layout.my_simple_listview, finishvalues);
        if(finished_listview == null)
            finished_listview = (ListView)getActivity().findViewById(R.id.already_finish_listview);
        finished_listview.setAdapter(adapterfinish);
        for(int i = 0;i < finishvalues.length;i++){
            Log.i(tag,"finishValue:"+finishvalues[i]);
        }
        finished_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(tag, "finished_listview position=" + position);
                final View item = LayoutInflater.from(getActivity()).inflate(R.layout.event_info, null);
                final int pos = position;
//                TextView event = (TextView) item.findViewById(R.id.event_discribe);
//                TextView limit = (TextView) item.findViewById(R.id.limit_describe);
//                TextView type = (TextView) item.findViewById(R.id.eventtype_describe);
//                TextView eventDetail = (TextView) item.findViewById(R.id.eventdetail);
//                event.setText(eventObjList.get(pos).getString("title"));
//                limit.setText("" + eventObjList.get(pos).getNumber("limit"));
                //TODO: set type
//                eventDetail.setText(eventObjList.get(pos).getString("context"));
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.thankful)
                        .setMessage(R.string.thankfuldetail)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//
                                ParseFunction.rateEvent(ParseUser.getCurrentUser().getObjectId(), finishObjList.get(pos).getObjectId(), 1);
//                                Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
//                                Log.i(tag, "eventId=" + eventObjList.get(pos).getObjectId());

                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//
                                ParseFunction.rateEvent(ParseUser.getCurrentUser().getObjectId(), finishObjList.get(pos).getObjectId(), 0);
//                                Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
//                                Log.i(tag, "eventId=" + eventObjList.get(pos).getObjectId());

                            }
                        })
                        .show();
            }
        });
    }
    class queryRunnable implements Runnable{
        boolean queryOK = false;
        ProgressDialog pd;
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
            eventObjList = parseFun.getAlreadyJoinEvent();
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
                    refresh();
//                    String[] values = new String[eventObjList.size()];
//                    int count = 0;
//                    for (ParseObject temp : eventObjList) {
//                        Log.i(tag, "parseObj is not null");
//                        values[count++] = temp.getString("title");
//                    }
//                    ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_simple_listview, values);
//                    if(my_event_listview == null)
//                        my_event_listview = (ListView)getActivity().findViewById(R.id.already_join_listview);
//                    my_event_listview.setAdapter(adapter);
//
//                    my_event_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Log.i(tag,"position="+position);
//                            final View item = LayoutInflater.from(getActivity()).inflate(R.layout.event_info, null);
//                            final int pos = position;
//                            TextView event = (TextView)item.findViewById(R.id.event_discribe);
//                            TextView limit = (TextView)item.findViewById(R.id.limit_describe);
//                            TextView type = (TextView)item.findViewById(R.id.eventtype_describe);
//                            TextView eventDetail = (TextView)item.findViewById(R.id.eventdetail);
//                            event.setText(eventObjList.get(pos).getString("title"));
//                            limit.setText(""+eventObjList.get(pos).getNumber("limit"));
//                            //TODO: set type
//                            eventDetail.setText(eventObjList.get(pos).getString("context"));
//                            new AlertDialog.Builder(getActivity())
//                                    .setTitle(R.string.eventinfo)
//                                    .setView(item)
//                                    .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
////                                            TextView event = (EditText)item.findViewById(R.id.event_discribe);
////                                            TextView limit = (EditText)item.findViewById(R.id.limit_describe);
////                                            TextView type = (EditText)item.findViewById(R.id.eventtype_describe);
////                                            TextView eventDetail = (EditText)item.findViewById(R.id.eventdetail);
////                                            event.setText(eventObjList.get(pos).getString("title"));
////                                            limit.setText(""+eventObjList.get(pos).getNumber("limit"));
////                                            //TODO: set type
////                                            eventDetail.setText(eventObjList.get(pos).getString("context"));
//                                            ParseFunction.exitEvent(ParseUser.getCurrentUser().getObjectId(),eventObjList.get(pos).getObjectId());
//                                            Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
//                                            Log.i(tag, "eventId=" + eventObjList.get(pos).getObjectId());
//
//                                        }
//                                    })
//                                    .show();
//                        }
//                    });
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
