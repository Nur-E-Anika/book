package com.example.anan.project;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class openMessage extends AppCompatActivity {

    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_message);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);


            Firebase.setAndroidContext(this);
            reference1 = new Firebase("https://project-22aa1.firebaseio.com/msg/"+UserDetails.bookRef);


            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageText = messageArea.getText().toString();

                    if(!messageText.equals("")){
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("message", messageText);
                        map.put("user",  Objects.requireNonNull(FirebaseAuth.getInstance()
                                .getCurrentUser()).getDisplayName());
                        reference1.push().setValue(map);



                    }
                    messageArea.setText("");
                }
            });

            reference1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Map map = dataSnapshot.getValue(Map.class);
                    String message = map.get("message").toString();
                    String userName = map.get("user").toString();

                    Log.e("ChatActivity",  userName + ", " + message);


                    addMessageBox(userName ,  message, 1);

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
        }

    public void addMessageBox(String user,String message, int type){
        TextView textView = new TextView(openMessage.this);
        TextView tv=new TextView(openMessage.this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(user);
        tv.setText(message+"\n");

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 5;
        lp2.bottomMargin=3;

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.weight = 5;

        if(type == 1) {
            lp2.gravity = Gravity.CENTER;
            lp1.gravity=Gravity.CENTER;
            textView.setTextSize(19);
            tv.setTextSize(19);
            textView.setBackgroundResource(R.color.cardview_light_background);
            tv.setBackgroundResource(R.color.cardview_light_background);
        }

        tv.setLayoutParams(lp2);
        textView.setLayoutParams(lp1);
        layout.addView(textView);
        layout.addView(tv);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}