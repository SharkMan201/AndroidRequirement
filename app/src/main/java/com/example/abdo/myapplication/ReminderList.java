package com.example.abdo.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReminderList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReminderList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderList extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REMINDER_LIST = "reminder-list";

    private ArrayList<ReminderModel> mReminderList;
    private ReminderListArrayAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private ListView mListView;

    public ReminderList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param reminderList Arraylist of string
     * @return A new instance of fragment ReminderList.
     */
    public static ReminderList newInstance(ArrayList<ReminderModel> reminderList) {
        ReminderList fragment = new ReminderList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_REMINDER_LIST, reminderList);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mReminderList = getArguments().getParcelableArrayList(ARG_REMINDER_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_reminder_list, container, false);

        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);

        // Set the adapter
        if (view instanceof ListView) {
            //Context context = view.getContext();
            mListView = (ListView) view;

            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.onListItemHold(position, mReminderList.get(position).getID());
                    return false;
                }
            });

            //listView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new ReminderListArrayAdapter(getContext(), mReminderList);


            mListView.setAdapter(mAdapter);
        }
        return view;
    }


    public void updateView(ArrayList<ReminderModel> data) {
        mReminderList.clear();
        mReminderList.addAll(data);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onListItemHold(int idx, String databaseID);
    }
}
