package com.example.anan.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class favourite extends AppCompatActivity {

    ArrayList<String> al = new ArrayList<>();
    ProgressDialog pd;

    ArrayList<String> listKeys = new ArrayList<>();

    ListView listView;
    Button rmvBtn;
    Button readBtn;
    Boolean itemSel = false;
    String selPos ;
    Firebase ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        final ArrayAdapter<String>arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, al);


        listView = (ListView)findViewById(R.id.listView);
        rmvBtn = (Button) findViewById(R.id.rmvBtn);
        readBtn = (Button)findViewById(R.id.readBtn);

        pd = new ProgressDialog(favourite.this);
        pd.setMessage("Loading...");
        pd.show();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            UserDetails.uid = user.getUid();
        }


        final String url = "https://project-22aa1.firebaseio.com/Favourite/"+UserDetails.uid+".json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(arrayAdapter);

                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                pd.dismiss();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(favourite.this);
        rQueue.add(request);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.bookRef= al.get(position);


                selPos = arrayAdapter.getItem(position);
                itemSel = true;
            }
        });

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://project-22aa1.firebaseio.com/Favourite/"+UserDetails.uid);



        final ChildEventListener eventlis = (new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                //UserDetails.BookName=(String)dataSnapshot.child(UserDetails.bookRef).child("genre").getValue();
                //arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);


                al.remove(index);
                listKeys.remove(index);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        ref.addChildEventListener(eventlis);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest request1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject obj1 = new JSONObject(s);

                            UserDetails.BookName = obj1.getJSONObject(UserDetails.bookRef).getString("genre");




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("" + volleyError);
                    }
                });

                RequestQueue rQueue1 = Volley.newRequestQueue(favourite.this);
                rQueue1.add(request1);

                startActivity(new Intent(favourite.this,bookView.class));
            }
        });



        rmvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                arrayAdapter.remove(selPos);

                ref.removeEventListener(eventlis);

                ref.child(UserDetails.bookRef).child("genre").removeValue();

            }
        });

    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                al.add(key);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
















       /*

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://project-22aa1.firebaseio.com/WantToRead/" + UserDetails.uid + UserDetails.name);


        listView = (ListView) findViewById(R.id.listView);


        final ArrayAdapter<String>arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                listItems);

        listView.setAdapter(arrayAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

       listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selPos = arrayAdapter.getItem(i);
                        itemSel = true;
                        UserDetails.s=listItems.get(i);
                        rmvBtn.setEnabled(true);
                        readBtn.setEnabled(true);
                    }
                });

        rmvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //arrayAdapter.remove(selPos);
            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = dataSnapshot.getValue(String.class);
                listItems.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

               String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);


                    listItems.remove(index);
                    listKeys.remove(index);
                    arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/













           /* ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserDetails.BookName=(String)dataSnapshot.child(UserDetails.bookRef).child("genre").getValue();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });/*(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                UserDetails.BookName = (String)dataSnapshot.child("genre").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Query query = ref.orderByChild("name").equalTo(UserDetails.bookRef);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserDetails.BookName=(String)dataSnapshot.getValue();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
*/


























