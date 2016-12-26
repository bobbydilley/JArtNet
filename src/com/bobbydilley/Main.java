package com.bobbydilley;

import JArtNet.JArtNet;

public class Main {
    public static void main(String[] args) {
        JArtNet artNet = new JArtNet("192.168.0.97");

        artNet.set(0, 255);
        artNet.set(4, 255);


        // Lambda Runnable
        Runnable task1 = () -> {
            int light = 0;
            for(int i = 0 ; i < 1000 ; i++) {

                for(int j = 0 ; j < 256 ; j++) {
                    artNet.set(1, j);
                    artNet.set(5, j);
                    artNet.send();
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        System.err.println("Could not sleep.");
                    }
                }

                for(int j = 255 ; j > 0 ; j--) {
                    artNet.set(1, j);
                    artNet.set(5, j);
                    artNet.send();
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        System.err.println("Could not sleep.");
                    }
                }
            }
        };

        // start the thread
        new Thread(task1).start();


    }
}
