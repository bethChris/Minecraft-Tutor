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
            new ItemStack(Material.GLASS_BOTTLE, 1),
            new ItemStack(Material.GHAST_TEAR, 1),
            new ItemStack(Material.STRING, 1),
            new ItemStack(Material.INK_SAC, 1),
            new ItemStack(Material.APPLE, 1)
    };
    // List of all the tasks that can be provided to a user in order of difficulty
    public static final Task[] TASKS = new Task[] {
            new Task(
                    "Create an awkward potion",
                    new String[][]{
                            {
                                    "What do you need to put in the bottom section of the brewing stand?",
                                    "The first item you should start with is a water bottle",
                                    "Place a water bottle in one of the three bottom sections of the brewing stand"
                            },
                            {
                                    "What item can you use to turn a water bottle into an awkward potion?",
                                    "A plant from the nether may help",
                                    "Nether warts seem pretty awkward",
                                    "Place a nether wart in the top section of the brewing stand"
                            }},
                    new PotionType[]{
                            null,
                            PotionType.WATER
                    },
                    PotionType.AWKWARD,
                    null,
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.AWKWARD
                    }
            ),
            new Task(
                    "Create a potion of regeneration",
                    new String[][]{
                            {
                                    "What is the most basic potion you can make?",
                                    "Is there a base potion you can make with nether wart?",
                                    "First you need to start with an awkward potion",
                                    "Place an awkward potion in one of the three bottom sections of the brewing stand"
                            },
                            {
                                    "What ingredient can you add to an awkward potion to make it regeneration?",
                                    "A certain white colored mob from the nether may drop the needed ingredient",
                                    "Ghast tears can help to create a regeneration potion",
                                    "Place a ghast tear in the top section of the brewing stand"
                            }},
                    new PotionType[]{
                            null,
                            PotionType.AWKWARD
                    },
                    PotionType.REGEN,
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.AWKWARD
                    },
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.REGEN
                    }
            ),
            new Task(
                    "Congratulations, you've mastered the art of brewing!!!",
                    new String[][]{
                            {
                                    "There is nothing left for me to teach you"
                            }},
                    new PotionType[]{null},
                    null,
                    null,
                    null
            )
    };

    // TODO: I believe no longer needed, currently commented just in case we need it
//    // Creates an ItemStack of a potion for a given potionType
//    public static ItemStack makePotionItemStack(PotionType potionType) {
//        ItemStack stack = new ItemStack(Material.POTION);
//        PotionMeta thing = (PotionMeta)stack.getItemMeta();
//        thing.setBasePotionType(potionType);
//        stack.setItemMeta(thing);
//        return stack;
//    }
}
