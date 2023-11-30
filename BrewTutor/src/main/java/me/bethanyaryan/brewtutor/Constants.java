package me.bethanyaryan.brewtutor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class Constants {
    private Constants() {
        // Restrict instantiation
    }

    public static final ItemStack[] MATERIALS = new ItemStack[] {
            new ItemStack(Material.NETHER_WART, 1),
            new ItemStack(Material.WATER, 1),
            new ItemStack(Material.GHAST_TEAR, 1),
            new ItemStack(Material.STRING, 1),
            new ItemStack(Material.INK_SAC, 1),
            new ItemStack(Material.APPLE, 1)
    };
    // List of all the tasks that can be provided to a user in order of difficulty
    public static final Task[] TASKS = new Task[] {
            new Task(
                    "Create an awkward potion",
                    new String[]{
                            "What is the first ingredient you brew when making a potion?",
                            "Nether warts can help to create an awkward potion"
                    },
                    PotionType.AWKWARD,
                    null,
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.AWKWARD
                    }
            ),
            new Task(
                    "Create a potion of regeneration",
                    new String[]{
                            "What is the first type of potion you should make?",
                            "First you need to make an awkward potion",
                            "What ingredient do you add to an awkward potion to make it regeneration?",
                            "Ghast tears can help to create a regeneration potion"
                    },
                    PotionType.REGEN,
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.AWKWARD
                    },
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.REGEN
                    }
            )
    };//TODO: maybe add a "final" task that is just "woot woot you finished"?
}
