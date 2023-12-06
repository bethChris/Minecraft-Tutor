package me.bethanyaryan.brewtutor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.Dictionary;
import java.util.Hashtable;

public class Constants {
    private Constants() {
        // Restrict instantiation
    }

    // All the available potion materials that will appear in the material chest
    public static final ItemStack[] MATERIALS = new ItemStack[] {
            new ItemStack(Material.GLASS_BOTTLE, 1),
            new ItemStack(Material.NETHER_WART, 1),
            new ItemStack(Material.GHAST_TEAR, 1),
            new ItemStack(Material.FERMENTED_SPIDER_EYE, 1),
            new ItemStack(Material.GLOWSTONE_DUST, 1),
            new ItemStack(Material.REDSTONE, 1),
            new ItemStack(Material.BLAZE_POWDER, 1),
            new ItemStack(Material.MAGMA_CREAM, 1),
            new ItemStack(Material.SPIDER_EYE, 1),
            new ItemStack(Material.GLISTERING_MELON_SLICE, 1),
            new ItemStack(Material.SUGAR, 1),
            new ItemStack(Material.RABBIT_FOOT, 1),
            new ItemStack(Material.PUFFERFISH, 1),
            new ItemStack(Material.GOLDEN_CARROT, 1),
            new ItemStack(Material.GUNPOWDER, 1),
            new ItemStack(Material.TURTLE_HELMET, 1),
            new ItemStack(Material.DRAGON_BREATH, 1),
            new ItemStack(Material.PHANTOM_MEMBRANE, 1)
    };

    // List of all the tasks that can be provided to a user in order of difficulty
    // Each task follows the template:
    //      Prompt
    //      Hints[Step of task player is on][Specificity of hint]
    //      Hint Items[Item to check for to see if player is on a specific step]
    //      Type of potion the player needs to create
    //      The knowledge components needed to get task assigned to player
    //      The knowledge components that the task will update for the player
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
                    "Create a potion of leaping",
                    new String[][]{
                            {
                                    "What is the most basic potion you can make?",
                                    "Is there a base potion you can make with nether wart?",
                                    "First you need to start with an awkward potion",
                                    "Place an awkward potion in one of the three bottom sections of the brewing stand"
                            },
                            {
                                    "What ingredient can you add to an awkward potion to make it leaping?",
                                    "A certain long-eared mob with mad hops may drop the needed ingredient",
                                    "A rabbit's foot can help to create a potion of leaping",
                                    "Place a rabbit's foot in the top section of the brewing stand"
                            }},
                    new PotionType[]{
                            null,
                            PotionType.AWKWARD
                    },
                    PotionType.JUMP,
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.AWKWARD
                    },
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.LEAP
                    }
            ),
            new Task(
                    "Create a potion of healing",
                    new String[][]{
                            {
                                    "What is the most basic potion you can make?",
                                    "Is there a base potion you can make with nether wart?",
                                    "First you need to start with an awkward potion",
                                    "Place an awkward potion in one of the three bottom sections of the brewing stand"
                            },
                            {
                                    "What ingredient can you add to an awkward potion to make it healing?",
                                    "A fruit adorned with gold can provide the healing you seek",
                                    "A glistering melon can help to create a potion of healing",
                                    "Place a glistering melon in the top section of the brewing stand"
                            }},
                    new PotionType[]{
                            null,
                            PotionType.AWKWARD
                    },
                    PotionType.INSTANT_HEAL,
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.AWKWARD
                    },
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.HEAL
                    }
            ),
            new Task(
                    "Create a potion of slowness",
                    new String[][]{
                            {
                                    "What is the most basic potion you can make?",
                                    "Is there a base potion you can make with nether wart?",
                                    "First you need to start with an awkward potion",
                                    "Place an awkward potion in one of the three bottom sections of the brewing stand"
                            },
                            {
                                    "Before you create a slowness potion you need to increase your movement",
                                    "What kind of potion helped make you more maneuverable?",
                                    "First you must create a potion of leaping",
                                    "What ingredient can you add to an awkward potion to make it leaping?",
                                    "A certain long-eared mob with mad hops may drop the needed ingredient",
                                    "A rabbit's foot can help to create a potion of leaping",
                                    "Place a rabbit's foot in the top section of the brewing stand"
                            },
                            {
                                    "What ingredient can you add to a potion of leaping to make it slowness?",
                                    "A body part of a certain eight-legged mob need to be modified to get the needed ingredient",
                                    "A fermented spider eye can help to create a potion of slowness",
                                    "Place a fermented spider eye in the top section of the brewing stand"
                            }},
                    new PotionType[]{
                            null,
                            PotionType.AWKWARD,
                            PotionType.JUMP
                    },
                    PotionType.SLOWNESS,
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.AWKWARD,
                            KNOWLEDGE_COMPONENTS.LEAP
                    },
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.SLOW
                    }
            ),
            new Task(
                    "Create a potion of harming",
                    new String[][]{
                            {
                                    "What is the most basic potion you can make?",
                                    "Is there a base potion you can make with nether wart?",
                                    "First you need to start with an awkward potion",
                                    "Place an awkward potion in one of the three bottom sections of the brewing stand"
                            },
                            {
                                    "Before you create a harming potion you need to do the opposite",
                                    "What kind of potion increases your health?",
                                    "First you must create a potion of healing",
                                    "What ingredient can you add to an awkward potion to make it healing?",
                                    "A fruit adorned with gold can provide the healing you seek",
                                    "A glistering melon can help to create a potion of healing",
                                    "Place a glistering melon in the top section of the brewing stand"
                            },
                            {
                                    "What ingredient can you add to a potion of healing to make it harming?",
                                    "A body part of a certain eight-legged mob need to be modified to get the needed ingredient",
                                    "A fermented spider eye can help to create a potion of harming",
                                    "Place a fermented spider eye in the top section of the brewing stand"
                            }},
                    new PotionType[]{
                            null,
                            PotionType.AWKWARD,
                            PotionType.INSTANT_HEAL
                    },
                    PotionType.INSTANT_DAMAGE,
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.AWKWARD,
                            KNOWLEDGE_COMPONENTS.HEAL
                    },
                    new KNOWLEDGE_COMPONENTS[]{
                            KNOWLEDGE_COMPONENTS.HARM
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

    // Dictionary containing the value that a knowledge component's mastery level
    // needs to be considered mastered
    // Level of 0.6 requires only one correct submission
    // Level of 0.95 requires two correct submissions
    public static final Dictionary<String, Double> KC_LEVELS = new Hashtable<>();
    static {
        KC_LEVELS.put(KNOWLEDGE_COMPONENTS.AWKWARD.toString(), 0.60);
        KC_LEVELS.put(KNOWLEDGE_COMPONENTS.REGEN.toString(), 0.60);
        KC_LEVELS.put(KNOWLEDGE_COMPONENTS.LEAP.toString(), 0.95);
        KC_LEVELS.put(KNOWLEDGE_COMPONENTS.HEAL.toString(), 0.95);
        KC_LEVELS.put(KNOWLEDGE_COMPONENTS.SLOW.toString(), 0.95);
        KC_LEVELS.put(KNOWLEDGE_COMPONENTS.HARM.toString(), 0.95);
    }
}
