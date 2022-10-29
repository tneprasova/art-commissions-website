package cz.cvut.fit.tjv.art_commissions.app.business;

public class EntityStateException extends RuntimeException {
    public EntityStateException() {}

    public EntityStateException(String s) {
        super(s);
    }

    public <Entity> EntityStateException(Entity entity) {
        super("Illegal state of entity " + entity);
    }
}