package me.bethanyaryan.brewtutor;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public final class BrewTutor extends JavaPlugin implements Listener {

    public final ArrayList<Player> CurrentlyInTutor = new ArrayList<Player>();
    public final ArrayList<StudentModel> SavedTutorData = new ArrayList<StudentModel>();
    public Location brewingStandLocation;
    @Override
    public void onEnable() {
        System.out.println("[BrewTutor] has been enabled!");
        CommandBrewTutor commandListener = new CommandBrewTutor();
        this.getCommand("BrewTutor").setExecutor(commandListener);
        Objects.requireNonNull(this.getCommand("BrewTutor")).setExecutor(new CommandBrewTutor());
        getServer().getPluginManager().registerEvents(this, this);
    }

    //Toggles the brew tutor based on if the player is in the tutor or not already
    public void toggleBrewTutor(Player player){
        if (!inTutor(player)){
            CurrentlyInTutor.add(player);
            player.sendMessage(ChatColor.DARK_PURPLE + "BrewTutor has been enabled!");
            start(player);
        }else {
            CurrentlyInTutor.remove(player);
            player.sendMessage(ChatColor.DARK_RED + "BrewTutor has been disabled!");
            //probably do something here to save the student model to our ongoing list
            end();
        }
    }
    public boolean inTutor(Player player) {
        return CurrentlyInTutor.contains(player);
    }
    public void start(Player player) {
        // if player has used the tutor before, grab their student model state from usedTutor list
        createBrewingStand(player);
        boolean containsPlayer = false;
        StudentModel model;

        for (StudentModel SM : SavedTutorData) {
            if (SM.getPlayer() == player){
                containsPlayer = true;
                model = SM;
                break;
            }
        }
        if (!containsPlayer) {
            model = new StudentModel(player);
        }

        //send them over to the main pedagocial loop. Loop will use each players data to make decisions.

    }
    public void end(){
        deleteBrewingStand(brewingStandLocation);
    }
    public void createBrewingStand(Player player) {
        Location playerLocation = player.getLocation();
        int platformZ = playerLocation.getBlockZ() + 1;
        Location platformLocation = new Location(playerLocation.getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY(), platformZ);


        // Calculate the location of the block in front of the player based on their facing direction
        BlockFace facingDirection = player.getFacing();
        Location brewingStandLocation = platformLocation.getBlock().getRelative(facingDirection).getLocation();

        // Spawn a brewing stand at the location in front of the player
        brewingStandLocation.getBlock().setType(Material.BREWING_STAND);

        this.brewingStandLocation = brewingStandLocation;
    }
    private void deleteBrewingStand(Location brewingStandLocation) {
        brewingStandLocation.getBlock().setType(Material.AIR);
    }

    @EventHandler
    public void cancelBlockBreak(BlockBreakEvent event) {
        //stops user from picking up block and walking away with it lol
        if (event.getBlock().getType() == Material.BREWING_STAND) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the clicked block is a brewing stand
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BREWING_STAND) {
            // The player clicked on a brewing stand
            Player player = event.getPlayer();
            player.sendMessage(ChatColor.GREEN + "You clicked on a brewing stand!");
        }
    }
    @Override
    public void onDisable() {
        System.out.println("[BrewTutor] has been disabled!");
    }
}




