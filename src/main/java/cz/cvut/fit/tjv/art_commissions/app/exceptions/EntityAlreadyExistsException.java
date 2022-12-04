package cz.cvut.fit.tjv.art_commissions.app.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String s) {
        super(s);
    }
}
