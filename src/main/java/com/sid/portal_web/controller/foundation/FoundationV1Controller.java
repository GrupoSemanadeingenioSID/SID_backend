package com.sid.portal_web.controller.foundation;



import com.sid.portal_web.dto.request.FoundationRequest;
import com.sid.portal_web.dto.response.FoundationContactResponse;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.service.foundation.FoundationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/foundations")
@RequiredArgsConstructor
public class FoundationV1Controller{

    private final FoundationService foundationService;

    @GetMapping
    public ResponseEntity<Page<FoundationResponse>> findAll(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "asc", defaultValue = "true", required = false) boolean asc
    ) {
        return new ResponseEntity<>(foundationService.findAll(pageNo, pageSize, sortBy, asc), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoundationContactResponse> findById(@PathVariable int id){
        return foundationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Map<String, Object>> createFoundation(@RequestBody FoundationRequest foundationRequest) {
        foundationService.createFoundation(foundationRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 201);
        response.put("message", "Foundation created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> updateFoundation(
            @PathVariable("id") Integer foundationId,
            @RequestBody FoundationRequest foundationRequest) {

        foundationService.updateFoundation(foundationId, foundationRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Foundation updated successfully");

        return ResponseEntity.ok(response); // HttpStatus 200
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json", produces = "application/json")
    public ResponseEntity<?> patchFoundation(
            @PathVariable Integer id,
            @RequestBody JsonPatch patch) {

        FoundationPatchRequest updated = foundationService.patchFoundation(id, patch);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoundation(@PathVariable int id) {
        foundationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}