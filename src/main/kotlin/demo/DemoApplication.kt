package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String)

interface MessageRepository: CrudRepository<Message, String> {
	@Query("select * from messages")
	fun findMessages(): List<Message>
}

