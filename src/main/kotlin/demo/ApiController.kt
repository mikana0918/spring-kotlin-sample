package demo

import org.springframework.web.bind.annotation.*

@RestController
class MessageController(val service: Service, val repo: Repository) {
    @GetMapping("/api")
    fun index(): List<Message> = service.findMessages()

    @PostMapping("/api")
    fun post(@RequestBody message: Message) {
        service.post(message)
    }

    @PostMapping("/api/edit/{id}")
    fun updateById(@PathVariable id: String, @RequestBody message: Message) {
        service.updateById(id, message)
    }

    @DeleteMapping("/api")
    fun deleteAll() {
        repo.deleteAll()
    }

    @DeleteMapping("/api/{id}")
    fun deleteById(@PathVariable id: String) {
        repo.deleteById(id)
    }
}