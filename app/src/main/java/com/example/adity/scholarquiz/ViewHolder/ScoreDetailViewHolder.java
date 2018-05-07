package com.example.adity.scholarquiz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.adity.scholarquiz.R;

public class ScoreDetailViewHolder extends RecyclerView.ViewHolder {
    public TextView text_name,text_score;

    public ScoreDetailViewHolder(View itemView) {
        super(itemView);

        text_name = (TextView)itemView.findViewById(R.id.text_name);

        text_score = (TextView)itemView.findViewById(R.id.text_score);
    }
}
