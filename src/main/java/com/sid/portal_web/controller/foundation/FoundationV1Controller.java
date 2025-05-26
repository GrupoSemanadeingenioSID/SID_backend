package com.sid.portal_web.controller.foundation;



import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.service.foundation.FoundationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/foundations")
@RequiredArgsConstructor
public class FoundationV1Controller{

    private final FoundationService foundationService;
    @GetMapping
    public ResponseEntity<Page<FoundationResponse>> findAll(
            @RequestParam(value = "pageNo", defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize

    )
    {
        return new ResponseEntity<>(foundationService.findAll(pageNo,pageSize), HttpStatus.OK);
    }

}