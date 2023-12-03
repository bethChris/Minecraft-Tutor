package me.bethanyaryan.brewtutor;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

import static me.bethanyaryan.brewtutor.Constants.MATERIALS;


public class DecisionModel {
    private final BrewTutor plugin;
    private BukkitScheduler scheduler;
    public DecisionModel(BrewTutor plugin){
        this.plugin = plugin;
    }

    public void run(){
        // With BukkitScheduler
        this.scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(plugin, () -> {
            for (StudentModel sm : plugin.CurrentlyInTutorData){
                Player player = sm.getPlayer();
                if (sm.waitingForPrompt){
                    player.sendMessage(ChatColor.DARK_PURPLE + "Witch: " + ChatColor.GOLD + sm.getQuestion());
                    sm.waitingForPrompt = false;
                }

                //grade submission if any
                Chest submissionChest = sm.submissionChest;
                ItemStack[] submissionChestItems = submissionChest.getInventory().getContents();
                ItemStack[] submission = getNonNullItems(submissionChestItems);

                if (submission.length > 0){
                    if (submission.length > 1){
                        player.sendMessage(ChatColor.DARK_PURPLE + "Witch: You have too many items in the submission chest. 1 submission at a time please.");
                    }else{
                        for (ItemStack item : submission){
                            player.sendMessage(ChatColor.DARK_PURPLE + "Witch: Grading your submission...");
                            sm.submitTask(item);
                            submissionChest.getInventory().remove(item);
                            break; //should only be 1
                        }

                    }
                }

                //refill chests with materials and brewing stand fuel
                Chest materialChest = sm.materialChest;
                ItemStack[] materials = getNonNullItems(materialChest.getInventory().getContents());
                if (materials.length < MATERIALS.length){
                    sm.refillMaterials();
                }

                sm.brewingStand.getInventory().setFuel(new ItemStack(Material.BLAZE_POWDER, 1));
                //TODO: add this to student model? ^

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

    //stops the async loop
    public void stop(){
        scheduler.cancelTasks(plugin);
    }
}
