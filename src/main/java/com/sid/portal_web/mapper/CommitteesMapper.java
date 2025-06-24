package com.sid.portal_web.mapper;


import com.sid.portal_web.core.Committees;
import com.sid.portal_web.dto.response.CommitteesResponse;
import com.sid.portal_web.entity.activity.CommitteeEntity;
import org.springframework.stereotype.Component;

@Component
public class CommitteesMapper {

    public static Committees entityToCore(CommitteeEntity entity) {

        return new Committees(
                entity.getCommitteeId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    public static CommitteesResponse coreToResponse(Committees committee, String leader) {

        if (committee == null) {
            throw new IllegalArgumentException("Committee cannot be null");
        }

        return CommitteesResponse.builder()
                .name(committee.name())
                .leader(leader)
                .description(committee.description())
                .build();

    }

}
