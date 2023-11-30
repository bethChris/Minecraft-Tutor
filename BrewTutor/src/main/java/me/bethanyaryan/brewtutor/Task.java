package me.bethanyaryan.brewtutor;

import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class Task {
    private final Player player;
    private final String prompt;

    //TODO: Ideas for hint system: maybe a 2D array that sorts the hints in order of step #
    //      If a user is on step 2, loop through all of the step 2 hints infinitely
    private final String[][] hints;
    private final ItemStack[] hintItems;
    private final PotionType potionType;
    private final Plugin plugin;
    public final KNOWLEDGE_COMPONENTS[] neededKCs;
    public final KNOWLEDGE_COMPONENTS[] givenKCs;

    public Task(Player player, String prompt, String[][] hints, ItemStack[] hintItems, PotionType potionType, Plugin plugin, KNOWLEDGE_COMPONENTS[] neededKCs, KNOWLEDGE_COMPONENTS[] givenKCs) {
        this.player = player;
        this.prompt = prompt;
        this.hints = hints;
        this.hintItems = hintItems;
        this.potionType = potionType;
        this.plugin = plugin;
        this.neededKCs = neededKCs;
        this.givenKCs = givenKCs;
    }

    // copy is a template Task created in the Constants class
    public Task(Task copy, Player player, Plugin plugin) {
        this.player = player;
        this.prompt = copy.prompt;
        this.hints = copy.hints;
        this.hintItems = copy.hintItems;
        this.potionType = copy.potionType;
        this.plugin = plugin;
        this.neededKCs = copy.neededKCs;
        this.givenKCs = copy.givenKCs;
    }

    // Constructor called by the Constants class
    public Task(String prompt, String[][] hints, ItemStack[] hintItems, PotionType potionType, KNOWLEDGE_COMPONENTS[] neededKCs, KNOWLEDGE_COMPONENTS[] givenKCs) {
        this.prompt = prompt;
        this.hints = hints;
        this.hintItems = hintItems;
        this.potionType = potionType;
        this.neededKCs = neededKCs;
        this.givenKCs = givenKCs;

        this.plugin = null;
        this.player = null;
    }

    // @Parameter submission : The potion the player created
    public boolean checkConditionMet(ItemStack submission) {
        if (!(submission.getType() == Material.POTION)) {
            this.player.sendMessage(ChatColor.DARK_PURPLE + "Witch: " + ChatColor.RED + "That's.....not a potion");
            return false;
        }

        PotionMeta potion = (PotionMeta)submission.getItemMeta();
        assert potion != null;
        if (potion.getBasePotionType() == this.potionType) {
            this.player.sendMessage(ChatColor.DARK_PURPLE + "Witch: " + ChatColor.GREEN + "Well Done!");
            // Currently disabled because firework damages player
//            this.shootFirework();
            return true;
        } else {
            this.player.sendMessage(ChatColor.DARK_PURPLE + "Witch: " + ChatColor.RED + "That's Not Quite Right. Try Again! Remember you can type /hint to get some help!");
        }

        return false;
    }

//    public void nextHint(int hintNum) {
//        this.player.sendMessage(ChatColor.AQUA + hints[hintNum-1]);
//    }

    // Shoots off a firework on the player when task is successfully completed
    private void shootFirework() {
        Location playerLocation = this.player.getLocation();
        Firework f = (Firework) playerLocation.getWorld().spawn(playerLocation, Firework.class);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .withColor(Color.ORANGE)        // TODO: Change firework color in future
                .withColor(Color.BLUE)
                .withFade(Color.BLUE)
                .build());
        fm.setPower(0);
        f.setFireworkMeta(fm);
        f.detonate();
    }

    @Override
    public String toString(){
        return this.prompt;
    }
}
