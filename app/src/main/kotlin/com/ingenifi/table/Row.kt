package com.ingenifi.table

data class Row(val rowNumber: Int, val cells: List<Cell>) {
    private var maxColumnNumber: Int = 0

    constructor(rowNumber: Int, cells: List<Cell>, maxColumnNumber: Int) : this(rowNumber, cells) {
        this.maxColumnNumber = maxColumnNumber
    }

    fun column(columnNumber: Int): Cell = if (columnNumber in 1..maxColumnNumber)
        cells.first { it.columnNumber == columnNumber }
    else
        throw IllegalAccessException("Column number $columnNumber is out of bounds.")
}