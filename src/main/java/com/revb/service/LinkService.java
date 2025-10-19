package com.revb.service;

import com.revb.model.Link;
import com.revb.model.Subtopic;
import com.revb.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public Link createLink(Link link) {
        return linkRepository.save(link);
    }

    public List<Link> getLinksBySubtopic(Subtopic subtopic) {
        return linkRepository.findBySubtopic(subtopic);
    }

    public Optional<Link> getLinkById(Long id) {
        return linkRepository.findById(id);
    }

    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }
}
