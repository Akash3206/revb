package com.revb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subtopic_id")
    @JsonBackReference
    private Subtopic subtopic;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(name = "insertion_order")
    private Integer insertionOrder;

    // ✅ Add JsonFormat so it’s properly serialized in JSON
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_added", nullable = false, updatable = false)
    private LocalDateTime dateAdded = LocalDateTime.now();

    public Link() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Subtopic getSubtopic() { return subtopic; }
    public void setSubtopic(Subtopic subtopic) { this.subtopic = subtopic; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Integer getInsertionOrder() { return insertionOrder; }
    public void setInsertionOrder(Integer insertionOrder) { this.insertionOrder = insertionOrder; }

    public LocalDateTime getDateAdded() { return dateAdded; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }
}
