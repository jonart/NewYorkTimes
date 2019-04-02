package ru.evgeniy.nytimes.data.network.dto;

import com.google.gson.annotations.SerializedName;

import io.reactivex.annotations.NonNull;

public class MultimediaDTO {
    /**
     * url : https://static01.nyt.com/images/2019/01/21/world/21Isis1/21Isis1-thumbStandard.jpg
     * format : Standard Thumbnail
     * height : 75
     * width : 75
     * type : image
     * subtype : photo
     * caption : President Trump at Dover Air Force Base on Saturday as troops carried the remains of Scott A. Wirtz, who was killed in a suicide bombing in Manbij, Syria.
     * copyright : Doug Mills/The New York Times
     */

    @SerializedName("url")
    private String url;
    @SerializedName("format")
    private String format;
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;
    @SerializedName("type")
    private String type;
    @SerializedName("subtype")
    private String subtype;
    @SerializedName("caption")
    private String caption;
    @SerializedName("copyright")
    private String copyright;

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public String getFormat() {
        return format;
    }

    public void setFormat(@NonNull String format) {
        this.format = format;
    }

    @NonNull
    public int getHeight() {
        return height;
    }

    public void setHeight(@NonNull int height) {
        this.height = height;
    }

    @NonNull
    public int getWidth() {
        return width;
    }

    public void setWidth(@NonNull int width) {
        this.width = width;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(@NonNull String subtype) {
        this.subtype = subtype;
    }

    @NonNull
    public String getCaption() {
        return caption;
    }

    public void setCaption(@NonNull String caption) {
        this.caption = caption;
    }

    @NonNull
    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(@NonNull String copyright) {
        this.copyright = copyright;
    }
}
