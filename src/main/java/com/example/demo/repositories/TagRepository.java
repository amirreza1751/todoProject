package com.example.demo.repositories;

import com.example.demo.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    @Override
    Iterable<Tag> findAllById(Iterable<Long> longs);
}
