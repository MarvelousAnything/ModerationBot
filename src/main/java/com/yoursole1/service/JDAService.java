package com.yoursole1.service;

import com.yoursole1.event.GuildMessageEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
public final class JDAService {

    private static JDAService instance;
    public static final int COLOR = 2_871_056;

    private JDA jda;

    private JDAService() {
        try {
            JDABuilder builder = JDABuilder
                    .createDefault(System.getenv("BOT_TOKEN"))
                    .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setBulkDeleteSplittingEnabled(false)
                    .setCompression(Compression.NONE)
                    .setActivity(Activity.playing("Your messages"))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(new GuildMessageEvent());
            this.jda = builder.build();
        } catch (LoginException e) {
            log.error("Invalid token!", e);
        }
    }

    public static MessageEmbed createEmbed(Map<String,String> content){

        Guild guild = instance.jda.getGuilds().get(0);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Moderation Bot");
        embedBuilder.setDescription("This is in BETA - DM Yoursole1#7254 with issues");
        embedBuilder.setThumbnail(guild.getIconUrl());

        for (Map.Entry<String, String> field : content.entrySet()){
            embedBuilder.addField(field.getKey(), field.getValue(),false);
        }

        embedBuilder.setFooter("Created with ♥ by Yoursole1#7254");
        embedBuilder.setColor(COLOR);
        return embedBuilder.build();
    }

    public static synchronized JDAService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new JDAService();
        }
        return instance;
    }

    public static Member tagToMember(String tag) {
        return JDAService
                .getInstance()
                .getJda()
                .getGuilds()
                .get(0)
                .getMemberByTag(tag);
    }


}
