//
//  DefaultTransmitter
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.transmitter;

import android.util.Log;

import com.obzen.tagworks.data.Event;
import com.obzen.tagworks.data.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class EventTransmitter implements Transmitter{

    private final LinkedBlockingDeque<Event> queue = new LinkedBlockingDeque<>();
    private volatile long connectionTimeOut = 5 * 1000;
    private volatile long transmitInterval = 5 * 1000;
    private final Object THREAD_LOCK = new Object();
    private final PacketTransfer packetTransfer;
    private final PacketSender packetSender;
    private volatile boolean isRunning = false;
    private volatile boolean useGzipped = false;
    private final Semaphore mSleepToken = new Semaphore(0);

    public EventTransmitter(PacketTransfer packetTransfer, PacketSender packetSender){
        this.packetTransfer = packetTransfer;
        this.packetSender = packetSender;
    }

    private final Runnable loop = new Runnable() {
        @Override
        public void run(){
            while (isRunning){
                try{
                    long sleepTime = transmitInterval;
                    mSleepToken.tryAcquire(sleepTime, TimeUnit.MILLISECONDS);

                    Log.d("TagWokrs", "loop isRunning");

                    List<Event> eventBacks = new ArrayList<>();
                    queue.drainTo(eventBacks);

                    for(Packet packet : packetTransfer.transferPackets(eventBacks)){
                        Log.d("TagWokrs", "trans data => " + packet.getBody().toString());
                        // send
                        boolean job = packetSender.send(packet);
                        Log.d("TagWokrs", "send result => " + job);
                    }

                    synchronized(THREAD_LOCK){
                        if(queue.isEmpty() || transmitInterval < 0){
                            isRunning = false;
                            Log.d("TagWokrs", "loop end");
                            break;
                        }
                    }

                }catch (Exception e){
                    // 에러 처리
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    @Override
    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    @Override
    public void setTransmitInterval(long transmitInterval) {
        this.transmitInterval = transmitInterval;
    }

    @Override
    public long getTransmitInterval() {
        return transmitInterval;
    }

    @Override
    public void transmit(Event eventBack) {
        queue.add(eventBack);
        Log.d("TagWokrs", "call transmit()");
        launch();
    }

    @Override
    public void flush() {
        synchronized (THREAD_LOCK){

        }
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean launch() {
        synchronized (THREAD_LOCK){
            if(!isRunning){
                Log.d("TagWokrs", "call launch()");
                isRunning = true;
                Thread thread = new Thread(loop);
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.setName("TagWorks-EventTransmitter");
                thread.start();
            }
        }
        return false;
    }
}
