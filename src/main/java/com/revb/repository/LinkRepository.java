package com.revb.repository;

import com.revb.model.Link;
import com.revb.model.Subtopic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findBySubtopic(Subtopic subtopic);
}
