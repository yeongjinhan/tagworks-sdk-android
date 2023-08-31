//
//  PacketSender
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.dispatcher;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PacketSender{

    private final String targetUrl;
    private final long timeout;

    public PacketSender(String targetUrl, long timeout){
        this.targetUrl = targetUrl;
        this.timeout = timeout;
    }

    public boolean send(String packet) {
        HttpURLConnection urlConnection = null;
        try{
            urlConnection = (HttpURLConnection) new URL(targetUrl).openConnection();
            urlConnection.setConnectTimeout((int) timeout);
            urlConnection.setReadTimeout((int) timeout);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), StandardCharsets.UTF_8));
                writer.write(packet);
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            int statusCode = urlConnection.getResponseCode();
            final boolean successful = checkResponseCode(statusCode);
            return successful;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
    }


    private static boolean checkResponseCode(int code) {
        return code == HttpURLConnection.HTTP_NO_CONTENT || code == HttpURLConnection.HTTP_OK;
    }
}
