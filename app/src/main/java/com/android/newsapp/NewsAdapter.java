package com.android.newsapp;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is an existing list item view we can reuse,
        // otherwise inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the news object at the given position in the list
        News currentNews = getItem(position);

        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);
        sectionView.setText(currentNews.getSection());

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentNews.getTitle());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        dateView.setText(convertDateFormat(currentNews.getDate()));

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        timeView.setText(convertTimeFormat(currentNews.getDate()));

        return listItemView;
    }

    // Convert json DateTime to Date and Time
    public String convertDateFormat(String input) {
        input = input.substring(0, input.length() - 1);
        String oldFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String newFormat = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(oldFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(newFormat);
        Date date = null;
        String output = "";
        try {
            date = inputFormat.parse(input);
            output = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "DateTime parse exception: " + e);
        }
        return output;
    }

    public String convertTimeFormat(String input) {
        input = input.substring(0, input.length() - 1);
        String oldFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String newFormat = "HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(oldFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(newFormat);
        Date date = null;
        String output = "";
        try {
            date = inputFormat.parse(input);
            output = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "DateTime parse exception: " + e);
        }
        return output;
    }

}
