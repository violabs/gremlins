package io.violabs.gremlins.service

import io.violabs.gremlins.domain.BatchLine
import io.violabs.gremlins.repository.BatchLineRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class BatchLineService(private val batchLineRepository: BatchLineRepository) {
    suspend fun createBatchLine(batchLine: BatchLine): BatchLine = batchLineRepository.save(batchLine)

    fun createManyBatchLines(batchLines: List<BatchLine>): Flow<BatchLine> = batchLineRepository.saveAll(batchLines)

    suspend fun getBatchLineById(id: Long): BatchLine? = batchLineRepository.findById(id)

    fun listBatchLines(): Flow<BatchLine> = batchLineRepository.findAll()

    suspend fun count(): Long = batchLineRepository.count()
}