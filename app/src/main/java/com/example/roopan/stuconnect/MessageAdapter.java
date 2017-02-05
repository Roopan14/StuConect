package com.example.roopan.stuconnect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Roopan on 30-01-2017.
 */

public class MessageAdapter extends ArrayAdapter<MessagePOJO> {

    public MessageAdapter(Context context, ArrayList<MessagePOJO> messagePOJOArrayList)
    {
        super(context,0,messagePOJOArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.chateach,parent,false);
        TextView textView = (TextView) convertView.findViewById(R.id.chattext);

        MessagePOJO messagePOJO = getItem(position);
        String name = messagePOJO.getName();
        String message = messagePOJO.getMessage();
        textView.setText(message);
        return convertView;
    }
}
