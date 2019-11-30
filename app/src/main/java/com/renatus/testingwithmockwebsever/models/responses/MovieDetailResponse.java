package com.renatus.testingwithmockwebsever.models.responses;

import com.google.gson.annotations.SerializedName;

public class MovieDetailResponse extends ErrorCode {

    @SerializedName("title")
    private String title;

    @SerializedName("episode_id")
    private String episodeId;

    @SerializedName("opening_crawl")
    private String openingCrawl;

    @SerializedName("director")
    private String director;

    @SerializedName("producer")
    private String producer;

    @SerializedName("release_date")
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    public String getDirector() {
        return director;
    }

    public String getProducer() {
        return producer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "MovieDetailResponse{" +
                "title='" + title + '\'' +
                ", episodeId='" + episodeId + '\'' +
                ", openingCrawl='" + openingCrawl + '\'' +
                ", director='" + director + '\'' +
                ", producer='" + producer + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
