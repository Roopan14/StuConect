package com.example.roopan.stuconnect;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Roopan on 07-01-2017.
 */

public class PostListt extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<PostPOJO> postList;

    public PostListt(Activity activity, List<PostPOJO> postList) {
        this.activity = activity;
        this.postList = postList;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int i) {
        return postList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.feed,null);

        TextView name = (TextView) view1.findViewById(R.id.postname);
        TextView timestamp = (TextView) view1
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) view1
                .findViewById(R.id.txtStatusMsg);

        PostPOJO post = postList.get(i);
        name.setText(post.getName());
        timestamp.setText(post.getTime());
        statusMsg.setText(post.getPost());

        return view1;
    }
}
