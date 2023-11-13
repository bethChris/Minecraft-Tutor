package me.bethanyaryan.brewtutor;

public class KnowledgeComponents {
    final String name;
    double masteryLevel;
    boolean[] successes = new boolean[10];
    public KnowledgeComponents(String name) {
        this.name = name;
        this.masteryLevel = 0.25;
    }

    private void checkMastery(boolean evidence) {
        double slip = 0.1;
        double guess = 0.2;
        double acquisition = 0.7;
        double probL1;
        if (evidence) {
            probL1 = (this.masteryLevel * (1-slip)) / (this.masteryLevel * (1-slip) + (1-this.masteryLevel) * guess);
        } else {
            probL1 = (this.masteryLevel * slip) / (this.masteryLevel * slip + (1-this.masteryLevel) * (1-guess));
        }

        this.masteryLevel = probL1 + (1 - probL1) * acquisition;
    }
}
