package de.spectrum.art.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Giorgio on 23.05.17.
 */
public class CommandRegistry {
    public static LinkedHashMap<String, Class<? extends Command>> registry = new LinkedHashMap<String, Class<? extends Command>>() {
        {
            put(NullCommand.DESCRIPTION, NullCommand.class);
            put(DrawEllipse.DESCRIPTION, DrawEllipse.class);
            put(UpdateVariable.DESCRIPTION, UpdateVariable.class);

            // random number generation with option to only generate number once
            // add any root where cursor is located
            // edit background (refresh y/n, color)
            // determine if someting will overdraw another item
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
