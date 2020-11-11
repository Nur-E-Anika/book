package com.example.anan.project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class admin extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);

        listView = (ListView)findViewById(R.id.bookList);

        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, UserDetails.al){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.WHITE);

                textView.setTextSize(21);



                return view;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserDetails.bookRef=UserDetails.al.get(i);
                UserDetails.BookName=UserDetails.bl.get(i);
                startActivity(new Intent(admin.this,bookView.class));

            }
        });


    }
}
