package demo

import org.springframework.stereotype.Service

@Service
class MessageService(val repo: MessageRepository) {
    fun findMessages(): List<Message> = repo.findMessages()

    fun post(message: Message) {
        repo.save(message)
    }

    fun updateById(id: String, message: Message) {
        val storedMessage: Message = repo.findById(id).get()

        repo.save(storedMessage.copy(text = message.text))
    }
}