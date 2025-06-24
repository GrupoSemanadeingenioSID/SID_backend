package com.sid.portal_web.mapper;


import com.sid.portal_web.core.Members;
import com.sid.portal_web.dto.response.MembersResponse;
import com.sid.portal_web.entity.activity.ActivityMembersEntity;
import org.springframework.stereotype.Component;

@Component
public class MembersMapper {

    public static Members entityToCore(ActivityMembersEntity entity){
        return new Members(
                entity.getActivityMemberId(),
                entity.getName(),
                entity.getId()
        );
    }

    public static MembersResponse coreToResponse(Members member, boolean isMember){

        return MembersResponse.builder()
                .name(member.name())
                .identify(member.identify())
                .isMember(isMember)
                .build();
    }

}
