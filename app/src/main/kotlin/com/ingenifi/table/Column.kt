package com.ingenifi.table

data class Column(val columnNumber: Int, val cells: List<Cell>) {
    private var maxRowNumber: Int = 0

    constructor(columnNumber: Int, cells: List<Cell>, maxRowNumber: Int) : this(columnNumber, cells) {
        this.maxRowNumber = maxRowNumber
    }

    fun row(rowNumber: Int): Cell = if (rowNumber in 1..maxRowNumber)
        cells.first { it.rowNumber == rowNumber }
    else
        throw IllegalAccessException("Row number $rowNumber is out of bounds.")
}