package pl.vertty.nomenhc.handlers;

import pl.vertty.nomenhc.objects.User;

import java.util.HashSet;
import java.util.Set;

public final class WhiteListManager {
    public static void addWhitelist(String uuid) {
        User u = UserManager.get(uuid);
        u.setWhitelist(true);
    }

    public static void removeWhitelist(String uuid) {
        User u = UserManager.get(uuid);
        u.setWhitelist(false);
    }

    public static void setWhitelist(boolean whitelist) {
        for(User u : UserManager.getAccounts()){
            u.setWhitelist_status(whitelist);
        }
    }

    public static Set<String> getWhitelisted() {
        Set<String> wl = new HashSet<>();
        for(User u : UserManager.getAccounts()){
            if(u.isWhitelist()){
                wl.add(u.getName());
            }
        }
        return wl;
    }
}

