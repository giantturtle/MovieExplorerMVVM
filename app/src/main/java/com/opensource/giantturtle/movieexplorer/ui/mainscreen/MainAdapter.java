package com.opensource.giantturtle.movieexplorer.ui.mainscreen;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.opensource.giantturtle.movieexplorer.R;
import com.opensource.giantturtle.movieexplorer.data.database.ModelCachedMovie;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CachedViewHolder> {
    private List<ModelCachedMovie> cachedMoviesList = new ArrayList<>();
    private ItemClickCallback clickCallback;
    private Resources resources;

    MainAdapter(ItemClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public CachedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        if (resources == null) {
            resources = itemView.getContext().getApplicationContext().getResources();
        }
        return new CachedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CachedViewHolder holder, int position) {
        ModelCachedMovie cachedMovie = cachedMoviesList.get(position);
        Glide.with(holder.itemView.getContext()).load(cachedMovie.getPosterPath()).into(holder.posterImage);
        holder.title.setText(cachedMovie.getTitle());
        holder.releaseDate.setText(resources.getString(R.string.released, cachedMovie.getPrettyReleaseDate()));
        holder.averageVote.setText(Float.toString(cachedMovie.getVoteAverage()));
        //show more button when scrolled down to the last repo item at the end of recyclerview
        if (position == cachedMoviesList.size() - 1) {
            holder.showMoreButton.setVisibility(View.VISIBLE);
        } else {
            holder.showMoreButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cachedMoviesList.size();
    }

    private ModelCachedMovie getCurrentRowInfo(int position) {
        return cachedMoviesList.get(position);
    }

    public void setList(List<ModelCachedMovie> cachedMoviesList) {
        this.cachedMoviesList = cachedMoviesList;
        notifyDataSetChanged();
    }

    class CachedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView  title, releaseDate,  averageVote;
        private Button showMoreButton, actionButton;
        private ImageView posterImage;
        private CardView entireRowCardView;

        CachedViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            releaseDate = itemView.findViewById(R.id.released_date_tv);
            averageVote = itemView.findViewById(R.id.vote_average_tv);
            posterImage = itemView.findViewById(R.id.poster_iv);
            actionButton = itemView.findViewById(R.id.action_button);
            showMoreButton = itemView.findViewById(R.id.show_more_button);
            showMoreButton.setOnClickListener(this);
            entireRowCardView = itemView.findViewById(R.id.card_view_entire_row);
            entireRowCardView.setOnClickListener(this);
            actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bookmarks_24dp, 0, 0, 0);
            if (resources == null) {
                resources = itemView.getResources();
            }
            actionButton.setText(resources.getString(R.string.bookmark_button_text));
            actionButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ModelCachedMovie currentProject = getCurrentRowInfo(getAdapterPosition());

            switch (v.getId()) {
                case R.id.card_view_entire_row:
                    clickCallback.onRowClicked(currentProject, MainClickType.GO_TO_DETAILS);
                    break;
                case R.id.action_button:
                    clickCallback.onRowClicked(currentProject, MainClickType.BOOKMARK_PROJECT);
                    break;
                case R.id.show_more_button:
                    clickCallback.onRowClicked(currentProject, MainClickType.SHOW_MORE);
                    break;
                default:
                    break;
            }
        }
    }

    public interface ItemClickCallback {
        void onRowClicked(ModelCachedMovie cachedGitHubProjectModel, MainClickType mainClickType);
    }

    public enum MainClickType {
        BOOKMARK_PROJECT,
        SHOW_MORE,
        GO_TO_DETAILS
    }
}
