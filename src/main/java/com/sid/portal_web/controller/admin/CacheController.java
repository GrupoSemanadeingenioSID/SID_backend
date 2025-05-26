package com.sid.portal_web.controller.admin;

import com.sid.portal_web.utils.cache.TeamCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/cache")
public class CacheController {

    private final TeamCache teamCache;

    public CacheController(TeamCache teamCache) {
        this.teamCache = teamCache;
    }

    @GetMapping("teams/stats")
    public ResponseEntity<Map<String, Object>> getTeamCacheStats() {
        return ResponseEntity.ok(teamCache.getDetailedStats());
    }

}