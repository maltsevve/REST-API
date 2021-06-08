package com.maltsevve.restApi.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "events")
@Data
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventId")
    private Long id;
    @Column(name = "EventTime")
    private Date eventTime;
    @Column(name = "Status")
    Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FileId")
    private File file;
}
