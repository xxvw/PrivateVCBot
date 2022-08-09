package com.github.xrozl.listener;

import com.github.xrozl.PrivateVCBot;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetupCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        if (event.getUser().isBot()) {
            return;
        }

        boolean found = false;
        try {
            for (Category category : event.getGuild().getCategories()) {
                if (category.getName().equals(PrivateVCBot.category)) {
                    found = true;
                    break;
                }
            }
        } catch (NullPointerException e) {
            found = false;
        }

        if (event.getName().equals("setup-private-vc") && !found) {

            Category category = event.getGuild().createCategory(PrivateVCBot.category).complete();

            event.reply("プライベートVCのセットアップを完了しました。").queue();
        } else if (event.getName().equals("setup-private-vc") && found) {
            event.reply("プライベートVCは既にセットアップされています。").queue();
        }
    }
}
