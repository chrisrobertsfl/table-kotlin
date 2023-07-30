package com.ingenifi.table

fun String.occurrences(fieldSeparator: String) = this.split(fieldSeparator).size - 1