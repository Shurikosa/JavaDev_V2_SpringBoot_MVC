package go.it.oleksandr.repository;

import go.it.oleksandr.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("FROM Note n WHERE n.title LIKE :request")
    List<Note> findNotesByTitle(@Param("request") String request);
}
