package com.vcoders.on_demand_youtube_player.youtubeApi.response;


public class ResponseAPIListener<Type> {
    private Type data;
    private String errorMessage;
    private int errorCode;

    public Type getData() {
        return data;
    }

    public void setData(Type data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
