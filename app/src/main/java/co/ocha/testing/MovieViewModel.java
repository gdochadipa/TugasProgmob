package co.ocha.testing;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<MovieResultsItem>> listMovie = new MutableLiveData<>();
    private Gson gson = new Gson();

    void setMovieData(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=78502e880981d58974d3322176715ee3&language=en-US&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String data = new String(responseBody);
                    MovieModelResponse modelResponse = gson.fromJson(data,MovieModelResponse.class);
                    listMovie.postValue(modelResponse.getResults());

                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Log.d("onFailure", e.getMessage());
            }
        });
    }

    public LiveData<List<MovieResultsItem>> getMovieData(){
        return listMovie;
    }

}
