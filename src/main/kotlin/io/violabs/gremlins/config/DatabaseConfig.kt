package io.violabs.gremlins.config

import jakarta.annotation.PostConstruct
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull

@Configuration
open class DatabaseConfig(
    @Autowired private val databaseClient: DatabaseClient,
    private val dataInitializer: DataInitializer
) {

    @PostConstruct
    fun buildDb() = runBlocking {
        println("Building database...")
        Sql
            .queries
            .stream()
            .map(CreateQuery::query)
            .map(databaseClient::sql)
            .map(DatabaseClient.GenericExecuteSpec::fetch)
            .forEach {
                runBlocking {
                    it.awaitOneOrNull()
                }
            }

        dataInitializer.initialize()
    }

    private object Sql {
        private val batchLine = CreateQuery(
            """
                CREATE TABLE IF NOT EXISTS batch_line (
                    id BIGSERIAL PRIMARY KEY,
                    amount_processed BIGINT NOT NULL DEFAULT 0
                );
            """
        )

        val queries = listOf(
            batchLine,
        )
    }

    @JvmInline
    private value class CreateQuery(val query: String)
}