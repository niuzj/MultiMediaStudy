package niu.multimediastudy.bean;

/**
 * Created by Qinlian_niu on 2018/3/7.
 */

public class ImageBean {
    private String display_name;
    private String description;
    private float date_added;
    private float date_taken;
    private String mime_type;
    private String data;
    private String title;
    private double lat;
    private double lon;
    private int orientation;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDate_added() {
        return date_added;
    }

    public void setDate_added(float date_added) {
        this.date_added = date_added;
    }

    public float getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(float date_taken) {
        this.date_taken = date_taken;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "display_name='" + display_name + '\'' +
                ", description='" + description + '\'' +
                ", date_added=" + date_added +
                ", date_taken=" + date_taken +
                ", mime_type='" + mime_type + '\'' +
                ", data='" + data + '\'' +
                ", title='" + title + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", orientation=" + orientation +
                '}';
    }
}
