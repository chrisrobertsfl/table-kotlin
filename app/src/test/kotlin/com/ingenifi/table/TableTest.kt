package com.ingenifi.table

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TableTest {

    private val table = Table(fieldSeparator = "*", input = listOf("Hello*World*Row", "Bye*Life*!", "***Long one"))

    @Test
    fun `Row and column fetching`() = forAll(
        row(1, 1, "Hello"),
        row(1, 2, "World"),
        row(3, 1, ""),
        row(3, 4, "Long one")
    )
    { rowNumber, columnNumber, contents -> table.row(rowNumber).column(columnNumber).contents shouldBe contents }

    @Test
    fun `Out of bounds rows`() = forAll(
        row(0),
        row(4),
        row(-100),
        row(100)
    )
    { rowNumber -> shouldThrow<IllegalAccessException> { table.row(rowNumber) } }

    @Test
    fun `Out of bounds columns for row 1`() = forAll(
        row(0),
        row(5),
        row(-100),
        row(100)
    )
    { columnNumber -> shouldThrow<IllegalAccessException> { table.row(1).column(columnNumber) } }

    @Test
    fun `Give me the rows`() {
        table.rows() shouldBe listOf(
            Row(1, listOf(Cell(1, 1, "Hello"), Cell(1, 2, "World"), Cell(1, 3, "Row"), Cell(1, 4, ""))),
            Row(2, listOf(Cell(2, 1, "Bye"), Cell(2, 2, "Life"), Cell(2, 3, "!"), Cell(2, 4, ""))),
            Row(3, listOf(Cell(3, 1, ""), Cell(3, 2, ""), Cell(3, 3, ""), Cell(3, 4, "Long one")))
        )
    }

    @Test
    fun `Give me the columns`() {
        table.columns() shouldBe listOf(
            Column(1, listOf(Cell(1, 1, "Hello"), Cell(2, 1, "Bye"), Cell(3, 1, ""))),
            Column(2, listOf(Cell(1, 2, "World"), Cell(2, 2, "Life"), Cell(3, 2, ""))),
            Column(3, listOf(Cell(1, 3, "Row"), Cell(2, 3, "!"), Cell(3, 3, ""))),
            Column(4, listOf(Cell(1, 4, ""), Cell(2, 4, ""), Cell(3, 4, "Long one")))
        )
    }

    @Test
    fun output() {
        for (i in 1..4)
            for (j in 1..3)
                println("column $i, row $j -> ${table.column(i).row(j)}")
    }
}


