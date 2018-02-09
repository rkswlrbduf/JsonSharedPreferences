package com.example.jsonsharedpreferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView textView;
    String text = "";
    ArrayList<Friend> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textView);

        friends.add(new Friend("John", 13));
        friends.add(new Friend("Sam", 30));
        friends.add(new Friend("Kim", 25));
        friends.add(new Friend("Smith", 20));

        for (Friend friend : ReadFriendsData()) {
            text += friend.name + " : " + friend.age + "\n";
        }
        textView.setText(text);

    }

    @Override
    public void onClick(View v) {
        SaveFriendData(friends);
    }

    private void SaveFriendData(ArrayList<Friend> friends) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(friends);
        editor.putString("MyFriends", json);
        editor.commit();
    }

    private ArrayList<Friend> ReadFriendsData() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("MyFriends", "EMPTY");
        Type type = new TypeToken<ArrayList<Friend>>() {
        }.getType();
        ArrayList<Friend> arrayList = gson.fromJson(json, type);
        return arrayList;
    }

}
