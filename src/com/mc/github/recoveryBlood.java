package com.mc.github;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;


public class recoveryBlood extends JavaPlugin implements Listener {
    private Set<String> OnlinePlayers = new HashSet<>();
    @Override
    public void onDisable(){
        super.onDisable();
    }


    @EventHandler
    public void PlayerJoinGame(PlayerJoinEvent event){
        OnlinePlayers.add(event.getPlayer().getName());
    }

    @EventHandler
    public void PlayerQuitGame(PlayerQuitEvent event){
        OnlinePlayers.remove(event.getPlayer().getName());
    }

    @Override
    public void onEnable(){
        ScanThread();
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        super.onEnable();
    }

    private void ScanThread(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for (String name:OnlinePlayers){
                    Player player = Bukkit.getPlayer(name);
                    if (player.isOnline()){
                        int addhealth = player.getLevel() * 2;
                        addhealth += player.getHealth();
                        if (addhealth<=player.getMaxHealth()){
                            player.setHealth((double) (addhealth));
                            player.sendMessage("回血中"+addhealth);
                        }else {
                            player.setHealth(player.getMaxHealth());
                            player.sendMessage("回满");
                        }
                    }
                }

            }
        }.runTaskTimer(this,20L,20L);
    }
}
