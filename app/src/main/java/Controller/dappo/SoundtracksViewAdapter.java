package Controller.dappo;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Model.dappo.PlayList;
import Model.dappo.Soundtrack;
import view.dappo.R;
import view.dappo.SoundtracksFragment.OnListFragmentInteractionListener;
import view.dappo.SoundTracks.SoundTracksContent.SoundtrackItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SoundtrackItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SoundtracksViewAdapter extends RecyclerView.Adapter<SoundtracksViewAdapter.ViewHolder> {

    private final List<SoundtrackItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Drawable dMenu;


    /*
      public SoundtrackItem(String SongName, String SongArtistAlbum,
                                     Drawable SoundtrackIcon)
     */
    public SoundtracksViewAdapter(PlayList<Soundtrack> items, OnListFragmentInteractionListener listener,
                                  Context context) {

        dMenu = ContextCompat.getDrawable(context, R.drawable.note_128);
        mValues = new ArrayList<>();

        int index = 0;
        for (Soundtrack soundtrack:
             items) {

            Drawable drawable = soundtrack.getImage() != null
                    ? soundtrack.getImage()
                    : dMenu;

            mValues.add(new SoundtrackItem(soundtrack.getTittle(),
                    soundtrack.getArtist() + ", " + soundtrack.getAlbum(),
                    drawable, soundtrack.getName(), index));

            index++;
        }

        MainController.setCurrentPlayList(items);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_soundtracks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvSongName.setText(mValues.get(position).SongName);
        holder.tvSongArtistAlbum.setText(mValues.get(position).SongArtistAlbum);
        holder.imSoundtrackIcon.setImageDrawable(mValues.get(position).SoundtrackIcon);
        //holder.imSoundTrackPreferences.setImageDrawable(mValues.get(position).SoundTrackPreferences);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvSongName;
        public final TextView tvSongArtistAlbum;
        public final ImageView imSoundTrackPreferences;
        public final ImageView imSoundtrackIcon;
        public SoundtrackItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvSongName = view.findViewById(R.id.tvSongName);
            tvSongArtistAlbum = view.findViewById(R.id.tvSongArtistAlbum);
            imSoundTrackPreferences = view.findViewById(R.id.imSoundTrackPreferences);
            imSoundtrackIcon = view.findViewById(R.id.imSoundtrackIcon);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvSongName.getText() +
                    " " + tvSongArtistAlbum.getText() + "'";
        }
    }
}
