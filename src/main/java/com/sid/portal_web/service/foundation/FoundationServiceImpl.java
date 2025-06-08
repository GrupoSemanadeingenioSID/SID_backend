package com.sid.portal_web.service.foundation;

import com.sid.portal_web.core.Foundation;
import com.sid.portal_web.core.FoundationContact;
import com.sid.portal_web.dto.response.FoundationContactResponse;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.dto.response.SocialMediaResponse;
import com.sid.portal_web.entity.FoundSocialMediaEntity;
import com.sid.portal_web.entity.FoundationContactEntity;
import com.sid.portal_web.entity.FoundationEntity;
import com.sid.portal_web.entity.SocialMediaEntity;
import com.sid.portal_web.mapper.foundation.FoundationContactMapper;
import com.sid.portal_web.mapper.foundation.FoundationMapper;
import com.sid.portal_web.mapper.foundation.SocialMediaMapper;
import com.sid.portal_web.repository.foundation.FoundSocialMediaRepository;
import com.sid.portal_web.repository.foundation.FoundationContactRepository;
import com.sid.portal_web.repository.foundation.FoundationRepository;
import com.sid.portal_web.repository.foundation.SocialMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoundationServiceImpl implements FoundationService {
    // Repositorios
    private final FoundationRepository foundationRepository;
    private final FoundSocialMediaRepository foundSocialMediaRepository;
    private final FoundationContactRepository foundationContactRepository;
    private final SocialMediaRepository socialMediaRepository;

    private final FoundationMapper foundationMapper;
    private final FoundationContactMapper foundationContactMapper;
    private final SocialMediaMapper socialMediaMapper;

    @Override
    public Page<FoundationResponse> findAll(int page, int pageSize, String sortBy, boolean asc) {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<FoundationEntity> pageEntities = foundationRepository.findAll(pageable);

        return pageEntities
                .map(foundationEntity -> {
                    Foundation domain = foundationMapper.entityToDomain(foundationEntity);
                    return foundationMapper.domainToResponse(domain);
                });
    }


    @Override
    public Optional<FoundationContactResponse> findById(int id) {
        return foundationContactRepository.findById(id)
                .map(entity -> {
                    // Extraer redes sociales
                    List<Integer> socialMediaIds = entity.getFoundationSocial().stream()
                            .map(FoundSocialMediaEntity::getFoundSocialMediaId)
                            .collect(Collectors.toList());

                    List<SocialMediaResponse> socialMediaFoundation = socialMediaRepository.findAllById(socialMediaIds).stream()
                            .map(socialMediaMapper::entityToDomain)
                            .map(socialMediaMapper::domainToResponse)
                            .toList();

                    return foundationContactMapper.domainToResponse(
                            foundationContactMapper.entityToDomain(entity),
                            socialMediaFoundation);
                });
    }

    @Override
    public void deleteById(int id) {
        foundationContactRepository.deleteById(id);
    }
}
