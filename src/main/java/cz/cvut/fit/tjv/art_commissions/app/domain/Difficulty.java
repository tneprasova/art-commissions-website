package cz.cvut.fit.tjv.art_commissions.app.domain;

public enum Difficulty {
    EASY(8),
    MEDIUM(32),
    HARD(64);

    public final int hours;

    private Difficulty(int hours) {
        this.hours = hours;
    }
}
