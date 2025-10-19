package com.revb.service;

import com.revb.model.Subject;
import com.revb.model.User;
import com.revb.repository.SubjectRepository;
import com.revb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjectsByUser(Long userId) {
        // Fetch the user first
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Then find subjects by the user
        return subjectRepository.findByUser(user);
    }

    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}
