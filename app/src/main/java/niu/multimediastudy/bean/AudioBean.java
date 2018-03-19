package niu.multimediastudy.bean;

/**
 * Created by Qinlian_niu on 2018/3/14.
 */

public class AudioBean {

    private String data;
    private int id;
    private String title;
    private String diplay_name;
    private String mime_type;
    private String artist;
    private String album;
    private int is_alarm;
    private int is_music;
    private int is_notification;
    private int is_ringtone;
    private int size;
    private int date_added;
    private int date_modified;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiplay_name() {
        return diplay_name;
    }

    public void setDiplay_name(String diplay_name) {
        this.diplay_name = diplay_name;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
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

    public int getIs_alarm() {
        return is_alarm;
    }

    public void setIs_alarm(int is_alarm) {
        this.is_alarm = is_alarm;
    }

    public int getIs_music() {
        return is_music;
    }

    public void setIs_music(int is_music) {
        this.is_music = is_music;
    }

    public int getIs_notification() {
        return is_notification;
    }

    public void setIs_notification(int is_notification) {
        this.is_notification = is_notification;
    }

    public int getIs_ringtone() {
        return is_ringtone;
    }

    public void setIs_ringtone(int is_ringtone) {
        this.is_ringtone = is_ringtone;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDate_added() {
        return date_added;
    }

    public void setDate_added(int date_added) {
        this.date_added = date_added;
    }

    public int getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(int date_modified) {
        this.date_modified = date_modified;
    }

    @Override
    public String toString() {
        return "AudioBean{" +
                "data='" + data + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", diplay_name='" + diplay_name + '\'' +
                ", mime_type='" + mime_type + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", is_alarm=" + is_alarm +
                ", is_music=" + is_music +
                ", is_notification=" + is_notification +
                ", is_ringtone=" + is_ringtone +
                ", size=" + size +
                ", date_added=" + date_added +
                ", date_modified=" + date_modified +
                '}';
    }
}
