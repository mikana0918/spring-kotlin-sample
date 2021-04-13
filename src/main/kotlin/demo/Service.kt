package demo

import org.springframework.stereotype.Service

@Service
class Service(val repo: Repository) {
    fun findMessages(): List<Message> = repo.findMessages()

    fun post(message: Message): Message {
        return repo.save(message)
    }

    fun updateById(id: String, message: Message): Message {
        val storedMessage: Message = repo.findById(id).get()

        return repo.save(storedMessage.copy(text = message.text))
    }
}