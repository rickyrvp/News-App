package com.android.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private NewsAdapter newsAdapter;
    private static final String REQUEST_URL = "http://content.guardianapis.com/search";
    private TextView emptyTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        ListView listView = (ListView) findViewById(R.id.list);
        emptyTextView = (TextView) findViewById(R.id.empty_text);
        listView.setEmptyView(emptyTextView);
        listView.setAdapter(newsAdapter);
        progressBar = findViewById(R.id.progress_bar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                News current = newsAdapter.getItem(position);

                Uri news_Uri = Uri.parse(current.getWebURL());

                Intent website = new Intent(Intent.ACTION_VIEW, news_Uri);
                startActivity(website);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);

        } else {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("There is no internet Connection. Please try Again");
        }

    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", "test");

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        newsAdapter.clear();
        progressBar.setVisibility(View.GONE);
        emptyTextView.setText(R.string.error_message);

        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }

}