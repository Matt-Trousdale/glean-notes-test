package uk.co.cloudmatica.notes.service.delegate

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.co.cloudmatica.notes.model.Lecture
import uk.co.cloudmatica.notes.model.Note

class LectureConflictResolverTest {

    private val underTest = LectureConflictResolver()

    @Test
    fun `Assert conflicts are resolved, when both names are present and different`() {

        val noteOfConflictLocal = Note(1, 3200, "A")
        val noteOfConflictRemote = Note(1, 3200, "B")

        val localLecture = Lecture("Name 1", listOf(noteOfConflictLocal, Note(2, 5600, "C")))
        val remoteLecture = Lecture("Name 2", listOf(noteOfConflictRemote))
        val actualLecture = underTest.resolveLectureConflicts(remoteLecture, localLecture)

        assertThat(actualLecture).isEqualTo("Name 1 / Name 2")
    }

    @Test
    fun `Assert only one name is present, when both names are the same`() {

        val notesOfLocalLecture: List<Note> = listOf(
            Note(1, 3200, "A"),
            Note(2, 5600, "C")
        )
        val noteOfRemoteConflict = Note(1, 3600, "B")

        val localLecture = Lecture("Name 1", notesOfLocalLecture)
        val remoteLecture = Lecture("Name 1", listOf(noteOfRemoteConflict))
        val actualLecture = underTest.resolveLectureConflicts(remoteLecture, localLecture)

        assertThat(actualLecture).isEqualTo("Name 1")
    }
}