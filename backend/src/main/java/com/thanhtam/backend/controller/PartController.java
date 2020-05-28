package com.thanhtam.backend.controller;

import com.thanhtam.backend.dto.PageResult;
import com.thanhtam.backend.entity.Part;
import com.thanhtam.backend.service.PartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api")
@Slf4j
public class PartController {
    private PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping(value = "/courses/{courseId}/parts")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResult getPartListByCourse(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, @PathVariable Long courseId) {
        Page<Part> parts = partService.getPartLisByCourse(pageable, courseId);
        return new PageResult(parts);
    }

    @PatchMapping(value = "/parts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePartName(@PathVariable Long id, @Valid @RequestBody String name) {
        Part part = partService.findPartById(id).get();
        part.setName(name);
        partService.savePart(part);
        return ResponseEntity.ok().body(part);
    }
}
