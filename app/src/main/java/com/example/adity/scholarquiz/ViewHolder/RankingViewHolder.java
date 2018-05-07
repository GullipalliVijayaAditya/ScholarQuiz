package com.example.adity.scholarquiz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.adity.scholarquiz.Interface.ItemClickListener;
import com.example.adity.scholarquiz.R;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView text_name, text_score;

    private ItemClickListener itemClickListener;

    public RankingViewHolder(View itemView) {
        super(itemView);
        text_name = (TextView) itemView.findViewById(R.id.text_name);
        text_score = (TextView) itemView.findViewById(R.id.text_score);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);

    }
}
