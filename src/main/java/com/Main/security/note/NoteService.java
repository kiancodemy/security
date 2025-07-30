package com.Main.security.note;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements nodeServiceFunctions {

    private final NodeRepository  nodeRepository;

    public NoteService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Note createNote(String username, String content) {
        Note note = new Note();
        note.setUsername(username);
        note.setContent(content);
        nodeRepository.save(note);
        return note;
    }

    @Override
    public void deleteNote(Long id, String username) {
        nodeRepository.deleteById(id);

    }

    @Override
    public Note findNoteById(Long id) {
       return  nodeRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));

    }

    @Override
    public List<Note> getNotesForUser(String username) {
        return nodeRepository.findByUsername(username);
    }

    @Override
    public Note updateNote(Long id, String content,String username) {
        Note finder= nodeRepository.findById(id).orElseThrow(()-> new RuntimeException("Note not found"));
        finder.setContent(content);
        return nodeRepository.save(finder);
    };
}
