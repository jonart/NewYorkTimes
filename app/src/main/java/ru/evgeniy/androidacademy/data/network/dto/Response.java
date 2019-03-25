package ru.evgeniy.androidacademy.data.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class Response {

    /**
     * status : OK
     * results : []
     */

    @SerializedName("status")
    private String status;
    @SerializedName("results")
    private List<NewsDTO> results;

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public List<NewsDTO> getResults() {
        return results;
    }

    public void setResults(@NonNull List<NewsDTO> results) {
        this.results = results;
    }
}
