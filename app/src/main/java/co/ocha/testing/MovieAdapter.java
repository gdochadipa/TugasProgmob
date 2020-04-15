package co.ocha.testing;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<MovieResultsItem> resultsItems;


    public MovieAdapter(List<MovieResultsItem> list, Context context){
        this.resultsItems=list;
        this.context = context;
    }

    public void setResultsItems(List<MovieResultsItem> resultsItems){
        this.resultsItems=resultsItems;
    }

    public List<MovieResultsItem> getResultsItems(){
        return resultsItems;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int i) {
        holder.title.setText(getResultsItems().get(i).getTitle());
        holder.score.setText(String.valueOf(getResultsItems().get(i).getVoteAverage()));
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500"+ getResultsItems().get(i).getPosterPath())
                .apply(new RequestOptions().override(250,300))
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {

        return getResultsItems().size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView score;
        private ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_i);
            poster = itemView.findViewById(R.id.poster_i);
            score = itemView.findViewById(R.id.score_i);
        }
    }
}
