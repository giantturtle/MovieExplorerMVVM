package com.opensource.giantturtle.movieexplorer.ui.savedscreen;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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
import com.opensource.giantturtle.movieexplorer.data.database.ModelSavedMovie;

import java.util.ArrayList;
import java.util.List;

public class SavedMoviesAdapter extends RecyclerView.Adapter<SavedMoviesAdapter.SavedViewHolder> {
    private List<ModelSavedMovie> savedMoviesList = new ArrayList<>();
    private IClickCallback clickCallback;
    private Resources resources;

    SavedMoviesAdapter(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        if (resources == null) {
            resources = itemView.getContext().getApplicationContext().getResources();
        }
        return new SavedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        final ModelSavedMovie savedMovie = savedMoviesList.get(position);
        Glide.with(holder.itemView.getContext()).load(savedMovie.getPosterPath()).into(holder.posterImage);
        holder.title.setText(savedMovie.getTitle());
        holder.releaseDate.setText(resources.getString(R.string.released, savedMovie.getPrettyReleaseDate()));
        holder.averageVote.setText(Float.toString(savedMovie.getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return savedMoviesList.size();
    }

    public void setList(List<ModelSavedMovie> savedMoviesList) {
        //if it is initial filling of the list
        if (this.savedMoviesList == null) {
            this.savedMoviesList = savedMoviesList;
            notifyDataSetChanged();
        } else { //if it is something deleted or added to list, need to calculate
            final List<ModelSavedMovie> oldTempList = this.savedMoviesList;
            final List<ModelSavedMovie> newTempList = savedMoviesList;

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return oldTempList.size();
                }

                @Override
                public int getNewListSize() {
                    return newTempList.size();
                }

                @Override
                public boolean areItemsTheSame(int i, int i1) {
                    return oldTempList.get(i).getPrimKey() ==
                            newTempList.get(i1).getPrimKey();
                }

                @Override
                public boolean areContentsTheSame(int i, int i1) {
                    ModelSavedMovie oldSavedGitHubProject = oldTempList.get(i);
                    ModelSavedMovie newSavedGitHubProject = newTempList.get(i1);
                    return oldSavedGitHubProject.equals(newSavedGitHubProject);
                }
            });
            result.dispatchUpdatesTo(this);
            this.savedMoviesList = savedMoviesList;
        }
    }

    private ModelSavedMovie getCurrentRowInfo(int position) {
        return savedMoviesList.get(position);
    }

    class SavedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView  title, releaseDate,  averageVote;
        Button  actionButton;
        ImageView posterImage;
        CardView entireRowCardView;

        SavedViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            releaseDate = itemView.findViewById(R.id.released_date_tv);
            averageVote = itemView.findViewById(R.id.vote_average_tv);
            posterImage = itemView.findViewById(R.id.poster_iv);
            actionButton = itemView.findViewById(R.id.action_button);
            entireRowCardView = itemView.findViewById(R.id.card_view_entire_row);
            entireRowCardView.setOnClickListener(this);
            actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete_24dp, 0, 0, 0);
            actionButton.setText(resources.getString(R.string.delete_button_text));
            actionButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            ModelSavedMovie currentProject = getCurrentRowInfo(getAdapterPosition());

            switch (v.getId()) {
                case R.id.card_view_entire_row:
                    clickCallback.onRowClicked(currentProject, ClickActionSaved.GO_TO_DETAILS);
                    break;
                case R.id.action_button:
                    clickCallback.onRowClicked(currentProject, ClickActionSaved.DELETE_SAVED);
                    break;
                default:
                    break;
            }
        }
    }

    public interface IClickCallback {
        void onRowClicked(ModelSavedMovie savedGitHubProjectModel, ClickActionSaved clickActionSaved);
    }

    public enum ClickActionSaved {
        DELETE_SAVED,
        GO_TO_DETAILS
    }
}


