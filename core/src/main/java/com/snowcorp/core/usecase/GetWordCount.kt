package com.snowcorp.core.usecase

import com.snowcorp.core.data.Note

class GetWordCount {
    operator fun invoke(note: Note) = getCount(note.content) + getCount(note.title)

    private fun getCount(str: String) =
            str.split(" ", "\n")
                    .filter {
                        it.contains((Regex(".*[a-zA-Z].*")))
                    }
                    .count()

}