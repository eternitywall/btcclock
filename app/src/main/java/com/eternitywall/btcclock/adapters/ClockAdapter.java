package com.eternitywall.btcclock.adapters;

import android.content.Context;

import com.eternitywall.btcclock.Clock;

import java.util.List;

public class ClockAdapter extends RadioAdapter<Clock> {
    public ClockAdapter(Context context, List<Clock> items) {
        super(context, items);
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        viewHolder.mText.setText(mItems.get(i).name);
    }
}