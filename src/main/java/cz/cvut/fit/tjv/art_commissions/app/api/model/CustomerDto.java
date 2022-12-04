package cz.cvut.fit.tjv.art_commissions.app.api.model;

import java.util.Collection;

public class CustomerDto {
    private Long id;
    private String name;

    private Collection<Long> myCommissions;

    public CustomerDto(Long id, String name, Collection<Long> myCommissions) {
        this.id = id;
        this.name = name;
        this.myCommissions = myCommissions;
    }

    // Getters and setters --------------------------------------------------------------------------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Long> getMyCommissions() {
        return myCommissions;
    }

    public void setMyCommissions(Collection<Long> myCommissions) {
        this.myCommissions = myCommissions;
    }
}
