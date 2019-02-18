package com.example.android.popularmovies2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies2.R;
import com.example.android.popularmovies2.models.Reviews;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    Context context;
    private ArrayList<Reviews> mReviewsList;

    public ReviewsAdapter(ArrayList<Reviews> reviews) {
        mReviewsList = reviews;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        boolean attachToParentImmediately = false;
        context = viewGroup.getContext();
        View view = inflater.inflate(R.layout.reviews_list_item, viewGroup, attachToParentImmediately);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsViewHolder reviewsViewHolder, int i) {
        Reviews reviews = mReviewsList.get(i);
        reviewsViewHolder.reviewerName.setText(reviews.getAuthor());
        reviewsViewHolder.reviewContent.setText(reviews.getContent());
    }

    @Override
    public int getItemCount() {
        if (mReviewsList == null)
            return 0;

        return mReviewsList.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        final TextView reviewerName;
        final TextView reviewContent;

        ReviewsViewHolder(View view) {
            super(view);
            reviewerName = view.findViewById(R.id.tv_reviewer_name);
            reviewContent = view.findViewById(R.id.tv_review_content);
        }
    }
}