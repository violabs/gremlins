package io.violabs.gremlins.config

import io.violabs.gremlins.domain.BatchLine
import io.violabs.gremlins.repository.BatchLineRepository
import io.violabs.gremlins.service.BatchLineService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DataInitializer(val batchLineService: BatchLineService) {

    fun initialize() = runBlocking {
        if (batchLineService.count() > 1000) {
            println("Data already initialized.")
            return@runBlocking
        }

        println("Initializing data...")
        initializeBatchLines()
        println("Data initialization complete.")
    }

    private suspend fun initializeBatchLines() = coroutineScope {
        val numberOfRecords = 100_000
        val batchSize = 1_000  // Adjust based on your database and application performance

        (1..numberOfRecords).chunked(batchSize).forEach { batch ->
            launch {
                batchLineService
                    .createManyBatchLines(batch.map { BatchLine(amountProcessed = 0) })
                    .toList()
            }
        }
    }
}