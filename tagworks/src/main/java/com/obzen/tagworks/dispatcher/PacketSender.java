//	Copyright 2023 Obzen
//
//	Licensed under the Apache License, Version 2.0 (the "License");
//	you may not use this file except in compliance with the License.
//	You may obtain a copy of the License at
//
//	    http://www.apache.org/licenses/LICENSE-2.0
//
//	Unless required by applicable law or agreed to in writing, software
//	distributed under the License is distributed on an "AS IS" BASIS,
//	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	See the License for the specific language governing permissions and
//	limitations under the License.

package com.obzen.tagworks.dispatcher;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * The type Packet sender.
 */
public class PacketSender{

    private final String targetUrl;
    private final long timeout;

    /**
     * Instantiates a new Packet sender.
     *
     * @param targetUrl the target url
     * @param timeout   the timeout
     */
    public PacketSender(String targetUrl, long timeout){
        this.targetUrl = targetUrl;
        this.timeout = timeout;
    }

    /**
     * Send boolean.
     *
     * @param packet the packet
     * @return the boolean
     */
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
