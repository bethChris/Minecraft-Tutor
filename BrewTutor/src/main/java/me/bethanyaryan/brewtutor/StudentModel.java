package me.bethanyaryan.brewtutor;

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
    private List<KnowledgeComponents> knowledgeComponents = new ArrayList<KnowledgeComponents>();
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

        // For each knowledge component that the task requires, update the mastery accordingly
        for (KnowledgeComponents taskKC : this.currentTask.kc) {
            int idx = knowledgeComponents.indexOf(taskKC);
            if (idx == -1) {
                knowledgeComponents.add(new KnowledgeComponents(taskKC.getName()));
                knowledgeComponents.get(knowledgeComponents.size()-1).updateMastery(evidence);
            } else {
                knowledgeComponents.get(idx).updateMastery(evidence);
            }
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

