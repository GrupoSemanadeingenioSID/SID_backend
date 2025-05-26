package com.sid.portal_web.entity.activity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "committee_member")
@Data
public class CommitteeMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "committee_member_id")
    private Integer committeeMemberId;

    @ManyToOne
    @JoinColumn(name = "committee_id", nullable = false)
    private CommitteeEntity committee;

    @ManyToOne
    @JoinColumn(name = "committee_title_id", nullable = false)
    private CommitteeTitleEntity title;
}
