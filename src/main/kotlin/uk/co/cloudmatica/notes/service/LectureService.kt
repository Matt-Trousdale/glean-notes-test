package uk.co.cloudmatica.notes.service

import uk.co.cloudmatica.notes.model.Lecture
import uk.co.cloudmatica.notes.service.delegate.LectureConflictResolver
import uk.co.cloudmatica.notes.service.delegate.NotesConflictResolver

class LectureService(
    private val lectureConflictResolver: LectureConflictResolver,
    private val notesConflictResolver: NotesConflictResolver
) {

    companion object {
        fun concat(first: String, second: String) = first.plus(" / ".plus(second))
    }

    fun resolveLectures(remoteLecture: Lecture, localLecture: Lecture): Lecture {

        return Lecture(
            lectureConflictResolver.resolveLectureConflicts(remoteLecture, localLecture),
            notesConflictResolver.resolveNoteConflicts(localLecture.notes, remoteLecture.notes)
        )
    }
}