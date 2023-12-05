package go.it.oleksandr.services;

import go.it.oleksandr.entities.Note;
import go.it.oleksandr.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Log4j2
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public Note add(Note note) {
        noteRepository.save(note);
        log.info("Note was added");
        return note;
    }

    public void deletedById(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("Note with id " + id + " not found");
        } else {
            noteRepository.deleteById(id);
            log.info("Note by " + id + " was deleted");
        }
    }

    public void update(Note note) {
        if(noteRepository.existsById(note.getId())){
                noteRepository.save(note);
        }else {
            throw new IllegalArgumentException("Note with id " + note.getId() + " not found");
        }
    }

    public Note getById(Long id) {
       return noteRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Note with id " + id + " not found"));
    }

    public List<Note> getByTitle(String searchedTitle) {
        List<Note> result;
        result = noteRepository.findNotesByTitle(searchedTitle);
        return result;
    }

}
