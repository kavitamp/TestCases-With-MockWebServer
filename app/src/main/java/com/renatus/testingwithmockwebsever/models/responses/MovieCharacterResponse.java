package com.renatus.testingwithmockwebsever.models.responses;

import com.google.gson.annotations.SerializedName;

public class MovieCharacterResponse extends ErrorCode {

    @SerializedName("name")
    private String name;

    @SerializedName("hair_color")
    private String hairColor;

    @SerializedName("gender")
    private String gender;

    @SerializedName("height")
    private String height;

    @SerializedName("skin_color")
    private String skinColor;

    @SerializedName("eye_color")
    private String eyeColor;

    public String getName() {
        return name;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    @Override
    public String toString() {
        return "MovieCharacterResponse{" +
                "name='" + name + '\'' +
                ", hairColor='" + hairColor + '\'' +
                ", gender='" + gender + '\'' +
                ", height='" + height + '\'' +
                ", skinColor='" + skinColor + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                '}';
    }
}
