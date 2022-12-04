package cz.cvut.fit.tjv.art_commissions.app.exceptions;


public class CommissionException extends RuntimeException {
    public CommissionException() {}

    public CommissionException(String s) {
        super(s);
    }
}
