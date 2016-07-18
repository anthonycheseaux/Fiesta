package com.example.Arnaud.myapplication.backend;


import com.googlecode.objectify.annotation.*;
import java.util.Date;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Entity
public class EventEntity {
    @Id
    private Long id;
    public Long getId() {return id;}

    private String name;
    public String getName() {return name;}

    private Date beginning;
    public Date getBeginning() {return beginning;}

    private Date end;
    public Date getEnd() {return end;}

    public EventEntity(String name, Date beginning) {
        this.name = name;
        this.beginning = beginning;
        this.end = beginning;
    }

    public EventEntity(String name, Date beginning, Date end) {
        this.name = name;
        this.beginning = beginning;
        this.end = end;
    }

    public EventEntity() {
    }
}
