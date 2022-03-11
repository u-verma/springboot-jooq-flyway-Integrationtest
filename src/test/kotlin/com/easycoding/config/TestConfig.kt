package com.easycoding.config

import com.easycoding.container.POSTGRES_PORT
import com.easycoding.container.getPostgresContainer
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource
import mu.KotlinLogging
import org.flywaydb.core.Flyway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer

@Configuration
class TestConfig {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun databaseContainer(
        configurableApplicationContext: ConfigurableApplicationContext
    ): PostgreSQLContainer<*> {
        val container: JdbcDatabaseContainer<*> = getPostgresContainer()
        logger.info("Postgres test container started on port ${container.getMappedPort(POSTGRES_PORT)}")
        TestPropertyValues.of(
            "spring.datasource.url=${container.jdbcUrl}",
            "spring.datasource.username=${container.username}",
            "spring.datasource.password=${container.password}",
        ).applyTo(configurableApplicationContext.environment)
        return container as PostgreSQLContainer<*>
    }

    @Bean
    @Primary
    @DependsOn("databaseContainer")
    fun dataSource(
        @Value("\${spring.datasource.url}") url: String,
        @Value("\${spring.datasource.username}") username: String,
        @Value("\${spring.datasource.password}") password: String
    ): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = url
        hikariConfig.username = username
        hikariConfig.password = password
        hikariConfig.isAutoCommit = true
        hikariConfig.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        return HikariDataSource(hikariConfig)
    }

    @Bean
    @Primary
    @DependsOn("databaseContainer")
    fun flyway(
        @Value("\${spring.datasource.url}") url: String,
        @Value("\${spring.datasource.username}") username: String,
        @Value("\${spring.datasource.password}") password: String,
    ): Flyway {
        val flyway = Flyway.configure()
            .dataSource(url, username, password)
            .schemas("public")
            .load()
        flyway.migrate()
        return flyway
    }
}