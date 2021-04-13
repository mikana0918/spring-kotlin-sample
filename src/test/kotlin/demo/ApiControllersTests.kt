package demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class ApiControllersTests(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var service: Service

    @MockkBean
    lateinit var repo: Repository

    @Test
    fun `一覧を表示できるか`() {
        val message1 = Message(
                "1",
                "ほげ"
        )
        val message2 = Message(
                "2",
                "ほげ"
        )
        val message3 = Message(
                "3",
                "ほげ"
                )

        every { service.findMessages() } returns listOf(
                message1,
                message2,
                message3
        )

        mockMvc.perform(get("/api").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // message1
                .andExpect(jsonPath("\$.[0].id").value(message1.id))
                .andExpect(jsonPath("\$.[0].text").value(message1.text))
                // message2
                .andExpect(jsonPath("\$.[1].id").value(message2.id))
                .andExpect(jsonPath("\$.[1].text").value(message2.text))
                // message3
                .andExpect(jsonPath("\$.[2].id").value(message3.id))
                .andExpect(jsonPath("\$.[2].text").value(message3.text))
    }

    @Test
    fun `新規で追加できるか（Idなし。Messageインスタンスをpost）`() {

        val message1 = Message(
                "1",
                "ほげ"
        )

        val objectMapper = ObjectMapper()

        every { service.post(message1) } returns message1

        mockMvc.perform(
                post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message1))
        ).andExpect(status().isOk)
    }

    @Test
    fun `新規で追加できるか（IdをUriに含み、Messageインスタンスもpost)`() {
        val id: String = "1"
        val message1 = Message(
                id,
                "ほげ"
        )

        val objectMapper = ObjectMapper()
        every { service.updateById(id, message1) } returns message1

        mockMvc.perform(
                post("/api/edit/${id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message1))
        ).andExpect(status().isOk)
    }

    @Test
    fun `全件削除できるか`() {
        every {repo.deleteAll()} returns Unit

        mockMvc.perform(
                delete(("/api"))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun `id指定による削除ができるか`() {
        val id: String = "1"
        val message1 = Message(
                id,
                "ほげ"
        )

        every { repo.deleteById(id)} returns Unit

        mockMvc.perform(
                delete(("/api/${id}"))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }
}