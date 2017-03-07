package com.ampme.challenge.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ampme.challenge.R;
import com.ampme.challenge.model.YoutubeSnippet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.myinnos.alphabetsindexfastscrollrecycler.utilities_fs.StringMatcher;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.SnippetHolder> implements SectionIndexer {

    private final ArrayList<YoutubeSnippet> mYoutubeSnippets;
    private Context mContext;
    private final String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public MusicAdapter(Context context, ArrayList<YoutubeSnippet> youtubeSnippets) {
        mContext = context;
        mYoutubeSnippets = youtubeSnippets;
    }

    @Override
    public SnippetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_row, null);
        return new SnippetHolder(view);
    }

    @Override
    public void onBindViewHolder(SnippetHolder holder, int position) {
        YoutubeSnippet youtubeSnippet = mYoutubeSnippets.get(position);

        if (youtubeSnippet.getYoutubeThumbnails() != null && !TextUtils.isEmpty(youtubeSnippet.getYoutubeThumbnails().get("high").getUrl())) {
            Picasso.with(mContext).load(youtubeSnippet.getYoutubeThumbnails().get("high").getUrl())
                    .placeholder(R.drawable.playlist_placeholder)
                    .into(holder.coverImage);
        }
        holder.title.setText(youtubeSnippet.getTitle());
        holder.description.setText(youtubeSnippet.getDescription());
    }


    @Override
    public int getItemCount() {
        return mYoutubeSnippets.size();
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        //TODO make comparaison for letter with accent.
        // If there is no item for current section, previous section will be selected
        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getItemCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(mYoutubeSnippets.get(j).getTitle().toUpperCase().charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(mYoutubeSnippets.get(j).getTitle().toUpperCase().charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    class SnippetHolder extends RecyclerView.ViewHolder {

        final ImageView coverImage;
        final TextView title;
        final TextView description;

        SnippetHolder(View itemView) {
            super(itemView);
            coverImage = (ImageView) itemView.findViewById(R.id.cover_image);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
