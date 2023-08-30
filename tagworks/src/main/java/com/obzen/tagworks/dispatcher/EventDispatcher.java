//
//  EventDispatcher
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.dispatcher;

import com.obzen.tagworks.data.Event;
import com.obzen.tagworks.data.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class EventDispatcher implements Runnable {

    private final Object THREAD_LOCK = new Object();
    private final Semaphore THREAD_TOKEN = new Semaphore(0);
    private final LinkedBlockingDeque<Event> eventQueue = new LinkedBlockingDeque<>();
    private volatile long dispatchInterval = 5 * 1000;
    private volatile boolean isRunning = false;
    private volatile boolean manualDispatch = false;
    private final PacketTransfer packetTransfer;
    private final PacketSender packetSender;

    public EventDispatcher(PacketTransfer packetTransfer, PacketSender packetSender){
        this.packetTransfer = packetTransfer;
        this.packetSender = packetSender;
    }

    public void setDispatchInterval(long dispatchInterval){
        this.dispatchInterval = dispatchInterval;
    }

    public void setManualDispatch(boolean manualDispatch){
        this.manualDispatch = manualDispatch;
    }

    public void enqueue(Event event){
        this.eventQueue.add(event);
        start();
    }

    private void start(){
        synchronized (THREAD_LOCK){
            if(!isRunning){
                isRunning = true;
                Thread thread = new Thread(this);
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.setName("TagWorks-EventDispatcher");
                thread.start();
            }
        }
    }

    @Override
    public void run() {
        while (isRunning){
            try{

                THREAD_TOKEN.tryAcquire(dispatchInterval, TimeUnit.MILLISECONDS);

                List<Event> events = new ArrayList<>();
                eventQueue.drainTo(events);
                for(Packet packet : packetTransfer.transferPackets(events)){
                    boolean job = packetSender.send(packet);
                }

                synchronized(THREAD_LOCK){
                    if(eventQueue.isEmpty() || manualDispatch){
                        isRunning = false;
                        break;
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
