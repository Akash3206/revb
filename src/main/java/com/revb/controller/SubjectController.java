package com.revb.controller;

import com.revb.model.Subject;
import com.revb.model.User;
import com.revb.repository.UserRepository;
import com.revb.repository.SubjectRepository;
import com.revb.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Subject> subjects = subjectRepository.findByUser(user);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping
    public ResponseEntity<Subject> addSubject(@RequestHeader("Authorization") String token,
                                              @RequestBody Subject subject) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();
        subject.setUser(user);
        Subject saved = subjectRepository.save(subject);
        return ResponseEntity.ok(saved);
    }
}
