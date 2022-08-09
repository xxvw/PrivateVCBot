package com.github.xrozl;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class VCTrackingThread extends Thread {

    @Override
    public void run() {
        super.run();

        while (true) {
            try {
                for (Guild guild : PrivateVCBot.jda.getGuilds()) {
                    for (Category category : guild.getCategories()) {
                        if (category.getName().equals(PrivateVCBot.category)) {
                            for (VoiceChannel channel : category.getVoiceChannels()) {

                                if (channel.getName().contains(PrivateVCBot.suffix)) {
                                    if (channel.getMembers().size() == 0) {
                                        channel.delete().queue();
                                        System.out.println("Deleted empty VC: " + channel.getName());
                                        if (PrivateVCBot.vcManager.getVcMap().containsValue(channel)) {
                                            PrivateVCBot.vcManager.getVcMap().remove(channel.getName());
                                            User user = PrivateVCBot.vcManager.getUserMap().get(channel.getName());
                                            user.openPrivateChannel().complete().sendMessage("アクセスしているメンバーがいなかったため、プライベートVCを削除しました。").queue();
                                            PrivateVCBot.vcManager.getUserMap().remove(channel.getName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Thread.sleep(1000 * 60 * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
