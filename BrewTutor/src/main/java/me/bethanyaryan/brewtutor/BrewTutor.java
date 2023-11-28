package me.bethanyaryan.brewtutor;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class BrewTutor extends JavaPlugin implements Listener {

    public final ArrayList<Player> PlayersInTutor = new ArrayList<Player>();
    public final ArrayList<StudentModel> SavedTutorData = new ArrayList<StudentModel>();
    public ArrayList<StudentModel> CurrentlyInTutorData = new ArrayList<StudentModel>();
    public DecisionModel decisionModel;
    public Plugin plugin;
    @Override
    public void onEnable() {
        System.out.println("[BrewTutor] has been enabled!");
        Objects.requireNonNull(this.getCommand("BrewTutor")).setExecutor(new CommandBrewTutor());
        getServer().getPluginManager().registerEvents(this, this);
        this.plugin = this;
        this.decisionModel = new DecisionModel(this);
        this.decisionModel.run();
    }

    //Toggles the brew tutor based on if the player is in the tutor or not already
    public void toggleBrewTutor(Player player){
        if (!inTutor(player)){
            PlayersInTutor.add(player);
            player.sendMessage(ChatColor.DARK_PURPLE + "BrewTutor has been enabled!");
            start(player);
        }else {
            PlayersInTutor.remove(player);
            player.sendMessage(ChatColor.DARK_RED + "BrewTutor has been disabled!");
            //probably do something here to save the student model to our ongoing list
            end(player);
        }
    }
    public boolean inTutor(Player player) {
        return PlayersInTutor.contains(player);
    }
    public void start(Player player) {
        // if player has used the tutor before, grab their student model state from usedTutor list
        boolean containsPlayer = false;
        StudentModel model = null;

        for (StudentModel SM : SavedTutorData) {
            if (SM.getPlayer() == player){
                containsPlayer = true;
                model = SM;
                break;
            }
        }

        if (!containsPlayer) {
            model = new StudentModel(player);
            SavedTutorData.add(model);
        }

        CurrentlyInTutorData.add(model);
        model.createStartItems();
    }
    public void end(Player player){
        for (StudentModel sm : CurrentlyInTutorData) {
            if (sm.getPlayer() == player ){
                CurrentlyInTutorData.remove(sm);
                sm.deleteStartItems();
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the clicked block is a brewing stand
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BREWING_STAND) {
            // The player clicked on a brewing stand
            Player player = event.getPlayer();
            player.sendMessage(ChatColor.GREEN + "You clicked on a brewing stand!");

            Task task = new Task(player, this.plugin);
            ItemStack stack = new ItemStack(Material.POTION);
            PotionMeta thing = (PotionMeta)stack.getItemMeta();
            thing.setBasePotionType(PotionType.AWKWARD);
            stack.setItemMeta(thing);
            task.checkConditionMet(stack);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (inTutor(player)){
            toggleBrewTutor(event.getPlayer());
        }
    }
    @Override
    public void onDisable() {
        System.out.println("[BrewTutor] has been disabled!");
        this.decisionModel.stop();
    }
}




