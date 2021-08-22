package com.yoursole1.event;

import com.yoursole1.command.CommandParser;
import com.yoursole1.service.BannedWordService;
import com.yoursole1.service.JDAService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

@Slf4j
public class GuildMessageEvent extends ListenerAdapter {

    @Override
    @SneakyThrows
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event){
        HashMap<String, String> embedMap = new HashMap<>();
        String output = CommandParser.parse(event);
        if (!Objects.isNull(output)) {
            embedMap.put("Moderation Bot", output);
            event.getChannel().sendMessage(JDAService.createEmbed(embedMap)).queue();
        }
        try {
            if (!JDAService
                    .getInstance()
                    .getJda()
                    .getSelfUser()
                    .equals(event.getAuthor()) &&
                    Arrays.stream(event
                                    .getMessage()
                                    .getContentRaw()
                                    .split(" "))
                            .anyMatch(word -> BannedWordService.getInstance()
                                    .contains(word.toLowerCase(Locale.ROOT)))) {
                event.getMessage().delete().queue();
            }
        } catch(InsufficientPermissionException e){
            log.error("Please give me Admin Permissions so I can moderate properly", e);
        }
    }
}
