package com.easycoding

import javax.sql.DataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(classes = [SpringbootJooqFlayway::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class AbstractIntegrationTest {

    @LocalServerPort
    val serverPort: Int = 0

    @Autowired
    lateinit var dataSource: DataSource

    @BeforeEach
    internal fun setUp() {
        val tables = listOf(
            "user_principal"
        )
        truncate(tables)
    }

    fun truncate(tables: List<String>) {
        val jdbcTemplate = JdbcTemplate(dataSource)
        tables.forEach { table -> jdbcTemplate.execute("truncate table $table") }
    }
}
