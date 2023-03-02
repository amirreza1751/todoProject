package com.example.demo.controllers;

import com.example.demo.DTO.tag.TagDTO;
import com.example.demo.mapper.tag.TagMapper;
import com.example.demo.model.Tag;
import com.example.demo.services.TagServiceImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagServiceImpl tagService;
    private final TagMapper mapper;

    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
        this.mapper = Mappers.getMapper(TagMapper.class);
    }

    @GetMapping
    public ResponseEntity<Page<TagDTO>> getAllTags(@RequestParam(defaultValue = "0") int pageNo,
                                                   @RequestParam(defaultValue = "25") int pageSize,
                                                   @RequestParam(required = false) String sortBy,
                                                   @RequestParam(required = false) String sortDir) {
        return new ResponseEntity<>(tagService.getEntities(pageNo, pageSize, sortBy, sortDir).map(tag -> mapper.toDto(tag)),
                HttpStatus.OK);
    }
    @GetMapping({"/{tagId}"})
    public ResponseEntity<TagDTO> getTag(@PathVariable Long tagId) {
        return new ResponseEntity<>(mapper.toDto((Tag) tagService.getEntityById(tagId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TagDTO> saveTag(@RequestBody TagDTO tagDTO) {
        Tag tag = (Tag) tagService.insert(mapper.toEnity(tagDTO));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("tag", "/api/v1/tags/" + tag.getId().toString());
        return new ResponseEntity<>(mapper.toDto(tag), httpHeaders, HttpStatus.CREATED);
    }
    @PutMapping({"/{tagId}"})
    public ResponseEntity<TagDTO> updateTag(@PathVariable("tagId") Long tagId, @RequestBody TagDTO tagDTO) {
        tagService.updateEntity(tagId, mapper.toEnity(tagDTO));
        return new ResponseEntity<>(mapper.toDto((Tag) tagService.getEntityById(tagId)), HttpStatus.OK);
    }
    @DeleteMapping({"/{tagId}"})
    public ResponseEntity<?> deleteTag(@PathVariable("tagId") Long tagId) {
        tagService.deleteEntity(tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}