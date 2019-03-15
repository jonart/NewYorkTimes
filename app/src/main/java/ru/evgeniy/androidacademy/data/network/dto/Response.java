package ru.evgeniy.androidacademy.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    /**
     * status : OK
     * results : []
     */

    @SerializedName("status")
    private String status;
    @SerializedName("results")
    private List<NewsDTO> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<NewsDTO> getResults() {
        return results;
    }

    public void setResults(List<NewsDTO> results) {
        this.results = results;
    }
}
