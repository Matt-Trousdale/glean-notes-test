package uk.co.cloudmatica.notes.service

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.co.cloudmatica.notes.model.Lecture
import uk.co.cloudmatica.notes.model.Note
import uk.co.cloudmatica.notes.service.delegate.LectureConflictResolver
import uk.co.cloudmatica.notes.service.delegate.NotesConflictResolver

class LectureServiceTest {

    @Test
    fun `Assert when calling for resolution, both resolvers are called`() {

        val lectureConflictResolverMock: LectureConflictResolver = mock()
        whenever(
            lectureConflictResolverMock.resolveLectureConflicts(
                Lecture("name", listOf()),
                Lecture("name", listOf())
            )
        ).thenReturn("na")
        val notesConflictResolverMock: NotesConflictResolver = mock()
        whenever(
            notesConflictResolverMock.resolveNoteConflicts(
                listOf(),
                listOf()
            )
        ).thenReturn(listOf(Note(1, 1, "text")))

        val underTest = LectureService(lectureConflictResolverMock, notesConflictResolverMock)

        underTest.resolveLectures(Lecture("name", listOf()), Lecture("name", listOf()))

        verify(lectureConflictResolverMock, times(1))
            .resolveLectureConflicts(Lecture("name", listOf()), Lecture("name", listOf()))

        verify(notesConflictResolverMock, times(1)).resolveNoteConflicts(listOf(), listOf())
    }
}