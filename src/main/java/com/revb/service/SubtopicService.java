package com.revb.service;

import com.revb.model.Subtopic;
import com.revb.model.Subject;
import com.revb.repository.SubtopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubtopicService {

    @Autowired
    private SubtopicRepository subtopicRepository;

    public Subtopic createSubtopic(Subtopic subtopic) {
        return subtopicRepository.save(subtopic);
    }

    public List<Subtopic> getSubtopicsBySubject(Subject subject) {
        return subtopicRepository.findBySubject(subject);
    }

    public Optional<Subtopic> getSubtopicById(Long id) {
        return subtopicRepository.findById(id);
    }

    public void deleteSubtopic(Long id) {
        subtopicRepository.deleteById(id);
    }
}
