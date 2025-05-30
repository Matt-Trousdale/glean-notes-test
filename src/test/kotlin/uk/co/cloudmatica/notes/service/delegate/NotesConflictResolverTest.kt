package uk.co.cloudmatica.notes.service.delegate

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uk.co.cloudmatica.notes.model.Note

class NotesConflictResolverTest {

    private val underTest = NotesConflictResolver()
    private val noteOfConflictLocal = Note(1, 3600, "A")
    private val noteOfConflictRemote = Note(1, 3200, "B")

    @Test
    fun `Assert conflicts are resolved, when text differ`() {

        val noteOfConflictLocal = Note(1, 3200, "A")

        val localNotes = listOf(noteOfConflictLocal, Note(2, 5600, "C"))
        val remoteNotes = listOf(Note(1, 3200, "B"))
        val actualNotes = underTest.resolveNoteConflicts(localNotes, remoteNotes)

        assertThat(actualNotes).isEqualTo(
            listOf(
                Note(1, 3200, "A / B"),
                Note(2, 5600, "C")
            )
        )
    }

    @Test
    fun `Assert only one name is present, when both names are the same`() {

        val noteOfConflictLocal = Note(1, 3200, "A")

        val localNotes = listOf(noteOfConflictLocal, Note(2, 5600, "C"))
        val remoteNotes = listOf(Note(1, 3200, "A"))
        val actualNotes = underTest.resolveNoteConflicts(localNotes, remoteNotes)

        assertThat(actualNotes).isEqualTo(
            listOf(
                Note(1, 3200, "A"),
                Note(2, 5600, "C")
            )
        )
    }

    @Test
    fun `Assert remote timestamp is returned, when ids are the same but timestamps are different`() {

        val localNotes = listOf(noteOfConflictLocal, Note(2, 5600, "C"))
        val remoteNotes = listOf(noteOfConflictRemote)
        val actualNotes = underTest.resolveNoteConflicts(localNotes, remoteNotes)

        assertThat(actualNotes).isEqualTo(
            listOf(
                Note(1, 3200, "A / B"),
                Note(2, 5600, "C")
            )
        )
    }

    @Test
    fun `Assert both sides returned, when there is more remote data`() {

        val localNotes = listOf(noteOfConflictLocal, Note(2, 5600, "C"))
        val remoteNotes = listOf(noteOfConflictRemote,
            Note(3, 3200, "D"),
            Note(4, 5600, "E"))
        val actualNotes = underTest.resolveNoteConflicts(localNotes, remoteNotes)

        assertThat(actualNotes).isEqualTo(
            listOf(
                Note(1, 3200, "A / B"),
                Note(2, 5600, "C"),
                Note(3, 3200, "D"),
                Note(4, 5600, "E")
            )
        )
    }

    @Test
    fun `Assert both sides returned, when there is more local data`() {

        val localNotes = listOf(noteOfConflictLocal, Note(2, 5600, "C"),
            Note(3, 3200, "D"),
            Note(4, 5600, "E"))
        val remoteNotes = listOf(noteOfConflictRemote)
        val actualNotes = underTest.resolveNoteConflicts(localNotes, remoteNotes)

        assertThat(actualNotes).isEqualTo(
            listOf(
                Note(1, 3200, "A / B"),
                Note(2, 5600, "C"),
                Note(3, 3200, "D"),
                Note(4, 5600, "E")
            )
        )
    }
}