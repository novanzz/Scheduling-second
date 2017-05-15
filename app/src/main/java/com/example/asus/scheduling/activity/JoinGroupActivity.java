package com.example.asus.scheduling.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.scheduling.R;
import com.example.asus.scheduling.adapter.JoinGrupAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

//recycleview untuk JoinGrup
public class JoinGroupActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<User> result;
    private JoinGrupAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_activity);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("User");

        result = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.user_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lin);

        adapter = new JoinGrupAdapter(result);
        recyclerView.setAdapter(adapter);

        updateList();
    }

// buat upadate realtime  klo database nya di hapus nanti di list nya ikut ke apus

    private void updateList(){

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                result.add(dataSnapshot.getValue(User.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User model = dataSnapshot.getValue(User.class);
                int index = getItemIndex(model);

                result.set(index,model);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private int getItemIndex(User user) {
        int index = -1;

        for (int i = 0; i < result.size();i++){
            if (result.get(i).key.equals(user.key)){
                index = i;
                break;
            }
        }
        return index;
    }
}
