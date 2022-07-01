package pl.vertty.nomenhc.objects;

import org.bukkit.scheduler.BukkitTask;
import pl.vertty.nomenhc.handlers.TeleportHandler;
import lombok.Data;

import java.util.UUID;

/**
 * @Author: Kacper 'DeeKaPPy' Horbacz
 * @Created 08.05.2021
 * @Class: Teleport
 **/

@Data
public class Teleport extends TeleportHandler {

    private UUID uuid;
    private Long end;
    private BukkitTask task;

    public Teleport(UUID uuid, Long end, BukkitTask task){
        this.uuid = uuid;
        this.end = end;
        this.task = task;
        add(this);
    }

    public boolean isTeleporting() {
        return task != null;
    }

}
