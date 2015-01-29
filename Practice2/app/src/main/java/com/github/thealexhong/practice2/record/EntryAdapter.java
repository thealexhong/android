package com.github.thealexhong.practice2.record;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.thealexhong.practice2.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EntryAdapter extends ArrayAdapter<Displayable> {

    private final Map<Displayable, Integer> mEntriesMap = new HashMap<Displayable, Integer>();

    public EntryAdapter(Context context, List<Displayable> objects) {
        super(context, R.layout.fragment_list, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mEntriesMap.put(objects.get(i), i);
        }
    }

    static class ViewHolderItem {
        TextView view_header;
        TextView view_detail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.fragment_list, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.view_header = (TextView) convertView.findViewById(R.id.view_listHeader);
            viewHolder.view_detail = (TextView) convertView.findViewById(R.id.view_listDetail);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Displayable item = getItem(position);
        if (item != null) {
            viewHolder.view_header.setText(item.getHeader(getContext()));
            viewHolder.view_detail.setText(item.getDetail(getContext()));
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        Displayable item = getItem(position);
        return mEntriesMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
