package com.yilan.lib.playerlib;

/**
 * Created by chenshaolong on 2018/1/20.
 */

public class Test {

    public static Test singleton;

    public static Test getInstance() {
        if (singleton == null) {
            synchronized (Test.class) {
                if (singleton == null) {
                    singleton = new Test();
                }
            }
        }
        return singleton;
    }

    public void share(){
        if(listener != null){
            listener.share();
        }

    }
    Listener listener;
    public interface Listener{

        public void share();


    }

    public void setListenr(Listener listener){
        this.listener = listener;
    }
}
