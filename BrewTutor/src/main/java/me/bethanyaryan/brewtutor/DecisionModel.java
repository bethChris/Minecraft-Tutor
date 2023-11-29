package me.bethanyaryan.brewtutor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;


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

                Location chestLocation = sm.submissionChestLocation;

                Chest chest = (Chest) player.getWorld().getBlockAt(chestLocation).getState();
                ItemStack[] submissionChestItems = chest.getInventory().getContents();
                ItemStack[] submission = getNonNullItems(submissionChestItems);

                if (submission.length > 0){
                    if (submission.length > 1){
                        player.sendMessage(ChatColor.DARK_PURPLE + "Witch: You have too many items in the submission chest. 1 submission at a time please.");
                    }else{
                        for (ItemStack item : submission){
                            player.sendMessage(ChatColor.DARK_PURPLE + "Witch: you submitted a " + item.toString());
                            sm.submitTask(item);
                            chest.getInventory().remove(item);
                            break; //should only be 1
                        }
                    }
                }

                //TODO: decision making logic goes here. Update student knowledge, iterate to next prompt, ect
            }
        }, 20L * 10L /*<-- the initial delay */, 20L * 5L /*<-- the interval */); //TODO: adjust this time interval
    }

    private ItemStack[] getNonNullItems(ItemStack[] items) {
        ArrayList<ItemStack> nonNullItems = new ArrayList<>();
        for (ItemStack item : items) {
            if (item != null) {
                nonNullItems.add(item);
            }
        }
        return nonNullItems.toArray(new ItemStack[0]);
    }

    public void stop(){
        scheduler.cancelTasks(plugin);
    }
}
