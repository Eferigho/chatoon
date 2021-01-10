package com.chaton.repository;

import com.chaton.model.post.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository  extends JpaRepository<Video, Long> {
}
