package com.powerpoint45.dtube;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

/**
 * Created by michael on 12/11/17.
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private VideoArrayList videos;
    Activity c;

    ChannelAdapter(VideoArrayList list, Activity activity){
        this.videos = list;
        c = activity;
    }

    public void setVideos(VideoArrayList videos){
        this.videos = videos;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView thumbView;
        private TextView titleView;
        private TextView priceView;
        private RelativeTimeTextView timeView;
        private TextView userView;
        private LinearLayout itemView;

        ViewHolder(LinearLayout v) {
            super(v);
            itemView = v;
            thumbView = ((ImageView)v.findViewById(R.id.item_image));
            titleView = ((TextView)v.findViewById(R.id.item_title));
            timeView = ((RelativeTimeTextView)v.findViewById(R.id.item_time));
            priceView = ((TextView)v.findViewById(R.id.item_value));
            userView = ((TextView)v.findViewById(R.id.item_user));
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggested_item, parent, false);
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ChannelAdapter.ViewHolder holder, int position) {
        holder.titleView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c instanceof ChannelActivity)
                    ((ChannelActivity)c).onItemClick((Integer) ((ViewHolder) v.getTag()).titleView.getTag());
                else if (c instanceof SearchActivity)
                    ((SearchActivity)c).onItemClick((Integer) ((ViewHolder) v.getTag()).titleView.getTag());
            }
        });

        holder.titleView.setText(videos.get(position).title);
        holder.timeView.setReferenceTime(videos.get(position).getDate());
        if (videos.get(position).price==null)
            holder.priceView.setVisibility(View.GONE);
        else
            holder.priceView.setVisibility(View.VISIBLE);
        holder.priceView.setText(videos.get(position).price);
        holder.userView.setText(videos.get(position).user);
        holder.itemView.setTag(holder);


        Picasso.with(c).load(videos.get(position).getImageURL()).placeholder(R.drawable.ic_ondemand_video).resize(400,400).centerInside().into(
                holder.thumbView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (videos == null)
            return 0;
        else {
            return videos.size();
        }

    }
}
