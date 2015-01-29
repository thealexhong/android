package com.github.thealexhong.practice2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.thealexhong.practice2.R;
import com.github.thealexhong.practice2.record.DatabaseException;
import com.github.thealexhong.practice2.record.DatabaseManager;

public class StoreFragment extends MyFragment {

    private static final String TAG = StoreFragment.class.getSimpleName();

    private EditText mFilenameField;
    private final DatabaseManager mDatabaseManager = DatabaseManager.INSTANCE;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        mFilenameField = (EditText) getActivity().findViewById(R.id.edit_filename);

        Button btn_save = (Button) getActivity().findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeEntryList();
            }
        });

        Button btn_cancel = (Button) getActivity().findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPrevFragment();
            }
        });
    }

    private void storeEntryList() {
        String filename = mFilenameField.getText().toString();
        try {
            mDatabaseManager.saveDatabase(getActivity(), filename);
            getPrevFragment();
            showNotification(getResources().getString(R.string.msg_databaseSuccessStore));
        }
        catch (DatabaseException e) {
            Log.e(TAG, "Unable to store list", e);
            String err = getResources().getString(R.string.msg_databaseInvalidStore);
            showNotification(String.format(err, e.getMessage()));
        }
    }
}
