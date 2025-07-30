package com.Main.security.note;

import java.util.List;

public interface nodeServiceFunctions {

    Note createNote(String username, String content);
    void deleteNote(Long id,String username);
    Note findNoteById(Long id);
    List<Note> getNotesForUser(String username);
    Note updateNote(Long id, String content,String username);


}
