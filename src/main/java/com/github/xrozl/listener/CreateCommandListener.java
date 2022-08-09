package com.github.xrozl.listener;

import com.github.xrozl.PrivateVCBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CreateCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        if (event.getUser().isBot()) {
            return;
        }

        if (event.getName().equals("private-vc")) {
            boolean found = false;
            Category category = null;
            try {
                for (Category cat : event.getGuild().getCategories()) {
                    if (cat.getName().equals(PrivateVCBot.category)) {
                        found = true;
                        category = cat;
                        break;
                    }
                }
            } catch (NullPointerException e) {
                found = false;
            }

            if (found && !PrivateVCBot.vcManager.getVcMap().containsKey(event.getUser().getId())) {
                if (category.getVoiceChannels().size() > 50) {
                    event.reply("作成できる上限に達しました。空きが出るまでお待ちください。").queue();
                    return;
                } else {
                    VoiceChannel vc = category.createVoiceChannel(event.getUser().getName() + PrivateVCBot.suffix).complete();
                    vc.getManager().setUserLimit(5).queue();

                    List<Permission> allow = new ArrayList<>();
                    allow.add(Permission.VOICE_CONNECT);
                    allow.add(Permission.VOICE_SPEAK);
                    allow.add(Permission.VOICE_USE_VAD);

                    vc.getManager().putMemberPermissionOverride(event.getUser().getIdLong(), allow, null).queue();
                    PrivateVCBot.vcManager.getVcMap().put(event.getUser().getId(), vc);
                    PrivateVCBot.vcManager.getUserMap().put(vc.getName(), event.getUser());
                    event.reply("プライベートVCを作成しました。").queue();
                    event.getUser().openPrivateChannel()
                            .flatMap(channel -> channel.sendMessage(vc.createInvite().setMaxAge(3600).complete().getUrl()))
                            .complete();
                    System.out.println("Create private VC: " + vc.getName());
                }
            } else {
                event.reply("プライベートVCを作成できませんでした。").queue();
                return;
            }
        }
    }
}
