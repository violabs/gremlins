package io.violabs.gremlins.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("batch_line")
data class BatchLine(
    @Id var id: Long? = null,
    var amountProcessed: Long = 0
)