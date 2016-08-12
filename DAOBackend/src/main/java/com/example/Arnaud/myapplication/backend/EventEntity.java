package com.example.Arnaud.myapplication.backend;


import com.googlecode.objectify.annotation.*;
import java.util.Date;

/**
 * Created by Arnaud on 17.07.2016.
 */

@Entity
public class EventEntity {
    public static final String CLASS_PREFIX = "event_";

    public static final String ID = CLASS_PREFIX+"id";
    @Id
    private Long id;
    public Long getId() {return id;}


    public static final String NAME = CLASS_PREFIX+"name";
    private String name;
    public String getName() {return name;}

    public static final String BEGINNING = CLASS_PREFIX+"beginning";
    private Date beginning;
    public Date getBeginning() {return beginning;}

    public static final String END = CLASS_PREFIX+"end";
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

    public EventEntity(Long id, String name, Date beginning, Date end) {
        this.id = id;
        this.name = name;
        this.beginning = beginning;
        this.end = end;
    }
}
