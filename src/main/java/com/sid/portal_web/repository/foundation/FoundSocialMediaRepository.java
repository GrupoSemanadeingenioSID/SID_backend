package com.sid.portal_web.repository.foundation;

import com.sid.portal_web.entity.Foundation.FoundSocialMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoundSocialMediaRepository extends JpaRepository<FoundSocialMediaEntity,Long> {
    List<FoundSocialMediaEntity> findByFoundationsContact_Id(Integer id);
}
