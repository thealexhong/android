package com.github.thealexhong.practice2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.github.thealexhong.practice2.R;
import com.github.thealexhong.practice2.record.Displayable;
import com.github.thealexhong.practice2.record.EntryAdapter;
import com.github.thealexhong.practice2.record.PersonDatabase;

import java.util.List;

public class ViewFragment extends MyFragment {

    private static final String TAG = ViewFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
    }

    private void setView() {
        Log.d(TAG, "Loading entry list");
        PersonDatabase database = PersonDatabase.fromJSON((String) getArguments().get("bundle_id"));
        List<Displayable> list = database.getPersonRecordList();

        ListView listView = (ListView) getActivity().findViewById(R.id.list);
        EntryAdapter adapter = new EntryAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        Button btn_back = (Button) getActivity().findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrevFragment();
            }
        });
    }
}
