package io.violabs.gremlins.repository

import io.violabs.gremlins.domain.BatchLine
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BatchLineRepository : CoroutineCrudRepository<BatchLine, Long>