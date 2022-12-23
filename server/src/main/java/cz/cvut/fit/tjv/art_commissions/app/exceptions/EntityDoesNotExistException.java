package cz.cvut.fit.tjv.art_commissions.app.exceptions;

public class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException() {}

    public EntityDoesNotExistException(String s) {
        super(s);
    }
}