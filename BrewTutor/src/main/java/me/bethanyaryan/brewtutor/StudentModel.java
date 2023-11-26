package me.bethanyaryan.brewtutor;

import org.bukkit.entity.Player;

public class StudentModel {
    private final Player player;
    private int question;
    private int hintCount;

    public StudentModel(Player player) {
        super();
        this.player = player;
        this.question = 0;
        this.hintCount = 0;

    }
    public Player getPlayer(){
        System.out.println("Called get player with student model");
        return this.player;
    }
    public int getQuestion(){
        return this.question;
    }

    public int getHintCount(){
        return this.hintCount;
    }

    public void incrQuestion() {
        this.question += 1;
    }

    public void incrHintCount() {
        this.hintCount += 1;
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

