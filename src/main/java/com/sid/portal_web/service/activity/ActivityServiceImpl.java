package com.sid.portal_web.service.activity;


import com.sid.portal_web.core.Activities;
import com.sid.portal_web.dto.response.ActivityByIdResponse;
import com.sid.portal_web.dto.response.ActivityResponse;
import com.sid.portal_web.dto.response.CommitteesResponse;
import com.sid.portal_web.dto.response.MembersResponse;
import com.sid.portal_web.entity.activity.ActivityEntity;
import com.sid.portal_web.entity.activity.ActivityMembersEntity;
import com.sid.portal_web.entity.activity.CommitteeEntity;
import com.sid.portal_web.mapper.ActivityByIdMapper;
import com.sid.portal_web.mapper.ActivityMapper;
import com.sid.portal_web.mapper.CommitteesMapper;
import com.sid.portal_web.mapper.MembersMapper;
import com.sid.portal_web.repository.activity.ActivityParticipationRepository;
import com.sid.portal_web.repository.activity.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements IActivityService {

    private final ActivityParticipationRepository  activityParticipationRepository;
    private final ActivityRepository activityRepository;




    @Override
    public Page<ActivityResponse> getAll(Pageable pageable){

        Page<ActivityEntity> activityEntities = activityRepository.findAll(pageable);

        Map<Integer, String> managerNames = activityParticipationRepository
                .findByLeaderTitle()
                .stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],    // activity_id
                        row -> (String) row[1]   // manager_name
                ));

        return activityEntities
                .map(activityEntity -> {
                    Activities activityCore = ActivityMapper.entityToCore(
                            activityEntity,
                            managerNames.get(activityEntity.getActivityId())
                    );
                    return ActivityMapper.coreToResponse(activityCore);
                });

    }

    @Override
    public Optional<ActivityByIdResponse> getById(Integer activityId) {
        return activityRepository.findById(activityId)
                .map(activity -> {

                    ActivityEntity activityEntity = activityRepository.findById(activityId)
                            .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

                    List<MembersResponse> members = activity.getActivityParticipation()
                            .stream()
                            .map(participation ->
                            {
                                ActivityMembersEntity member = participation.getActivityMember();
                                return MembersMapper.coreToResponse(
                                        MembersMapper.entityToCore(member),
                                        participation.isMember()
                                );
                            })
                            .collect(Collectors.toList());

                    Map<Integer, String> leadersNames = activityRepository
                            .findLeaderCommitteeByActivityId(activityId)
                            .stream()
                            .collect(Collectors.toMap(
                                    row -> (Integer) row[0],    // activity_id
                                    row -> (String) row[1]   // leader_name
                            ));

                    List<CommitteesResponse> committees = activity.getActivityDev()
                            .stream()
                            .map(activityDevEntity ->
                            {
                                CommitteeEntity committee = activityDevEntity.getCommittee();
                                return CommitteesMapper.coreToResponse(
                                        CommitteesMapper.entityToCore(committee),
                                        leadersNames.get(committee.getCommitteeId())
                                );
                            })
                            .collect(Collectors.toList());

                    String managerName = activityParticipationRepository.findByLeaderTitleById(activityId);

            //MANAGER NAME
                    return ActivityByIdMapper.coreToResponse(
                            ActivityMapper.entityToCore(activityEntity, managerName),
                            members,
                            committees
                    );
                });
    }

}
