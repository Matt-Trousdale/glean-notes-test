package uk.co.cloudmatica.notes.service.delegate

import uk.co.cloudmatica.notes.model.Lecture
import uk.co.cloudmatica.notes.service.LectureService

class LectureConflictResolver {

    fun resolveLectureConflicts(remoteLecture: Lecture, localLecture: Lecture): String {

        return if (remoteLecture.name == localLecture.name) remoteLecture.name
        else LectureService.concat(localLecture.name, remoteLecture.name)
    }
}