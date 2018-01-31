package de.spectrum.art.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public class CommandRegistry {
    public static LinkedHashMap<String, Class<? extends Command>> registry = new LinkedHashMap<String, Class<? extends Command>>() {
        {
            put(NullCommand.DESCRIPTION, NullCommand.class);
            put(DrawEllipse.DESCRIPTION, DrawEllipse.class);
            put(UpdateVariable.DESCRIPTION, UpdateVariable.class);
        }
    };

    public static ArrayList<String> GetCommands() {
        ArrayList<String> cmdNames = new ArrayList<String>();
        for (String k : registry.keySet())
            cmdNames.add(k);

        return cmdNames;
    }

    public static Class<? extends Command> GetClass(String key) {
        return registry.get(key);
    }

}
