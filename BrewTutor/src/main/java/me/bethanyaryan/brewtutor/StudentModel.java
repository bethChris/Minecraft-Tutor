package me.bethanyaryan.brewtutor;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.bethanyaryan.brewtutor.Constants.TASKS;

public class StudentModel {
    private final Player player;
    private int questionId;
    private int hintCount;
    private final List<KnowledgeComponents> knowledgeComponents = new ArrayList<>();
    private Task currentTask;
    public boolean waitingForPrompt;

    public StudentModel(Player player) {
        super();
        this.player = player;
        this.questionId = 0;
        this.hintCount = 0;
        this.waitingForPrompt = true;

    }
    public Player getPlayer(){
        System.out.println("Called get player with student model");
        return this.player;
    }
    public String getQuestion(){
        this.currentTask = new Task(TASKS[this.questionId], this.player, null); // TODO: Do we need plugin?
        return this.currentTask.toString();
    }

    public void submitTask(ItemStack submission) {
        boolean evidence = this.currentTask.checkConditionMet(submission);

        // For each knowledge component that the task teaches, update the mastery accordingly
        for (KNOWLEDGE_COMPONENTS updateKC : this.currentTask.givenKCs) {
            int idx = knowledgeComponents.indexOf(new KnowledgeComponents(updateKC));
            if (idx == -1) {
                knowledgeComponents.add(new KnowledgeComponents(updateKC));
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
    public int getQuestionId() {
        return this.questionId;
    }

    public int getHintCount(){
        return this.hintCount;
    }

    public void incrQuestion() {
        this.questionId += 1;
    }

    public void incrHintCount() {
        this.hintCount += 1;
    }

    public void resetHintCount() {
        this.hintCount = 0;
    }

    // Calculates the next task to give the user based on their knowledge component mastery
    private void determineNextQuestion() {
        if (this.questionId == TASKS.length-1) {
            this.player.sendMessage(ChatColor.GREEN + "WOW YOU LEARNED IT ALL CONGRATULATIONS!!!");
            return;
        }

        for (int i=this.questionId; i < TASKS.length-1; i++) {
            Task tempTask = new Task(TASKS[i], this.player, null);

            // Check if knowledge components that are needed are mastered
            boolean goodTask = true;
            if (tempTask.neededKCs != null) {
                for (KNOWLEDGE_COMPONENTS neededKC : tempTask.neededKCs) {
                    int idx = knowledgeComponents.indexOf(new KnowledgeComponents(neededKC));
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
            for (KNOWLEDGE_COMPONENTS givenKC : tempTask.givenKCs) {
                int idx = knowledgeComponents.indexOf(new KnowledgeComponents(givenKC));
                if (idx == -1) {
                    goodTask = true;
                    break;
                }
                if (!knowledgeComponents.get(idx).isMastered()) {
                    goodTask = true;
                    break;
                }
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
}

