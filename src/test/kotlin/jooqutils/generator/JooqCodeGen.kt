package jooqutils.generator

fun main(args: Array<String>) {
    val dbConfig = DbConfig(schema = "public", username = "schema_generator", password = "generator")
    JooqGenerator(dbConfig).generate(
        Table("user_principal"),
        Table("account")
    )
}
