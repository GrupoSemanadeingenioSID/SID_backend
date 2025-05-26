/**
 *
 * Prueba de ActivityEntity


import com.sid.portal_web.entity.activity.*;
import com.sid.portal_web.repository.activity.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ActivityEntitiesTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityDevRepository activityDevRepository;

    @Autowired
    private ActivityTitleRepository activityTitleRepository;

    @Autowired
    private ActivityMembersRepository activityMembersRepository;

    @Autowired
    private ActivityParticipationRepository activityParticipationRepository;

    @Autowired
    private CommitteeRepository committeeRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCompleteActivityWorkflow() {
        // 1. Crear datos de prueba
        CommitteeEntity committee = createCommittee();
        ActivityEntity activity = createActivity();
        ActivityTitleEntity title = createActivityTitle();
        ActivityMembersEntity member = createActivityMember();

        // 2. Crear relación ActivityDev
        ActivityDevEntity activityDev = createActivityDev(activity, committee);

        // 3. Crear participación
        ActivityParticipationEntity participation = createParticipation(activity, title, member);

        // 4. Persistir todos los datos
        entityManager.flush();
        entityManager.clear();

        // 5. Verificar los datos insertados
        verifyInsertedData(activity.getActivityId(), committee.getCommitteeId(),
                title.getTitleId(), member.getActivityMemberId());
    }

    private CommitteeEntity createCommittee() {
        CommitteeEntity committee = new CommitteeEntity();
        committee.setName("Comité de Prueba");
        committee.setDescription("Descripción del comité");
        committee.setObjectives("Objetivos del comité");
        committee.setStartDate(LocalDate.now());
        return committeeRepository.save(committee);
    }

    private ActivityEntity createActivity() {
        ActivityEntity activity = new ActivityEntity();
        activity.setTitle("Reunión de planificación");
        activity.setDescription("Planificación del proyecto X");
        activity.setPriority(ActivityEntity.Priority.LOW);
        activity.setStatus(ActivityEntity.Status.IN_PROGRESS);
        activity.setTotalHours(2);
        activity.setStartDate(LocalDate.now());
        return activityRepository.save(activity);
    }

    private ActivityTitleEntity createActivityTitle() {
        ActivityTitleEntity title = new ActivityTitleEntity();
        title.setDescription("Coordinador");
        return activityTitleRepository.save(title);
    }

    private ActivityMembersEntity createActivityMember() {
        ActivityMembersEntity member = new ActivityMembersEntity();
        member.setName("Juan Pérez");
        member.setId("EMP-001");
        return activityMembersRepository.save(member);
    }

    private ActivityDevEntity createActivityDev(ActivityEntity activity, CommitteeEntity committee) {
        ActivityDevId id = new ActivityDevId();
        id.setActivityId(activity.getActivityId());
        id.setCommitteeId(committee.getCommitteeId());

        ActivityDevEntity activityDev = new ActivityDevEntity();
        activityDev.setId(id);
        activityDev.setActivity(activity);
        activityDev.setCommittee(committee);
        activityDev.setDescription("Actividad principal");
        return activityDevRepository.save(activityDev);
    }

    private ActivityParticipationEntity createParticipation(ActivityEntity activity,
                                                            ActivityTitleEntity title,
                                                            ActivityMembersEntity member) {
        ActivityParticipationEntity participation = new ActivityParticipationEntity();
        participation.setMember(true);
        participation.setTitle(title);
        participation.setActivityMember(member);
        participation.setActivity(activity);
        return activityParticipationRepository.save(participation);
    }

    private void verifyInsertedData(Integer activityId, Integer committeeId,
                                    Integer titleId, Integer memberId) {
        // Verificar actividad
        ActivityEntity savedActivity = activityRepository.findById(activityId).orElseThrow();
        assertEquals("Reunión de planificación", savedActivity.getTitle());

        // Verificar relación ActivityDev
        ActivityDevId devId = new ActivityDevId();
        devId.setActivityId(activityId);
        devId.setCommitteeId(committeeId);
        ActivityDevEntity savedDev = activityDevRepository.findById(devId).orElseThrow();
        assertEquals("Actividad principal", savedDev.getDescription());

        // Verificar participación
        List<ActivityParticipationEntity> participations =
                activityParticipationRepository.findByActivity_ActivityId(activityId);
        assertEquals(1, participations.size());
        assertEquals("Juan Pérez", participations.get(0).getActivityMember().getName());
        assertEquals("Coordinador", participations.get(0).getTitle().getDescription());

        System.out.println("Prueba exitosa. Datos verificados:");
        System.out.println("Actividad: " + savedActivity.getTitle());
        System.out.println("Miembro: " + participations.get(0).getActivityMember().getName());
        System.out.println("Rol: " + participations.get(0).getTitle().getDescription());
    }
}

**/