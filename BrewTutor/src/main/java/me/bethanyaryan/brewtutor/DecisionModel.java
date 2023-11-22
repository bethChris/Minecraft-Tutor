package me.bethanyaryan.brewtutor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;


public class DecisionModel {
    private final BrewTutor plugin;
    private BukkitScheduler scheduler;
    public DecisionModel(BrewTutor plugin){
        this.plugin = plugin;
        //TODO: this decision model will need access to: tasks, all student models in tutor
    }

    public void run(){
        // With BukkitScheduler
        this.scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(plugin, () -> {
            for (StudentModel sm : plugin.CurrentlyInTutorData){
                Player player = sm.getPlayer();
//                Bukkit.broadcastMessage(sm.getPlayer().getDisplayName() + " is in the tutor!");
                if (sm.waitingForPrompt){
                    player.sendMessage(ChatColor.DARK_PURPLE + "Witch: " + sm.getQuestion());
                    sm.waitingForPrompt = false;
                }

                //TODO: decision making logic goes here. Update student knowledge, iterate to next prompt, ect
            }
        }, 20L * 10L /*<-- the initial delay */, 20L * 5L /*<-- the interval */); //TODO: adjust this time interval
    }

    public void stop(){
        scheduler.cancelTasks(plugin);
    }
}
