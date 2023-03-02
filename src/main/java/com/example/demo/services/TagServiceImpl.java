package com.example.demo.services;

import com.example.demo.model.Tag;
import com.example.demo.model.exception.NoSuchEntityExistsException;
import com.example.demo.model.exception.OperationNotPermittedException;
import com.example.demo.repositories.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends GenericService<Tag> {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<Tag> getEntities(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable page = getSortingPageable(pageNo, pageSize, sortBy, sortDir);
        return tagRepository.findAll(page);
    }

    @Override
    public Tag getEntityById(Long id) {
        return tagRepository.findById(id).orElseThrow(
                () -> new NoSuchEntityExistsException(Tag.class.getSimpleName(), id)
        );
    }

    @Override
    public Tag insert(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void updateEntity(Long id, Tag tag) {
        Tag tagFromDb = tagRepository.findById(id).orElse(null);
        if (tagFromDb == null) throw new NoSuchEntityExistsException(Tag.class.getSimpleName(), id);
        tagFromDb.setName(tag.getName());
        tagRepository.save(tagFromDb);
    }

    @Override
    public void deleteEntity(Long tagId) {
        if (tagId == null) throw new NoSuchEntityExistsException(Tag.class.getSimpleName(), null);
        Tag tagFromDb = tagRepository.findById(tagId).orElse(null);
        if (tagFromDb == null) throw new NoSuchEntityExistsException(Tag.class.getSimpleName(), tagId);
        if (tagFromDb.getTodoSet().size() != 0) throw new OperationNotPermittedException(Tag.class.getSimpleName(), tagId);
        tagRepository.deleteById(tagId);
    }

    public ArrayList<Tag> findAllById(Long[] ids){
        return (ArrayList<Tag>) tagRepository.findAllById(List.of(ids));
    }

    public Iterable<Tag> saveAll(Iterable<Tag> tags){
        return tagRepository.saveAll(tags);
    }
}