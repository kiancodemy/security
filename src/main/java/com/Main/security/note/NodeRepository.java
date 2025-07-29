package com.Main.security.note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeRepository extends JpaRepository<Note,Long> {

    List<Note> findByUsername(String username);

}
