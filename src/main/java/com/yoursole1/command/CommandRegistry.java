package com.yoursole1.command;

import com.yoursole1.command.annotation.Command;
import com.yoursole1.command.annotation.Commands;

@Commands
public class CommandRegistry {

    @Command(prefix = "?", exampleText = "?concat hello world 123", helpText = "Concat three strings")
    public static String concat(String a, String b, String c) {
        return a + b + c;
    }

    @Command(prefix = "?", exampleText = "?help", helpText = "This page")
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
}
