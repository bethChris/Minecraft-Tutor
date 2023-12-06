package me.bethanyaryan.brewtutor;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.bethanyaryan.brewtutor.Constants.*;

public class StudentModel implements Listener {
    private final Player player;
    ArrayList<Location> itemLocations = new ArrayList<>();
    public Chest submissionChest;
    public Chest materialChest;
    public BrewingStand brewingStand;
    private int questionId;
    private final List<KnowledgeComponents> knowledgeComponents = new ArrayList<>();
    private Task currentTask;
    public boolean waitingForPrompt;

    public StudentModel(Player player) {
        super();
        this.player = player;
        this.questionId = 0;
        this.waitingForPrompt = true;

    }
    public Player getPlayer() {
        return this.player;
    }
    public String getQuestion() {
        this.currentTask = new Task(TASKS[this.questionId], this.player, null); // TODO: Do we need plugin?
        return this.currentTask.toString();
    }

    public void getHint() {
        ItemStack[] brewingContents = Arrays.copyOfRange(this.brewingStand.getInventory().getContents(), 0, 3);
        this.currentTask.nextHint(brewingContents);
    }

    public void submitTask(ItemStack submission) {
        boolean evidence = this.currentTask.checkConditionMet(submission);

        // For each knowledge component that the task teaches, update the mastery accordingly
        for (KNOWLEDGE_COMPONENTS updateKC : this.currentTask.givenKCs) {
            int idx = knowledgeComponents.indexOf(new KnowledgeComponents(updateKC, KC_LEVELS.get(updateKC.toString())));
            if (idx == -1) {
                knowledgeComponents.add(new KnowledgeComponents(updateKC, KC_LEVELS.get(updateKC.toString())));
                knowledgeComponents.get(knowledgeComponents.size()-1).updateMastery(evidence);
            } else {
                knowledgeComponents.get(idx).updateMastery(evidence);
            }
        }

        // If the player completed the task successfully, calculate the next task to give them
        if (evidence) {
            determineNextQuestion();
        }
    }

    // Refill the materials in the player's chest
    public void refillMaterials() {
        this.materialChest.getInventory().clear();
        for (ItemStack material : MATERIALS){
            this.materialChest.getInventory().addItem(material);
        }
    }

    public void createStartItems(){
        Block brewingStandBlock = this.player.getLocation().getBlock().getRelative(BlockFace.SOUTH);
        Block materialChestBlock = brewingStandBlock.getLocation().getBlock().getRelative(BlockFace.WEST);
        Block submissionChestBlock = brewingStandBlock.getLocation().getBlock().getRelative(BlockFace.EAST);
        Block waterCauldronBlock = materialChestBlock.getLocation().getBlock().getRelative(BlockFace.WEST);

        brewingStandBlock.setType(Material.BREWING_STAND);
        materialChestBlock.setType(Material.CHEST);
        submissionChestBlock.setType(Material.CHEST);
        waterCauldronBlock.setType(Material.WATER_CAULDRON);

        Chest materialChest = (Chest) materialChestBlock.getState();
        Chest submissionChest = (Chest) submissionChestBlock.getState();
        BrewingStand brewingStand = (BrewingStand) brewingStandBlock.getState();

        // Put a sign on the chests
        Block materialSignBlock = materialChestBlock.getRelative(BlockFace.SOUTH);
        materialSignBlock.setType(Material.OAK_SIGN);
        Sign materialSign = (Sign) materialSignBlock.getState();
        materialSign.getSide(Side.BACK).setLine(0, "Materials");
        materialSign.update();

        Block submissionSignBlock = submissionChestBlock.getRelative(BlockFace.SOUTH);
        submissionSignBlock.setType(Material.OAK_SIGN);
        Sign submissionSign = (Sign) submissionSignBlock.getState();
        submissionSign.getSide(Side.BACK).setLine(0, "Submissions");
        submissionSign.update();

        // Add signs first, so they'll be the first to be deleted
        this.itemLocations.add(materialSign.getLocation());
        this.itemLocations.add(submissionSign.getLocation());
        this.itemLocations.add(brewingStandBlock.getLocation());
        this.itemLocations.add(materialChestBlock.getLocation());
        this.itemLocations.add(submissionChestBlock.getLocation());
        this.itemLocations.add(waterCauldronBlock.getLocation());

        this.submissionChest = submissionChest;
        this.materialChest = materialChest;
        this.brewingStand = brewingStand;
        refillMaterials();
    }

    public void deleteStartItems() {
        for (Location location : this.itemLocations) {
            location.getBlock().setType(Material.AIR);
        }
    }

    // Calculates the next task to give the user based on their knowledge component mastery
    private void determineNextQuestion() {
        if (this.questionId == TASKS.length-1) {
            this.player.sendMessage(ChatColor.GREEN + "WOW YOU LEARNED IT ALL CONGRATULATIONS!!!");
            return;
        }

        this.waitingForPrompt = true;
        for (int i=this.questionId; i <= TASKS.length-1; i++) {
            Task tempTask = new Task(TASKS[i], this.player, null);
            // Check if knowledge components that are needed are mastered
            boolean goodTask = true;
            if (tempTask.neededKCs != null) {
                for (KNOWLEDGE_COMPONENTS neededKC : tempTask.neededKCs) {
                    int idx = knowledgeComponents.indexOf(new KnowledgeComponents(neededKC, KC_LEVELS.get(neededKC.toString())));
                    if (idx == -1) {
                        goodTask = false;
                        break;
                    }
                    if (!knowledgeComponents.get(idx).isMastered()) {
                        goodTask = false;
                        break;
                    }
                }

                if (!goodTask) { continue; }
            }

            // Check if knowledge components that will be learned are already mastered
            goodTask = false;
            if (tempTask.givenKCs != null) {
                for (KNOWLEDGE_COMPONENTS givenKC : tempTask.givenKCs) {
                    int idx = knowledgeComponents.indexOf(new KnowledgeComponents(givenKC, KC_LEVELS.get(givenKC.toString())));
                    if (idx == -1) {
                        goodTask = true;
                        break;
                    }
                    if (!knowledgeComponents.get(idx).isMastered()) {
                        goodTask = true;
                        break;
                    }
                }
            } else { // They've reached the end of the tutor
                this.questionId = i;
                break;
            }


            if (goodTask) {
                this.questionId = i;
                break;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;

        return this.player == ((StudentModel) obj).getPlayer();
    }

    @Override
    public String toString(){
        return this.player.getDisplayName();
    }
    @EventHandler
    public void cancelBlockBreak(BlockBreakEvent event) {
        if (this.itemLocations.contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

}

