/*package it.polito.did.gameskeleton

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor() {

    val question = Channel<List<QuestionDTO>>(Channel.CONFLATED)

    suspend fun loadQuestions(jsonInputStream: InputStream) {
        val questions = withContext(Dispatchers.IO) {
            kotlin.runCatching {
                jsonInputStream
                    .bufferedReader(Charsets.UTF_8)
                    .use { it.readText() }
                    .let { rawJson ->
                        adapter.fromJson(rawJson)?.questions ?: throw Exception("Could not parse questions")
                    }
            }
        }.getOrThrow()

        question.send(questions)
    }

}*/