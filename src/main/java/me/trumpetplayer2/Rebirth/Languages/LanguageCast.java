package me.trumpetplayer2.Rebirth.Languages;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;

public class LanguageCast {
    public List<String> phrase;
    private String id;
    
    public LanguageCast(List<String> temp, String identifier) {
	phrase = temp;
	id = ChatColor.WHITE + "<" + identifier + ChatColor.WHITE + "> ";
    }
    
    public String translate(int length) {
	String msg = id;
	Random rng = new Random();
	for(int i = 0; i < length; i++) {
	    String temp = phrase.get(rng.nextInt(phrase.size()));
	    msg += temp;
	    i += temp.length();
	    rng.nextInt();
	}
	return msg;
    }
}
