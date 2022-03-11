package jooqutils.generator

import java.time.Duration
import org.flywaydb.core.Flyway
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait

data class DbConfig(val schema: String, val username: String, val password: String)

class Table(val name: String)

class JooqGenerator(private val dbConfig: DbConfig) {
    private fun dbUrl() = postgresContainer.jdbcUrl

    private val postgresContainer = KPostgreSQLContainer()
        .withDatabaseName("easycoding-db")
        .withUsername(dbConfig.username)
        .withPassword(dbConfig.password)
        .waitingFor(
            Wait.forLogMessage(
                ".*database system is ready to accept connections\n", 1
            )
                .withStartupTimeout(Duration.ofSeconds(60))
        )

    private fun startContainers() {
        postgresContainer.start()
        Thread.sleep(5000)
    }

    private fun migrate() {
        Flyway.configure()
            .dataSource(dbUrl(), dbConfig.username, dbConfig.password)
            .schemas(dbConfig.schema)
            .load()
            .migrate()
    }

    private fun tableInclusions(tables: List<Table>): String {
        return tables.joinToString(separator = "|") { it.name }
    }

    private fun databaseConfig(tables: List<Table>): Database {
        return Database()
            .withName("org.jooq.meta.postgres.PostgresDatabase")
            .withInputSchema(dbConfig.schema)
            .withIncludes(tableInclusions(tables))
            .withIncludePrimaryKeys(true)
            .withIncludeForeignKeys(true)
            .withIncludeUniqueKeys(true)
    }


    private fun codeGen(tables: List<Table>) {
        val configuration = Configuration()
            .withJdbc(
                Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl(dbUrl())
                    .withUser(dbConfig.username)
                    .withPassword(dbConfig.password)
            )
            .withGenerator(
                Generator()
                    .withGenerate(
                        Generate()
                            .withRoutines(false)
                            .withRecords(true)
                            .withImmutablePojos(true)
                            .withFluentSetters(true)
                            .withJavaTimeTypes(true)
                    )
                    .withDatabase(databaseConfig(tables))
                    .withTarget(
                        Target()
                            .withPackageName("persistence")
                            .withDirectory("src/main/jooq")
                    )
                    .withName("org.jooq.codegen.KotlinGenerator")
            )

        GenerationTool.generate(configuration)
    }

    fun generate(vararg tables: Table) {
        startContainers()
        migrate()
        codeGen(tables.asList())
    }
}

class KPostgreSQLContainer : PostgreSQLContainer<KPostgreSQLContainer>("postgres:12.5-alpine")
