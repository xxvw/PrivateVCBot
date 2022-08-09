package com.github.xrozl;

import com.github.xrozl.listener.BotReadyListener;
import com.github.xrozl.listener.CreateCommandListener;
import com.github.xrozl.listener.SetupCommandListener;
import com.github.xrozl.listener.VCJoinEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PrivateVCBot {

    public static JDA jda;
    public static String category = "Private Voice Channel 1";
    public static String suffix = "'s channel";
    public static VCManager vcManager;
    public static VCTrackingThread vcTrackingThread;

    public static void main(String[] args) {

        File f = new File("config.txt");
        Map<String, String> cfgValues = new HashMap<>();
        if (!f.exists()) {
            System.out.println("Config file not found!");
            System.exit(0);
        }

        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("=");
                cfgValues.put(split[0], split[1]);
            }
        } catch (IOException e) {
            System.out.println("Config file not found!");
            System.exit(0);
        }

        vcManager = new VCManager();

        JDABuilder builder = JDABuilder.createDefault(cfgValues.get("token"));
        builder.setAutoReconnect(true);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES);

        builder.addEventListeners(new BotReadyListener());
        builder.addEventListeners(new SetupCommandListener());
        builder.addEventListeners(new CreateCommandListener());
        builder.addEventListeners(new VCJoinEventListener());

        try {
            jda = builder.build().awaitReady();
            jda.updateCommands().addCommands(Commands.slash("setup-private-vc", "Setup private voice channel"))
                                .addCommands(Commands.slash("private-vc", "Create private voice channel.")).queue();
            vcTrackingThread = new VCTrackingThread();
            vcTrackingThread.start();
        } catch (Exception e) {
            System.out.println("Failed to build JDA!");
            System.exit(0);
        }
    }
}
