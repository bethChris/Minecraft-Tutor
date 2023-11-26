package me.bethanyaryan.brewtutor;

import org.bukkit.inventory.BrewerInventory;

import java.util.ArrayList;

public class Question {
    private String prompt;
    private ArrayList<String> knowledgeComponents;
    private ArrayList<String> hints;
    private BrewerInventory finalState;

    public Question(String prompt, ArrayList<String> knowledgeComponents, ArrayList<String> hints, BrewerInventory finalState){
        this.prompt = prompt;
        this.knowledgeComponents = knowledgeComponents;
        this.hints = hints;
        this.finalState = finalState;
    }

    public boolean grade(BrewerInventory studentState) {
        return studentState == finalState;
    }

    public String getHint(Integer attempt) {
        int numberOfHints = this.hints.size();

        if (this.hints.isEmpty() ){
            return "No hints are available";
        }else if (attempt >= numberOfHints){
            return this.hints.get(numberOfHints-1);
        }else {
            return this.hints.get(attempt % numberOfHints);
        }
    }


}
