package com.memespace.memespace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A fragment for the main feed
 */
public class FeedFragment extends Fragment {

    DatabaseHelper db;

    public FeedFragment() {
        db = new FirebaseHelper();
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final RecyclerView feedRecycler = rootView.findViewById(R.id.feed_recycler);
        // allow inertial scrolling from NestedScrollView instead of RecyclerView scrolling
        // this has to be done programmatically to support API < 21
        ViewCompat.setNestedScrollingEnabled(feedRecycler, false);

        db.getAllMemes(new DatabaseHelper.Callback() {
            @Override
            public void onMemesRetrieved(List<Meme> memes) {
                feedRecycler.setAdapter(new MemeRecyclerAdapter(memes));
            }
        });

        feedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        return rootView;
    }

    public class MemeRecyclerAdapter extends RecyclerView.Adapter<MemeRecyclerAdapter.MemeViewHolder> {

        private List<Meme> memes;

        public MemeRecyclerAdapter(List<Meme> memes) {
            this.memes = memes;
        }

        @NonNull
        @Override
        public MemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_meme, parent, false);
            MemeViewHolder mvh = new MemeViewHolder(v);
            return mvh;
        }

        @Override
        public void onBindViewHolder(@NonNull MemeViewHolder holder, int position) {
            Meme meme = memes.get(position);
            holder.caption.setText(meme.getName());
            holder.likes.setText(String.format(Locale.getDefault(),"%d likes", meme.getRating()));
            holder.username.setText(meme.getUser() == null ? "anonymous" : meme.getUser());
            Glide.with(getContext())
                    .load(meme.getUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .apply(new RequestOptions().placeholder())
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return memes.size();
        }

        public class MemeViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView caption;
            TextView likes;
            TextView username;

            public MemeViewHolder(View v) {
                super(v);
                imageView = v.findViewById(R.id.meme_image);
                caption = v.findViewById(R.id.meme_caption);
                likes = v.findViewById(R.id.meme_likes);
                username = v.findViewById(R.id.meme_username);
            }
        }
    }
}
