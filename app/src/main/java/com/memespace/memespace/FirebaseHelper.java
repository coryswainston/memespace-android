package com.memespace.memespace;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Database helper for a Firebase implementation
 */

public class FirebaseHelper implements DatabaseHelper {
    private static final String TAG = "FirebaseHelper";
    private static final String KEY_MEMES = "memes";

    private FirebaseDatabase database;

    public FirebaseHelper() {
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void getAllMemes(final Callback callback) {
        DatabaseReference dbRef = database.getReference(KEY_MEMES);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Meme> memes = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Meme m = child.getValue(Meme.class);
                    memes.add(m);
                }
                // hack until we figure out how to query better
                List<Meme> reversed = new ArrayList<>();
                for (int i = memes.size() - 1; i >= 0; i--) {
                    reversed.add(memes.get(i));
                }
                memes = reversed;

                callback.onMemesRetrieved(memes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
