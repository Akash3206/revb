package com.revb.repository;

import com.revb.model.Subtopic;
import com.revb.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubtopicRepository extends JpaRepository<Subtopic, Long> {
    List<Subtopic> findBySubject(Subject subject);
}
