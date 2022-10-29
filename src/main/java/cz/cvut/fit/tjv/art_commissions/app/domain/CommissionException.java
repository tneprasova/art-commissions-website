package cz.cvut.fit.tjv.art_commissions.app.domain;


public class CommissionException extends RuntimeException {
    public CommissionException() {}

    public CommissionException(String s) {
        super(s);
    }
}
