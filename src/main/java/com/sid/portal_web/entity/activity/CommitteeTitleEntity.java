package com.sid.portal_web.entity.activity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "committee_title")
@Data
public class CommitteeTitleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "committee_title_id")
    private Integer committeeTitleId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;
}
