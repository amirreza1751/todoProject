package com.example.demo.repositories;

import com.example.demo.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Override
    List<Todo> findAllById(Iterable<Long> longs);

    Page<Todo> findAll(Pageable pageable);
}
