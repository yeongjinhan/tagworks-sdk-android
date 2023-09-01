//
//  EventDispatcher
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.dispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.obzen.tagworks.data.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * The type Event dispatcher.
 */
public class EventDispatcher implements Runnable {

    private final Object THREAD_LOCK = new Object();
    private final Semaphore THREAD_TOKEN = new Semaphore(0);
    private final LinkedBlockingDeque<Event> eventQueue = new LinkedBlockingDeque<>();
    private volatile long dispatchInterval;
    @Deprecated
    private volatile int dispatchRetryCount;
    private volatile boolean isRunning = false;
    private volatile boolean manualDispatch;
    private final int pageSize = 20;
    private final PacketSender packetSender;

    /**
     * Instantiates a new Event dispatcher.
     *
     * @param packetSender       the packet sender
     * @param dispatchInterval   the dispatch interval
     * @param dispatchRetryCount the dispatch retry count
     * @param manualDispatch     the manual dispatch
     */
    public EventDispatcher(PacketSender packetSender,
                           long dispatchInterval,
                           int dispatchRetryCount,
                           boolean manualDispatch){
        this.packetSender = packetSender;
        this.dispatchInterval = dispatchInterval;
        this.dispatchRetryCount = dispatchRetryCount;
        this.manualDispatch = manualDispatch;
    }

    /**
     * Set dispatch interval.
     *
     * @param dispatchInterval the dispatch interval
     */
    public void setDispatchInterval(long dispatchInterval){
        this.dispatchInterval = dispatchInterval;
    }

    /**
     * Set manual dispatch.
     *
     * @param manualDispatch the manual dispatch
     */
    public void setManualDispatch(boolean manualDispatch){
        this.manualDispatch = manualDispatch;
    }

    /**
     * Enqueue.
     *
     * @param event the event
     */
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

    @NonNull
    private List<String> transferPackets(@NonNull List<Event> events) {
        if (events.isEmpty()) {
            return Collections.emptyList();
        }
        int packetsSize = (int) Math.ceil(events.size() * 1.0 / pageSize);
        List<String> resultPackets = new ArrayList<>(packetsSize);
        for (int i = 0; i < events.size(); i += pageSize) {
            List<Event> batch = events.subList(i, Math.min(i + pageSize, events.size()));
            final String packet = serializeJsonObject(batch);
            if(packet != null){
                resultPackets.add(packet);
            }
        }
        return resultPackets;
    }

    @Nullable
    private String serializeJsonObject(@NonNull List<Event> events) {
        try {
            JSONObject params = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (Event event : events) {
                jsonArray.put(event.toSerializeString());
            }
            params.put("requests", jsonArray);
            return params.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while (isRunning){
            try{

                THREAD_TOKEN.tryAcquire(dispatchInterval, TimeUnit.MILLISECONDS);
                List<Event> events = new ArrayList<>();
                eventQueue.drainTo(events);
                for(String packet : transferPackets(events)){
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
