package com.github.thealexhong.practice2.fragment;

import android.app.Fragment;
import android.widget.Toast;

import com.github.thealexhong.practice2.R;

/**
 * Base class for other fragments
 */
public class MyFragment extends Fragment {

    /**
     * Switch fragments
     * @param frag    switch current fragment with frag
     */
    protected void swapFragment(Fragment frag) {
        getActivity().getFragmentManager()
                .beginTransaction().replace(R.id.fragment_container, frag)
                .addToBackStack(null).commit();
    }

    /**
     * Switch to previous fragment
     */
    protected void getPrevFragment () {
        getFragmentManager().popBackStack();
    }

    /**
     * Shows message box inside the fragment
     * @param msg
     */
    protected void showNotification (String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
