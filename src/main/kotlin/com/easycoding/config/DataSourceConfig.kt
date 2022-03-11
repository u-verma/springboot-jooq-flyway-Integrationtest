package com.easycoding.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource
import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.TransactionProvider
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.ThreadLocalTransactionProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class DataSourceConfig(
    @Value("\${spring.datasource.url}") val url: String,
    @Value("\${spring.datasource.username}") val username: String,
    @Value("\${spring.datasource.password}") val password: String,
    @Value("\${spring.datasource.driver-class-name}") val driver: String,
    @Value("\${spring.datasource.hikari.connection-timeout}") val connectionTimeout: String,
    @Value("\${spring.datasource.hikari.maximum-pool-size}") val maxPoolSize: Int
) {
    @Profile("!test")
    @Bean
    fun dataSource(): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = url
        hikariConfig.username = username
        hikariConfig.password = password
        hikariConfig.connectionTimeout = java.lang.Long.valueOf(connectionTimeout)
        hikariConfig.isAutoCommit = true
        hikariConfig.transactionIsolation = "TRANSACTION_READ_COMMITTED"
        hikariConfig.poolName = "easycoding-db-pool"
        hikariConfig.maximumPoolSize = maxPoolSize
        return HikariDataSource(hikariConfig)
    }

    @Profile("!test")
    @Bean
    fun jooqTransactionProvider(dataSource: DataSource): TransactionProvider {
        return ThreadLocalTransactionProvider(DataSourceConnectionProvider(dataSource))
    }

    @Profile("!test")
    @Bean
    fun dslContext(dataSource: DataSource, transactionProvider: TransactionProvider): DSLContext {
        val config = DefaultConfiguration()
        config.setDataSource(dataSource)
        config.setSQLDialect(SQLDialect.POSTGRES)
        config.setTransactionProvider(transactionProvider)
        return DefaultDSLContext(config)
    }

    @Profile("!test")
    @Bean("flyway")
    fun flyway(): Flyway {
        val flyway = Flyway.configure()
            .dataSource(url, username, password)
            .schemas("public")
            .load()
        flyway.migrate()
        return flyway
    }
}