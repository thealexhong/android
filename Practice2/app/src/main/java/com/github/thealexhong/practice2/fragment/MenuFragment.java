package com.github.thealexhong.practice2.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.thealexhong.practice2.R;
import com.github.thealexhong.practice2.record.DatabaseManager;

public class MenuFragment extends MyFragment {

    private final DatabaseManager mDatabaseManager = DatabaseManager.INSTANCE;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHeader();
        setEntryBtn();
        setViewBtn();
        setStoreBtn();
        setLoadBtn();
        setExitBtn();
    }

    private void setHeader() {
        TextView header = (TextView) getActivity().findViewById(R.id.view_myList);
        header.setText(String.format(getResources().getString(R.string.view_myList), mDatabaseManager.getDatabase().getFilename()));
        if(mDatabaseManager.hasUnsavedChanges())
        {
            header.setText(header.getText() + "*");
        }
    }

    private void setEntryBtn() {
        Button btn = (Button) getActivity().findViewById(R.id.btn_entry);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFragment(new EntryFragment());
            }
        });
    }

    private void setStoreBtn() {
        Button btn = (Button) getActivity().findViewById(R.id.btn_store);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFragment(new StoreFragment());
            }
        });
    }

    private void setViewBtn() {
        Button btn = (Button) getActivity().findViewById(R.id.btn_view);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("bundle_id", MenuFragment.this.mDatabaseManager.getDatabase().toJSON());
                ViewFragment fragment = new ViewFragment();
                fragment.setArguments(bundle);
                swapFragment(fragment);
            }
        });
    }

    private void setLoadBtn() {
        Button btn = (Button) getActivity().findViewById(R.id.btn_load);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFragment(new LoadFragment());
            }
        });
    }

    private void setExitBtn() {
        Button btn = (Button) getActivity().findViewById(R.id.btn_exit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MenuFragment.this.mDatabaseManager.hasUnsavedChanges()) {
                    getActivity().finish();
                }
                else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Unsaved changes")
                            .setMessage(getResources().getString(R.string.dialog_unsaved))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
    }
}
