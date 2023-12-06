package me.bethanyaryan.brewtutor;

import com.google.common.collect.Lists;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task {
    private final Player player;
    private final String prompt;

    private final String[][] hints;
    private final List<PotionType> hintItems;
    private final int[] hintNums;
    private final PotionType potionType;
    private final Plugin plugin;
    public final KNOWLEDGE_COMPONENTS[] neededKCs;
    public final KNOWLEDGE_COMPONENTS[] givenKCs;

    // copy is a template Task created in the Constants class
    public Task(Task copy, Player player, Plugin plugin) {
        this.player = player;
        this.prompt = copy.prompt;
        this.hints = copy.hints;
        this.hintItems = copy.hintItems;
        this.hintNums = copy.hintNums;
        this.potionType = copy.potionType;
        this.plugin = plugin;
        this.neededKCs = copy.neededKCs;
        this.givenKCs = copy.givenKCs;
    }

    // Constructor called by the Constants class
    public Task(String prompt, String[][] hints, PotionType[] hintItems, PotionType potionType, KNOWLEDGE_COMPONENTS[] neededKCs, KNOWLEDGE_COMPONENTS[] givenKCs) {
        this.prompt = prompt;
        this.hints = hints;
        this.hintItems = Arrays.asList(hintItems);
        this.hintNums = new int[this.hints.length];
        this.potionType = potionType;
        this.neededKCs = neededKCs;
        this.givenKCs = givenKCs;

        this.plugin = null;
        this.player = null;
    }

    // Checks if the player's submission is correct
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
            // Disabled because firework damages player
//            this.shootFirework();
            return true;
        } else {
            this.player.sendMessage(ChatColor.DARK_PURPLE + "Witch: " + ChatColor.RED + "That's Not Quite Right. Try Again! Remember you can type /hint to get some help!");
        }

        return false;
    }

    // Determines the next hint to give to the user
    public void nextHint(ItemStack[] brewingContents) {
        int stepNum = 0;
        List<PotionType> potions = new ArrayList<>();
        // Checks the potion in the player's brewing stand
        for (ItemStack item : brewingContents) {
            if (item == null) {
                potions.add(null);
            } else if (item.getType() == Material.POTION) {
                PotionMeta potion = (PotionMeta)item.getItemMeta();
                assert potion != null;
                potions.add(potion.getBasePotionType());
            } else {
                potions.add(null);
            }
        }

        // If a potion in player's brewing stand is the potion required for task completion
        if (this.potionType != null && potions.contains(this.potionType)) {
            this.player.sendMessage(ChatColor.DARK_PURPLE + "Witch: " + ChatColor.AQUA + "It seems you have completed the task. Submit your potion to the chest on your left");
            return;
        }

        // Get the latest hint step depending on the potions in player's brewing stand
        for (PotionType step : Lists.reverse(hintItems)) {
            if (potions.contains(step)) {
                stepNum = hintItems.indexOf(step);
                break;
            }
        }

        String[] hints = this.hints[stepNum];
        this.player.sendMessage(ChatColor.DARK_PURPLE + "Witch: " + ChatColor.AQUA + hints[this.hintNums[stepNum]]);

        if (this.hintNums[stepNum] != hints.length-1) {
            this.hintNums[stepNum]++;
        }
    }

    // Shoots off a firework on the player when task is successfully completed
    private void shootFirework() {
        Location playerLocation = this.player.getLocation();
        Firework f = (Firework) playerLocation.getWorld().spawn(playerLocation, Firework.class);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .withColor(Color.ORANGE)
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
