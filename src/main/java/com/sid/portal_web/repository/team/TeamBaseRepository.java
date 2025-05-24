package com.sid.portal_web.repository.team;


import com.sid.portal_web.core.TeamBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamBaseRepository {

    Page<TeamBase> findAll(Pageable pageable);

}
