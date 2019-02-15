package com.example.android.popularmovies2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.models.Trailers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {
    private ArrayList<Trailers> mTrailersList;
    final private TrailerClickListener mTrailerClick;
    Context context;

    public interface TrailerClickListener {
        void onItemClick(Trailers trailers);
    }

    public TrailersAdapter(ArrayList<Trailers> trailers, TrailerClickListener clickListener) {
        mTrailersList = trailers;
        mTrailerClick = clickListener;}


    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean attachToParentImmediately = false;
        context = viewGroup.getContext();
        View view = inflater.inflate(R.layout.trailers_list_item, viewGroup, attachToParentImmediately);
        return new TrailersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.TrailersViewHolder trailersViewHolder, int i) {
Trailers trailers = mTrailersList.get(i);
trailersViewHolder.trailerName.setText(trailers.getTrailerName());
trailersViewHolder.trailerSite.setText(trailers.getTrailerSite());
        String moviepreview = trailers.getTrailerImage();
        Picasso.get().load(moviepreview).into(trailersViewHolder.trailerpreview);

    }

    @Override
    public int getItemCount() {
        if (mTrailersList == null)
            return 0;
        return mTrailersList.size();
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        final TextView trailerName;
        final TextView trailerSite;
        final ImageView trailerpreview;


        TrailersViewHolder (View itemView){
            super(itemView);

            trailerName = itemView.findViewById(R.id.tv_trailer_name);
            trailerSite = itemView.findViewById(R.id.tv_trailer_site);
            trailerpreview = itemView.findViewById(R.id.iv_trailer_preview);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Trailers trailers = mTrailersList.get(position);
            mTrailerClick.onItemClick(trailers);

        }
    }
}
