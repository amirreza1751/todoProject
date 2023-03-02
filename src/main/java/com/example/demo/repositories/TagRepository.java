package com.example.demo.repositories;

import com.example.demo.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Override
    List<Tag> findAllById(Iterable<Long> longs);
    Page<Tag> findAll(Pageable pageable);
}
