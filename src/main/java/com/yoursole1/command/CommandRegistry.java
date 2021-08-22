package com.yoursole1.command;

import com.yoursole1.command.annotation.Command;
import com.yoursole1.command.annotation.Commands;
import com.yoursole1.service.AbstractStaticFileMemberService;
import com.yoursole1.service.AdminService;
import com.yoursole1.service.BannedWordService;
import com.yoursole1.service.JDAService;
import com.yoursole1.service.ModeratorService;
import net.dv8tion.jda.api.entities.Member;

import java.util.Locale;
import java.util.Objects;

@Commands
public final class CommandRegistry {

    private CommandRegistry() {}

    @Command(prefix = "?", exampleText = "?help", helpText = "This page", permissions = "none")
    public static String help() {
        StringBuilder builder = new StringBuilder();
        for (CommandWrapper command : CommandParser.getCommands()) {
            builder
                    .append("**")
                    .append(command.getExampleText())
                    .append("**\n")
                    .append(command.getHelpText())
                    .append("\n");
        }
        return builder.toString();
    }

    private static String addMemberOperation(String tag, AbstractStaticFileMemberService service) {
        Member member = JDAService.tagToMember(tag);
        if (Objects.isNull(member)) {
            return "This member does not exist!";
        } else {
            service.add(member);
            return member.getAsMention() + " is now a moderator!";
        }
    }

    private static String removeMemberOperation(String tag, AbstractStaticFileMemberService service) {
        Member member = JDAService.tagToMember(tag);
        if (Objects.isNull(member)) {
            return "This member does not exist!";
        } else {
            service.remove(member);
            return member.getAsMention() + " is now a moderator!";
        }
    }

    @Command(prefix = "?",
            exampleText = "?addMod \"example#1234\"",
            helpText = "Add a moderator")
    public static String addMod(String tag) {
        return addMemberOperation(tag, ModeratorService.getInstance());
    }

    @Command(prefix = "?",
            exampleText = "?addAdmin \"example#1234\"",
            helpText = "Add an admin")
    public static String addAdmin(String tag) {
        return addMemberOperation(tag, AdminService.getInstance());
    }

    @Command(prefix = "?",
            exampleText = "?removeMod \"example#1234\"",
            helpText = "Remove a moderator")
    public static String removeMod(String tag) {
        return removeMemberOperation(tag, ModeratorService.getInstance());
    }

    @Command(prefix = "?",
            exampleText = "?removeAdmin \"example#1234\"",
            helpText = "Remove an admin")
    public static String removeAdmin(String tag) {
        return removeMemberOperation(tag, AdminService.getInstance());
    }

    @Command(prefix = "?",
        exampleText = "?banWord \"BADWORD\"",
        helpText = "Ban a word",
        permissions = "moderator")
    public static String banWord(String word) {
        BannedWordService.getInstance().add(word.toLowerCase(Locale.ROOT));
        return "Successfully banned word!";
    }
}
