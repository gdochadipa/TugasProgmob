package co.ocha.testing;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MencobaInterface{


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private MovieViewModel viewModel;
    private ArrayList<MovieResultsItem> listMovie = new ArrayList<>();
    public MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getId();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);

        viewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        viewModel.setMovieData();
        viewModel.getMovieData().observe(this, new Observer<List<MovieResultsItem>>() {
            @Override
            public void onChanged(List<MovieResultsItem> list) {
                if (list != null){

                    listMovie.addAll(list);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    refreshLayout.setRefreshing(false);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressBar.setVisibility(View.VISIBLE);
                viewModel.setMovieData();
            }
        });

        showRecycleList();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Hello World!!", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            toast("Hello, this is Interface");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void toast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    private void showRecycleList(){
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        adapter = new MovieAdapter(listMovie,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private void getId(){
        recyclerView = findViewById(R.id.list_movie);
        progressBar = findViewById(R.id.progress_bar_movie);
        refreshLayout = findViewById(R.id.swipe_refresh_movie);


    }


}
