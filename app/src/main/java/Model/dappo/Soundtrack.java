package Model.dappo;

/**
 * Created by das953 on 14-Dec-17.
 */

import android.graphics.Bitmap;

import java.io.File;

/**
 *  All info about soundtrack
 */
public class Soundtrack {

    private String name;

    private String bitrate;
    private String duration;
    private String author;
    private String tittle;
    private String artist;
    private String album;
    private Bitmap image;
    private String date;

    public Soundtrack(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
