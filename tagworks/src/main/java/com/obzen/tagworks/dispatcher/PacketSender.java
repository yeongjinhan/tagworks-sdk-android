//
//  PacketSender
//  TagWorks SDK for android
//
//  Copyright (c) 2023 obzen All rights reserved.
//

package com.obzen.tagworks.dispatcher;

import com.obzen.tagworks.data.Packet;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PacketSender{

    private long timeout = 30;

    public boolean send(Packet packet) {
        HttpURLConnection urlConnection = null;
        try{
            urlConnection = (HttpURLConnection) new URL(packet.getTargetUrl()).openConnection();
            urlConnection.setConnectTimeout((int) timeout);
            urlConnection.setReadTimeout((int) timeout);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            final String data = packet.getBody().toString();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), StandardCharsets.UTF_8));
                writer.write(data);
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
