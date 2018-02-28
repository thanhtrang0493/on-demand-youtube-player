package com.vcoders.on_demand_youtube_player.architecture;


public class BasePresenter<View, Router> {
    private View view;
    private Router router;

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Router getRouter() {
        return this.router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }
}
