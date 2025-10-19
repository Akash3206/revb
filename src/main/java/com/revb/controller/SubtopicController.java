package com.revb.controller;

import com.revb.model.Subject;
import com.revb.model.Subtopic;
import com.revb.model.User;
import com.revb.repository.SubjectRepository;
import com.revb.repository.SubtopicRepository;
import com.revb.repository.UserRepository;
import com.revb.security.JwtUtil;
import com.revb.service.SubtopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/subtopics")
public class SubtopicController {

    @Autowired
    private SubtopicService subtopicService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{subjectId}")
    public ResponseEntity<List<Subtopic>> getSubtopics(@RequestHeader("Authorization") String token,
                                                       @PathVariable Long subjectId) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();

        Subject subject = subjectRepository.findById(subjectId)
                .filter(s -> s.getUser().getId().equals(user.getId()))
                .orElseThrow();

        List<Subtopic> subtopics = subtopicService.getSubtopicsBySubject(subject);
        return ResponseEntity.ok(subtopics);
    }

    @PostMapping("/{subjectId}")
    public ResponseEntity<Subtopic> addSubtopic(@RequestHeader("Authorization") String token,
                                                @PathVariable Long subjectId,
                                                @RequestBody Subtopic subtopic) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();

        Subject subject = subjectRepository.findById(subjectId)
                .filter(s -> s.getUser().getId().equals(user.getId()))
                .orElseThrow();

        subtopic.setSubject(subject);
        Subtopic saved = subtopicService.createSubtopic(subtopic);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubtopic(@PathVariable Long id) {
        subtopicService.deleteSubtopic(id);
        return ResponseEntity.ok("Subtopic deleted successfully");
    }
}
