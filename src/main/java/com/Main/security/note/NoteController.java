package com.Main.security.note;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {
    private NoteService  noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("/post")
    public Note postNote(@AuthenticationPrincipal UserDetails userDetails,@RequestBody String content) {
        String UserDetailsName = userDetails.getUsername();
        return noteService.createNote(UserDetailsName,content);

    }

    @GetMapping
    public List<Note> getAllByUserNmae(@AuthenticationPrincipal UserDetails userDetails) {
        String UserDetailsName = userDetails.getUsername();
        return noteService.getNotesForUser(UserDetailsName);
    }

    @PutMapping("/{id}")
    public Note putNote(@PathVariable Long id, @RequestBody String content,@AuthenticationPrincipal UserDetails userDetails) {
        String UserDetailsName = userDetails.getUsername();
        return noteService.updateNote(id,content,UserDetailsName);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String UserDetailsName = userDetails.getUsername();
        noteService.deleteNote(id,UserDetailsName);
    }





}
