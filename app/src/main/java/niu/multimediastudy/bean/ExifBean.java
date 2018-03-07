package niu.multimediastudy.bean;

/**
 * Created by Qinlian_niu on 2018/3/7.
 */

public class ExifBean {
    private String userComment;
    private String imageDescription;
    private String artist;
    private String copyRight;
    private String softWare;

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
    }

    public String getSoftWare() {
        return softWare;
    }

    public void setSoftWare(String softWare) {
        this.softWare = softWare;
    }

    @Override
    public String toString() {
        return "ExifBean{" +
                "userComment='" + userComment + '\'' +
                ", imageDescription='" + imageDescription + '\'' +
                ", artist='" + artist + '\'' +
                ", copyRight='" + copyRight + '\'' +
                ", softWare='" + softWare + '\'' +
                '}';
    }

    public enum ExifAttr {
        UserComment("UserComment"),
        Artist("Artist"),
        Copyright("Copyright"),
        Software("Software"),
        ImageDescription("ImageDescription");

        public String name;
        ExifAttr(String name) {
            this.name = name;
        }
    }

}
