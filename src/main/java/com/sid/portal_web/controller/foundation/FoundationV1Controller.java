package com.sid.portal_web.controller.foundation;



import com.sid.portal_web.dto.response.FoundationContactResponse;
import com.sid.portal_web.dto.response.FoundationResponse;
import com.sid.portal_web.service.foundation.FoundationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Aqui hace falta la autenticacion con token
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        foundationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}