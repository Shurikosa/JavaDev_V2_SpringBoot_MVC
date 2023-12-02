package go.it.oleksandr.services;

import go.it.oleksandr.entities.Note;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Log4j2
@Service
@RequiredArgsConstructor
public class NoteService {
    private final List<Note> notesList;

    public List<Note> listAll() {

        return notesList;
    }

    public Note add(Note note) {
        Random random = new Random();
        note.setId(Math.abs(random.nextLong()));
        log.info("Note was added");
        notesList.add(note);
        return note;
    }

    public void deletedById(Long id) {
        boolean isRemoved = notesList.removeIf(note -> note.getId().equals(id));
        if (!isRemoved) {
            throw new IllegalArgumentException("Note with id " + id + " not found");
        } else {
            log.info("Note by " + id + " was deleted");
        }
    }

    public void update(Note note) {
        for (Note existingNote : notesList) {
            if (existingNote.getId().equals(note.getId())) {
                existingNote.setTitle(note.getTitle());
                existingNote.setContent(note.getContent());
                log.info("Note by " + note.getId() + " was updated");
                return;
            }
        }
        throw new IllegalArgumentException("Note with id " + note.getId() + " not found");
    }

    public Note getById(Long id) {
        for (Note note : notesList) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        throw new IllegalArgumentException("Note with id " + id + " not found");
    }

    public List<Note> getByTitle(String searchedTitle) {
        List<Note> result = new ArrayList<>();
        for (Note note : notesList) {
            String lowerCaseNote = note.getTitle().toLowerCase();
            if (lowerCaseNote.contains(searchedTitle.toLowerCase())) {
                result.add(note);
            }
        }
        return result;
    }

}
