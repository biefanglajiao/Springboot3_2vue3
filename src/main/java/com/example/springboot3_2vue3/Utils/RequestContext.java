package com.example.springboot3_2vue3.Utils;


import java.io.Serializable;

public class RequestContext implements Serializable {

    //通过线程取值，在线程某个节点取值，不会和其他的互相有影响

    private static ThreadLocal<String> remoteAddr = new ThreadLocal<>();//远程地址  IP

    public static String getRemoteAddr() {
        return remoteAddr.get();
    }

    public static void setRemoteAddr(String remoteAddr) {
        RequestContext.remoteAddr.set(remoteAddr);
    }

}
