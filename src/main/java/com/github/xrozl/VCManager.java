package com.github.xrozl;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.Map;

public class VCManager {

    private Map<String, VoiceChannel> vcMap;
    private Map<String, User> userMap;

    public VCManager() {
        vcMap = new java.util.HashMap<>();
        userMap = new java.util.HashMap<>();
    }

    public Map<String, VoiceChannel> getVcMap() {
        return vcMap;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
