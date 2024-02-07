package me.trumpetplayer2.Rebirth.Utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import me.trumpetplayer2.Rebirth.Debug.Debug;

public class SkinFetcher {
    static private String API_PROFILE_LINK = "https://sessionserver.mojang.com/session/minecraft/profile/";
    
    public static String getPlayerName(UUID player) {
        String temp = "";
        String playerInfo = getContent(API_PROFILE_LINK + player.toString());
        //In form: "id":"c9205ed1-118f-4c0b-b6b2-4f2e76470b77",
        Debug.log(playerInfo);
        String[] inputs = playerInfo.split("\"name\" : \"");
        String name = inputs[1];
        inputs = name.split("\",");
        temp = inputs[0];
        return temp;
    }

    public static String getContent(String link){
            try {
                StringBuilder result = new StringBuilder();
                URL url = new URL(link);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                        result.append(inputLine);
                }
                br.close();
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
