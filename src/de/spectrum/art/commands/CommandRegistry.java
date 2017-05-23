package de.spectrum.art.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Giorgio on 23.05.17.
 */
public class CommandRegistry {
    public static Map<String, Class<? extends Command>> registry = new HashMap<String, Class<? extends Command>>() {
        {
            put(DrawEllipse.DESCRIPTION, DrawEllipse.class);
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
