package ru.evgeniy.androidacademy.data.network.dto;

import com.google.gson.annotations.SerializedName;

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

    @SerializedName("num_results")
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
