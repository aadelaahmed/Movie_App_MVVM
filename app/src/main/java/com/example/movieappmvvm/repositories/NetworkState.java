package com.example.movieappmvvm.repositories;

public class NetworkState {
    enum Status {
        RUNNING,
        SUCCESS,
        FAILED,
        ENDOFLIST
    }

    String msg;
    Status status;
    public static NetworkState LOADED = new NetworkState(Status.SUCCESS,"Success")
            ,LOADING = new NetworkState(Status.RUNNING,"Running")
            ,ERROR = new NetworkState(Status.FAILED,"Something went wrong")
            ,ENDOFLIST = new NetworkState(Status.ENDOFLIST,"you have reached the end of list");

    public NetworkState(Status status, String msg)
    {
        this.msg = msg;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    //        LOADED = new NetworkState(Status.SUCCESS,"Success");
//        LOADING = new NetworkState(Status.RUNNING,"Running");
//        ERROR = new NetworkState(Status.FAILED,"Something went wrong");

}
