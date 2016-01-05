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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.ntuhappytogether.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

import ParseUtil.ParseFunction;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String tag = "MyEvent";
    private ArrayList<ParseObject> eventObjList;
    private ListView my_event_listview;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyEventFragment newInstance(String param1, String param2) {
        MyEventFragment fragment = new MyEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyEventFragment() {
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
        return inflater.inflate(R.layout.fragment_my_event, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        my_event_listview = (ListView)getActivity().findViewById(R.id.my_event_listview);
        eventObjList = new ArrayList();
        new Thread((new queryRunnable())).start();
    }

    private void refresh(){
        String[] values = new String[eventObjList.size()];
        int count = 0;
        for (ParseObject temp : eventObjList) {
            Log.i(tag, "parseObj is not null");
            values[count++] = temp.getString("title");
        }
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_simple_listview, values);
        if(my_event_listview == null)
            my_event_listview = (ListView)getActivity().findViewById(R.id.my_event_listview);
        my_event_listview.setAdapter(adapter);

        my_event_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(tag,"position="+position);
                final View item = LayoutInflater.from(getActivity()).inflate(R.layout.event_info, null);
                final int pos = position;
                TextView event = (TextView)item.findViewById(R.id.event_discribe);
                TextView limit = (TextView)item.findViewById(R.id.limit_describe);
                TextView type = (TextView)item.findViewById(R.id.eventtype_describe);
                TextView eventDetail = (TextView)item.findViewById(R.id.eventdetail);
                event.setText(eventObjList.get(pos).getString("title"));
                limit.setText(""+eventObjList.get(pos).getNumber("limit"));
                //TODO: set type
                eventDetail.setText(eventObjList.get(pos).getString("context"));
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.eventinfo)
                        .setView(item)
                        .setNegativeButton(R.string.modify, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final View item2 = LayoutInflater.from(getActivity()).inflate(R.layout.modify_dialog, null);
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(R.string.eventinfo)
                                        .setView(item2)
                                        .setPositiveButton(R.string.modify, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                EditText text = (EditText) item2.findViewById(R.id.modify_context);
                                                String newcontext = text.getText().toString();
                                                ParseFunction.modifyEvent(ParseUser.getCurrentUser().getObjectId(), eventObjList.get(pos).getObjectId(), newcontext);
                                                Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
                                                Log.i(tag, "ModifyeventId=" + eventObjList.get(pos).getObjectId());

                                            }
                                        })
                                        .show();
                                Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
                                Log.i(tag, "CanceleventId=" + eventObjList.get(pos).getObjectId());

                            }
                        })
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(R.string.warning)
                                        .setMessage(R.string.ask_cancel)
                                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ParseFunction.cancelEvent(ParseUser.getCurrentUser().getObjectId(), eventObjList.get(pos).getObjectId());
                                                eventObjList.remove(pos);
                                                refresh();
                                                Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
                                                Log.i(tag, "CanceleventId=" + eventObjList.get(pos).getObjectId());

                                            }
                                        })
                                        .show();
//                                            ParseFunction.cancelEvent(ParseUser.getCurrentUser().getObjectId(), eventObjList.get(pos).getObjectId());
//                                            Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
//                                            Log.i(tag, "CanceleventId=" + eventObjList.get(pos).getObjectId());

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
            eventObjList = parseFun.getMyEvent();
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
//                        my_event_listview = (ListView)getActivity().findViewById(R.id.my_event_listview);
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
//                                    .setNegativeButton(R.string.modify, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            final View item2 = LayoutInflater.from(getActivity()).inflate(R.layout.modify_dialog, null);
//                                            new AlertDialog.Builder(getActivity())
//                                                    .setTitle(R.string.eventinfo)
//                                                    .setView(item2)
//                                                    .setPositiveButton(R.string.modify, new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            EditText text = (EditText) item2.findViewById(R.id.modify_context);
//                                                            String newcontext = text.getText().toString();
//                                                            ParseFunction.modifyEvent(ParseUser.getCurrentUser().getObjectId(), eventObjList.get(pos).getObjectId(), newcontext);
//                                                            Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
//                                                            Log.i(tag, "ModifyeventId=" + eventObjList.get(pos).getObjectId());
//
//                                                        }
//                                                    })
//                                                    .show();
//                                            Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
//                                            Log.i(tag, "CanceleventId=" + eventObjList.get(pos).getObjectId());
//
//                                        }
//                                    })
//                                    .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            new AlertDialog.Builder(getActivity())
//                                                    .setTitle(R.string.warning)
//                                                    .setMessage(R.string.ask_cancel)
//                                                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(DialogInterface dialog, int which) {
//                                                            ParseFunction.cancelEvent(ParseUser.getCurrentUser().getObjectId(), eventObjList.get(pos).getObjectId());
//                                                            Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
//                                                            Log.i(tag, "CanceleventId=" + eventObjList.get(pos).getObjectId());
//
//                                                        }
//                                                    })
//                                                    .show();
////                                            ParseFunction.cancelEvent(ParseUser.getCurrentUser().getObjectId(), eventObjList.get(pos).getObjectId());
////                                            Log.i(tag, "UserId=" + ParseUser.getCurrentUser().getObjectId());
////                                            Log.i(tag, "CanceleventId=" + eventObjList.get(pos).getObjectId());
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
