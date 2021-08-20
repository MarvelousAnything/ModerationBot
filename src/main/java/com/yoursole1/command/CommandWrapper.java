package com.yoursole1.command;

import com.yoursole1.command.annotation.Command;
import com.yoursole1.command.exception.NotACommandException;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

@Getter
public class CommandWrapper {
    private Method method;
    private Command command;

    public CommandWrapper(Method method) throws NotACommandException {
        if (!isCommand(method)) {
            throw new NotACommandException();
        }
        this.method = method;
        this.command = method.getAnnotation(Command.class);
    }

    public static boolean isCommand(AnnotatedElement element) {
        return element.isAnnotationPresent(Command.class);
    }

    public String[] getParameters(String input) {
        if (method.getParameterCount() > 0) {
            if (input.startsWith(getCommandString())) {
                input = input.replace(getCommandString() + " ", "");
            }
            return input.split(" ", method.getParameterCount());
        } else {
            return new String[]{};
        }
    }

    public String execute(Object[] parameters) throws InvocationTargetException, IllegalAccessException {
        if (parameters.length == 0) {
            return (String) method.invoke(null);
        }
        if (parameters.length != method.getParameterCount()) {
            return "Wrong number of arguments, expected "
                    + method.getParameterCount()
                    + " but got " + parameters.length;
        } else {
            return (String) method.invoke(null, parameters);
        }
    }

    public String execute(String input) throws InvocationTargetException, IllegalAccessException {
        return execute(getParameters(input));
    }

    public boolean checkPermissions(GuildMessageReceivedEvent event) {
        return Objects.requireNonNull(event.getMember())
                .hasPermission(Permission.getPermissions(command.permissions()));
    }

    public String getName() {
        return method.getName();
    }

    public String getPrefix() {
        return command.prefix();
    }

    public String getCommandString() {
        return getPrefix() + getName();
    }

    public String getHelpText() {
        return command.helpText();
    }

    public String getExampleText() {
        return command.exampleText();
    }

    public boolean commandStringMatches(String input) {
        return input.startsWith(getCommandString());
    }
}
