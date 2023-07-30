package com.ingenifi.table

data class Table(val fieldSeparator: String, val input: List<String>) {
    private var rows = HashMap<Int, Row>()
    private var columns = HashMap<Int, Column>()

    init {
        val cell = ArrayList<ArrayList<Cell>>()
        val max = MaxNumber(input.size, input.map { it.occurrences(fieldSeparator) }.max() + 1)

        for (row in 1..max.rows) {
            val field = input[row - 1].split(fieldSeparator)
            val rowCells = ArrayList<Cell>()
            for (column in 1..max.columns) {
                val contents = if (column <= field.size) field[column - 1] else ""
                rowCells.add(Cell(row, column, contents))
            }
            cell.add(rowCells)
        }
        (1..max.rows).associateWith { rows[it] = Row(it, cell[it - 1], max.columns) }
        (1..max.columns).associateWith { columns[it] = Column(it, cell.flatten().filter { cell -> cell.columnNumber == it }, max.rows) }
    }

    fun rows(): List<Row> = rows.values.sortedBy { it.rowNumber }
    fun columns(): List<Column> = columns.values.sortedBy { it.columnNumber }
    fun row(number: Int): Row = rows[number] ?: throw IllegalAccessException("Row number $number is out of bounds.")
    fun column(number: Int): Column = columns[number] ?: throw IllegalAccessException("Column number $number is out of bounds.")
    data class MaxNumber(val rows: Int, val columns: Int)
}