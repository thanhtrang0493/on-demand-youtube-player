package com.vcoders.on_demand_youtube_player.youtubeApi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    /**
     * The code of server returned.
     */
    @SerializedName("code")
    @Expose
    private Integer responseCode;

    /**
     * The message of server returned.
     */
    @SerializedName("message")
    @Expose
    private String responseMessage;

    /**
     * This method always returns immediately, return code after users fetch data from server
     * It helps users know the request is successful or error.
     *
     * @return code after users fetch data from server
     * @see Integer
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * This method is used to assign code to users.
     *
     * @param responseCode a code returned to users
     */
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * The method is returns appropriate message which is to be displayed
     * to the user against the specified object.
     *
     * @return message which is to be displayed to the user
     * @see String
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * This method is used to assign messages to uses.
     *
     * @param responseMessage a message returned to users
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
