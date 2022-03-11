package com.easycoding.container

import java.time.Duration
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait

const val POSTGRES_PORT = 5432

fun getPostgresContainer(): JdbcDatabaseContainer<*> {
    val container: JdbcDatabaseContainer<*> = PostgreSQLContainer<Nothing>("postgres:12.5-alpine")

    container.withDatabaseName("easycoding-db")
        ?.withUsername("postgres")
        ?.withPassword("postgres")
        ?.withExposedPorts(POSTGRES_PORT)
        ?.waitingFor(
            Wait.forLogMessage(
                ".*database system is ready to accept connections\n", 1
            )
                .withStartupTimeout(Duration.ofSeconds(60))
        )!!
        .start()
    Thread.sleep(5000)
    return container
}
