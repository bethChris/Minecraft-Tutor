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
    private final String[] hints;
    private final PotionType potionType;
    private final Plugin plugin;
    private final KnowledgeComponents[] kc;

    public Task(Player player, String prompt, String[] hints, PotionType potionType, Plugin plugin, KnowledgeComponents[] kc) {
        this.player = player;
        this.prompt = prompt;
        this.hints = hints;
        this.potionType = potionType;
        this.plugin = plugin;
        this.kc = kc;

        this.player.sendMessage(ChatColor.DARK_BLUE + this.prompt);
    }

    // Test constructor
    // TODO: Delete
    public Task(Player player, Plugin plugin) {
        this.player = player;
        this.potionType = PotionType.AWKWARD;
        this.plugin = plugin;
        this.prompt = "Test Prompt";
        this.hints = new String[]{"hint 1", "hint 2", "hint 3", "hint 4"};
        this.kc = new KnowledgeComponents[]{new KnowledgeComponents("awkward")};
    }

    // @Parameter submission : The potion the player created
    public void checkConditionMet(ItemStack submission) {
        if (!(submission.getType() == Material.POTION)) { return; }

        PotionMeta potion = (PotionMeta)submission.getItemMeta();
        assert potion != null;
        if (potion.getBasePotionType() == this.potionType) {
            this.player.sendMessage(ChatColor.GREEN + "Well Done!");
            // Currently disabled because firework damages player
//            this.shootFirework();
        }
    }

    public void nextHint(int hintNum) {
        this.player.sendMessage(ChatColor.GREEN + hints[hintNum-1]);
    }

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
}
