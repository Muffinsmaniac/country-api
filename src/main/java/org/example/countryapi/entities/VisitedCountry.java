package org.example.countryapi.entities;

import jakarta.persistence.*;

@Entity
public class VisitedCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;

    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Country getCountry(){
        return country;
    }
}
