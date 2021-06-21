package com.zsaveworld;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        getLogger().info("Habilitando salvamento automatico...");
        getLogger().info(String.format("Salvamento automatico a cada %s segundos!", getConfig().getInt("tempo")));
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,new BukkitRunnable() {
            @Override
            public void run() {
                if(getConfig().getBoolean("salvartodos")){
                    getLogger().info("Salvando todos mundos...");
                    for(World w : Bukkit.getServer().getWorlds()){
                        getLogger().info(String.format("Salvando mundo %s.", w.getName()));
                        w.save();
                    }
                }else{
                    List<String> mundos = getConfig().getStringList("mundos");
                    for(String m : mundos){
                        World w = Bukkit.getWorld(m);
                        if(w == null){
                            getLogger().info(String.format("Não foi possivel encontrar o mundo %s da configuração!", m));
                        }else{
                            getLogger().info(String.format("Salvando mundo %s...", w.getName()));
                            w.save();
                        }
                        Bukkit.getServer().savePlayers();
                        getLogger().info("Tarefa concluida!");
                    }
                }
            }
        },(long) getConfig().getInt("tempo")*20,(long) getConfig().getInt("tempo")*20);
    }
}
