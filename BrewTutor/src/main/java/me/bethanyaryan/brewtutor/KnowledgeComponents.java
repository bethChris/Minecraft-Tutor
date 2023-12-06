package me.bethanyaryan.brewtutor;

public class KnowledgeComponents {
    private KNOWLEDGE_COMPONENTS name = null;
    private double masteryLevel;
    private final double masteryAchievedVal;

    // The starting mastery level of each knowledge component is defaulted to 0.01
    public KnowledgeComponents(KNOWLEDGE_COMPONENTS name, double masteryAchievedVal) {
        this.name = name;
        this.masteryLevel = 0.01;
        this.masteryAchievedVal = masteryAchievedVal;
    }

    public boolean isMastered() { return this.masteryLevel >= this.masteryAchievedVal; }

    public void updateMastery(boolean evidence) {
        double slip = 0.1;
        double guess = 0.2;
        double acquisition = 0.6;
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
