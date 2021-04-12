package demo

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface MessageRepository: CrudRepository<Message, String> {
    @Query("select * from messages")
    fun findMessages(): List<Message>

    override fun deleteById(id: String)
    override fun deleteAll()
}