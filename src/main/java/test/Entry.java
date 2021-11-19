package test;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Entry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "x", nullable = false)
    private String x;
    @Column(name = "y", nullable = false)
    private String y;
    @Column(name = "r", nullable = false)
    private String r;
    @Column(name = "result", nullable = false)
    private String hitResult;
}