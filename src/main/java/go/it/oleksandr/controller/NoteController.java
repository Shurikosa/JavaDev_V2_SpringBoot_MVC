package go.it.oleksandr.controller;

import go.it.oleksandr.entities.Note;
import go.it.oleksandr.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final String REDIRECT = "redirect:/note/home";

    @GetMapping("/home")
    public ModelAndView getAllNotes(){
        ModelAndView result = new ModelAndView("note/home");
        result.addObject("noteList", noteService.listAll());
        return result;
    }
    @PostMapping("/home")
    public String saveNote(@RequestParam(name = "title", required = false) String title,
                           @RequestParam(name = "content", required = false) String content){
        System.out.println(title + " " + content + noteService.listAll().size());
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        noteService.add(note);
        System.out.println(note.getId());
        return REDIRECT;
    }

    @GetMapping("/edit_note/{id}")
    public String getEditNotePage (@PathVariable(name = "id") Long id, Model model){
       Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note/edit_note";
    }

    @PostMapping("/edit_note/{id}")
    public String editNote(@ModelAttribute(name = "note") Note note){
        noteService.update(note);
        return REDIRECT;
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam(name = "id") Long id){
        System.out.println(id);
        noteService.deletedById(id);
        return REDIRECT;
    }

    @GetMapping("/home/search")
    public String searchNoteByTitle(@RequestParam (name = "req") String req, Model model){
        List<Note> result = noteService.getByTitle(req);
        model.addAttribute("result", result);
        return "note/show_searched_note";
    }


}
