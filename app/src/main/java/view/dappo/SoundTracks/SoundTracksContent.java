package view.dappo.SoundTracks;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class SoundTracksContent {

    /**
     * An array of sample (Soundtrack) items.
     */
    public static final List<SoundtrackItem> ITEMS = new ArrayList<SoundtrackItem>();

    /**
     * A map of sample (Soundtrack) items, by ID.
     */
    public static final Map<String, SoundtrackItem> ITEM_MAP = new HashMap<String, SoundtrackItem>();

    private static void addItem(SoundtrackItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.SongName, item);
    }



    /**
     * A Soundtrack item representing a piece of content.
     */
    public static class SoundtrackItem {
        public final String SongName;
        public final String SongArtistAlbum;

        public final String Name;
        public final int index;

        public final Drawable SoundtrackIcon;
        //public final Drawable SoundTrackPreferences;

        public SoundtrackItem(String SongName, String SongArtistAlbum,
                              Drawable SoundtrackIcon, String Name, int index) {     //, Drawable  SoundTrackPreferences
            this.SongName = SongName;
            this.SongArtistAlbum = SongArtistAlbum;
            this.Name = Name;
            this.index = index;

            this.SoundtrackIcon = SoundtrackIcon;
            //this.SoundTrackPreferences = SoundTrackPreferences;


        }

        @Override
        public String toString() {
            return SongName + ": " + SongArtistAlbum ;
        }
    }
}
