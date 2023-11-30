package me.bethanyaryan.brewtutor;

public class KnowledgeComponents {
    private KNOWLEDGE_COMPONENTS name = null;
    private double masteryLevel;
    boolean[] successes = new boolean[10];

    public KnowledgeComponents(KNOWLEDGE_COMPONENTS name) {
        this.name = name;
        this.masteryLevel = 0.25;
    }

    public KNOWLEDGE_COMPONENTS getName() {
        return this.name;
    }

    public double getMasteryLevel() { return this.masteryLevel; }

    public boolean isMastered() { return this.masteryLevel >= 0.75; }

    public void updateMastery(boolean evidence) {
        double slip = 0.1;
        double guess = 0.1;
        double acquisition = 0.7;
        double probL1;
        if (evidence) {
            probL1 = (this.masteryLevel * (1-slip)) / (this.masteryLevel * (1-slip) + (1-this.masteryLevel) * guess);
        } else {
            probL1 = (this.masteryLevel * slip) / (this.masteryLevel * slip + (1-this.masteryLevel) * (1-guess));
        }

        this.masteryLevel = probL1 + (1 - probL1) * acquisition;
        System.out.println(this.masteryLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }

        final KnowledgeComponents other = (KnowledgeComponents) o;
        return this.name == other.name;
    }
}
