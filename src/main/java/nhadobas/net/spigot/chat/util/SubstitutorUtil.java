package nhadobas.net.spigot.chat.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StrSubstitutor;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class SubstitutorUtil {

    private static final Map<String, ChatColor> PATTERN = new HashMap<String, ChatColor>();
    private static final StrSubstitutor SUBSITUTOR;
    static {
        PATTERN.put("darkRed", ChatColor.DARK_RED);
        PATTERN.put("gold", ChatColor.GOLD);
        PATTERN.put("blue", ChatColor.BLUE);
        PATTERN.put("reset", ChatColor.RESET);
        PATTERN.put("red", ChatColor.RED);
        SUBSITUTOR = new StrSubstitutor(PATTERN);
    }
    
    public static String replace(String original) {
        return SUBSITUTOR.replace(original);
    }

    public static String replace(String original, Map<String, Object> values) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        return sub.replace(SUBSITUTOR.replace(original));
    }

    public static TextComponent constructMessage(String template, Map<String, Object> values, String message) {
        return new TextComponent(replace(template, values) + ": " + message);
    }
}
