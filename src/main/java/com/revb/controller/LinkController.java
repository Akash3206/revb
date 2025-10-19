package com.revb.controller;

import com.revb.model.Link;
import com.revb.model.Subtopic;
import com.revb.model.User;
import com.revb.repository.LinkRepository;
import com.revb.repository.SubtopicRepository;
import com.revb.repository.UserRepository;
import com.revb.security.JwtUtil;
import com.revb.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/links")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubtopicRepository subtopicRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Get all links for a subtopic
    @GetMapping("/{subtopicId}")
    public ResponseEntity<List<Link>> getLinks(@RequestHeader("Authorization") String token,
                                               @PathVariable Long subtopicId) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();

        Subtopic subtopic = subtopicRepository.findById(subtopicId)
                .filter(st -> st.getSubject().getUser().getId().equals(user.getId()))
                .orElseThrow();

        List<Link> links = linkService.getLinksBySubtopic(subtopic);
        return ResponseEntity.ok(links);
    }

    // ✅ Add new link
    @PostMapping("/{subtopicId}")
    public ResponseEntity<Link> addLink(@RequestHeader("Authorization") String token,
                                        @PathVariable Long subtopicId,
                                        @RequestBody Link link) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();

        Subtopic subtopic = subtopicRepository.findById(subtopicId)
                .filter(st -> st.getSubject().getUser().getId().equals(user.getId()))
                .orElseThrow();

        link.setSubtopic(subtopic);
        Link saved = linkService.createLink(link);
        return ResponseEntity.ok(saved);
    }

    // ✅ Edit existing link
    @PutMapping("/{linkId}")
    public ResponseEntity<Link> updateLink(@RequestHeader("Authorization") String token,
                                           @PathVariable Long linkId,
                                           @RequestBody Link updatedLink) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();

        Link existing = linkRepository.findById(linkId)
                .filter(l -> l.getSubtopic().getSubject().getUser().getId().equals(user.getId()))
                .orElseThrow();

        existing.setTitle(updatedLink.getTitle());
        existing.setDescription(updatedLink.getDescription());
        existing.setUrl(updatedLink.getUrl());

        Link saved = linkRepository.save(existing);
        return ResponseEntity.ok(saved);
    }

    // ✅ Delete link
    @DeleteMapping("/{linkId}")
    public ResponseEntity<String> deleteLink(@RequestHeader("Authorization") String token,
                                             @PathVariable Long linkId) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(username).orElseThrow();

        Link existing = linkRepository.findById(linkId)
                .filter(l -> l.getSubtopic().getSubject().getUser().getId().equals(user.getId()))
                .orElseThrow();

        linkRepository.delete(existing);
        return ResponseEntity.ok("Link deleted successfully");
    }
}
