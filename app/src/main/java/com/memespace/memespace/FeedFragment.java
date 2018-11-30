package com.memespace.memespace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.Arrays;
import java.util.List;

/**
 * A fragment for the main feed
 */
public class FeedFragment extends Fragment {

    public FeedFragment() {
    }

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView feedRecycler = rootView.findViewById(R.id.feed_recycler);

        List<String> urls = Arrays.asList(
                "https://storage.googleapis.com/memes2018/1542456981909alphameme.jpg",
                "https://storage.googleapis.com/memes2018/1542457080605dankness.jpg",
                "https://storage.googleapis.com/memes2018/154246604502944944688_461581084246475_6183301971798130688_n.jpg",
                "https://storage.googleapis.com/memes2018/1542572527562your-meme.png");
        List<String> captions = Arrays.asList(
                "The first meme ever invented",
                "Wow would you look at this",
                "How could they get any better?",
                "Good stuff");
        feedRecycler.setAdapter(new MemeRecyclerAdapter(urls, captions));
        feedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        return rootView;
    }

    public class MemeRecyclerAdapter extends RecyclerView.Adapter<MemeRecyclerAdapter.MemeViewHolder> {

        List<String> urls;
        List<String> captions;

        public MemeRecyclerAdapter(List<String> urls, List<String> captions) {
            this.urls = urls;
            this.captions = captions;
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
            holder.caption.setText(captions.get(position));
            Glide.with(getContext())
                    .load(urls.get(position))
                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .apply(new RequestOptions().placeholder())
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return urls.size();
        }

        public class MemeViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView caption;

            public MemeViewHolder(View v) {
                super(v);
                imageView = v.findViewById(R.id.meme_image);
                caption = v.findViewById(R.id.meme_caption);
            }
        }
    }
}
