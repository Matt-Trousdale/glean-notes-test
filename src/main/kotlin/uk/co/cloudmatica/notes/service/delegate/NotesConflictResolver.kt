package uk.co.cloudmatica.notes.service.delegate

import uk.co.cloudmatica.notes.model.Note
import uk.co.cloudmatica.notes.service.LectureService.Companion.concat

class NotesConflictResolver {

    fun resolveNoteConflicts(localNotes: List<Note>, remoteNotes: List<Note>): List<Note> {

        return localNotes
            .plus(remoteNotes)
            .groupBy { e -> e.id }
            .values
            .map { result -> noteResolution(result) }
    }

    private fun noteResolution(result: List<Note>) = if (result.size == 2) {

        val loc = result[0]
        val rem = result[1]
        if (loc.text != rem.text) {
            rem.text = concat(loc.text, rem.text)
            rem
        } else loc
    } else result[0]
}