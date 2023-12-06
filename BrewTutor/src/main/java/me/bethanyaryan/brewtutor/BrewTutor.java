package me.bethanyaryan.brewtutor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

// This is the main class that gets called when the plugin is started
public final class BrewTutor extends JavaPlugin implements Listener {

    public final ArrayList<Player> PlayersInTutor = new ArrayList<>();
    public final ArrayList<StudentModel> SavedTutorData = new ArrayList<>();
    public ArrayList<StudentModel> CurrentlyInTutorData = new ArrayList<>();
    public DecisionModel decisionModel;
    public Plugin plugin;
    @Override
    public void onEnable() {
        System.out.println("[BrewTutor] has been enabled!");
        Objects.requireNonNull(this.getCommand("BrewTutor")).setExecutor(new CommandBrewTutor());
        Objects.requireNonNull(this.getCommand("hint")).setExecutor(new CommandHint());
        getServer().getPluginManager().registerEvents(this, this);
        this.plugin = this;
        this.decisionModel = new DecisionModel(this);
        this.decisionModel.run();
    }
    //comment
    //Toggles the brew tutor based on if the player is in the tutor or not already
    public void toggleBrewTutor(Player player){
        if (!inTutor(player)){
            PlayersInTutor.add(player);
            player.sendMessage(ChatColor.DARK_PURPLE + "BrewTutor has been enabled!");
            start(player);
        }else {
            PlayersInTutor.remove(player);
            player.sendMessage(ChatColor.DARK_RED + "BrewTutor has been disabled!");
            end(player);
        }
    }

    // Gives a hint only if the player is in the tutor and their student model already exists
    // Called when the player types the hint command
    public void toggleHint(Player player) {
        if (!inTutor(player)) { return; }
        else {
            boolean containsPlayer = false;
            StudentModel model = null;

            for (StudentModel SM : SavedTutorData) {
                if (SM.getPlayer() == player){
                    containsPlayer = true;
                    model = SM;
                    break;
                }
            }

            if (!containsPlayer) { return; }

            model.getHint();
        }
    }

    public boolean inTutor(Player player) {
        return PlayersInTutor.contains(player);
    }

    // Called when the player runs the brewtutor command
    public void start(Player player) {
        // If player has used the tutor before, grab their student model state from usedTutor list
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

        // Add to list, create start items, start prompt
        CurrentlyInTutorData.add(model);
        model.createStartItems();
        model.waitingForPrompt = true;
    }

    // Called when the tutor is running and the player runs the brewtutor command
    public void end(Player player){
        for (StudentModel sm : CurrentlyInTutorData) {
            if (sm.getPlayer() == player ){
                // Delete them from list, remove spawned items
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




