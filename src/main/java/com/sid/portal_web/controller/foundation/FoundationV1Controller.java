package com.sid.portal_web.controller.foundation;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/foundations")
public class FoundationV1Controller{

    @GetMapping
    public ResponseEntity<Page<List<?>>> findAll()
    {
        return ResponseEntity.ok().body(new PageImpl<>(List.of()));
    }

}