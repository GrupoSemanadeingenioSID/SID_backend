package com.sid.portal_web.service.foundation;
import com.sid.portal_web.core.*;

import com.sid.portal_web.dto.request.*;
import com.sid.portal_web.dto.response.*;
import com.sid.portal_web.entity.Foundation.*;
import com.sid.portal_web.mapper.foundation.*;
import com.sid.portal_web.repository.foundation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


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

    // Mappers
    private final FoundationMapper foundationMapper;
    private final FoundationContactMapper foundationContactMapper;
    private final SocialMediaMapper socialMediaMapper;
    private final FoundContactDashboardMapper foundContactDashboardMapper;

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
                    List<Integer> socialMediaIds = entity.getFoundationSocial().stream()
                            .filter(s -> s.getFoundationsContact().getId().equals(id))
                            .map(s -> Integer.parseInt(s.getSocialMedia().getId()))
                            .collect(Collectors.toList());

                    List<SocialMediaResponse> socialMediaFoundation = socialMediaRepository.findAllById(socialMediaIds).stream()
                            .map(socialMediaMapper::entityToDomain)
                            .map(socialMediaMapper::domainToResponse)
                            .toList();

                    FoundationEntity foundationEntity = foundationRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Fundación no encontrada con id " + id));

                    Foundation foundation = foundationMapper.entityToDomain(foundationEntity);

                    return foundationContactMapper.domainToResponse(
                            foundationContactMapper.entityToDomain(entity),
                            socialMediaFoundation,
                            foundation
                    );
                });
    }

    @Override
    public Optional<FoundationDashboardResponse> findByIdDashboard(int id) {
        return foundationContactRepository.findById(id)
                .map(entity -> {
                    FoundationEntity foundationEntity = foundationRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Fundación no encontrada con id " + id));

                    FoundationContact foundationContact = foundationContactMapper.entityToDomain(entity);
                    Foundation foundation = foundationMapper.entityToDomain(foundationEntity);

                    List<Integer> socialMediaIds = entity.getFoundationSocial().stream()
                            .filter(s -> s.getFoundationsContact().getId().equals(id))
                            .map(s -> Integer.parseInt(s.getSocialMedia().getId()))
                            .collect(Collectors.toList());

                    List<SocialMediaResponse> socialMediaFoundation = socialMediaRepository.findAllById(socialMediaIds).stream()
                            .map(socialMediaMapper::entityToDomain)
                            .map(socialMediaMapper::domainToResponse)
                            .toList();

                    return foundContactDashboardMapper.domainToResponse(
                            foundationContact,
                            foundation,
                            foundationEntity.getProjectsInFoundation().size(),
                            socialMediaFoundation
                    );
                });
    }

    @Override
    public void createFoundation(FoundationRequest foundationRequest){
        Foundation foundation = foundationMapper.requestToDomain(foundationRequest);
        FoundationContact foundationContact = foundationContactMapper.requestToDomain(foundationRequest);

        FoundationEntity foundationEntity = foundationMapper.domainToEntity(foundation);
        FoundationContactEntity foundationContactEntity = foundationContactMapper.domainToEntity(foundationContact);

        foundationContactEntity.setFoundation(foundationEntity);

        List<SocialMediaEntity> socialMediaEntities = socialMediaRepository
                .findAllById(foundationRequest.getSocialMediaIds().stream()
                        .map(Integer::parseInt)
                        .toList());


        List<FoundSocialMediaEntity> foundSocialMediaEntities = socialMediaEntities.stream()
                .map(socialMedia -> FoundSocialMediaEntity.builder()
                        .socialMedia(socialMedia)
                        .foundationsContact(foundationContactEntity)
                        .build())
                .toList();

        foundationContactEntity.setFoundationSocial(foundSocialMediaEntities);

        foundationContactRepository.save(foundationContactEntity);
    }

    @Override
    public void updateFoundation(Integer idExistingFoundation,FoundationRequest foundationRequest){
        FoundationContactEntity existing = foundationContactRepository.findById(idExistingFoundation)
                .orElseThrow(() -> new RuntimeException("Fundación no encontrada"));

        // 1. Mapper para actualizar entidad base desde core
        FoundationContact updatedCore = foundationContactMapper.requestToDomain(foundationRequest);
        foundationContactMapper.updateEntityFromDomain(existing, updatedCore);

        // 2. Actualizar datos de foundation directamente desde el DTO (porque no están en el core)
        existing.getFoundation().setDescription(foundationRequest.getDescription());
        existing.getFoundation().setLogo_url(foundationRequest.getLogo_url());

        // 3. Actualizar redes sociales
        existing.getFoundationSocial().clear();

        List<SocialMediaEntity> socialMediaEntities = socialMediaRepository
                .findAllById(foundationRequest.getSocialMediaIds().stream()
                        .map(Integer::parseInt)
                        .toList());

        List<FoundSocialMediaEntity> newSocialLinks = socialMediaEntities.stream()
                .map(social -> FoundSocialMediaEntity.builder()
                        .socialMedia(social)
                        .foundationsContact(existing)
                        .build())
                .toList();

        existing.getFoundationSocial().addAll(newSocialLinks);

        // 4. Guardar
        foundationContactRepository.save(existing);
    }

    @Override
    public void deleteById(int id) {
        foundationContactRepository.deleteById(id);
    }
}
