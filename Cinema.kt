package cinema

import java.lang.annotation.IncompleteAnnotationException

const val price1 = 10
const val price2 = 8
fun statistics(totalSeat: Int, ticketSold: Int, income: Int, rows: Int, seats: Int) {
    val totalPercentage : Double = (ticketSold * 100.0) / totalSeat
    val formatTotalPercentage = "%.2f".format(totalPercentage)
    var totalIncome = 0
    if (totalSeat < 60) {
        totalIncome = totalSeat * price2
    }
    if (totalSeat > 60) {
        val frontRows = (rows / 2) * seats
        totalIncome =  (frontRows * price1) + ((totalSeat - frontRows) * price2)
    }
    println("Number of purchased tickets: $ticketSold")
    println("Percentage: $formatTotalPercentage%")
    println("Current income: $$income")
    println("Total income: $$totalIncome")

}
fun menu() {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

typealias CinemaMatrix = List<List<String>>

fun buyATicket (totalSeat: Int, rows: Int, cinema: List<List<String>>): Pair<Int, CinemaMatrix> {
    try {
        println("Enter a row number:")
        val desiredRow = readln().toInt()
        println("Enter a seat number in that row:")
        val desiredSeat = readln().toInt()
        if (cinema[desiredRow - 1][desiredSeat - 1] == "B"){
            println("That ticket has already been purchased!")
            return buyATicket(totalSeat, rows, cinema)
        }
        var price = 0
        if (totalSeat < 60) {
            price = price1
        }
        if (totalSeat > 60) {
            if (desiredRow > rows / 2) {
                price = price2
            }
            if (desiredRow <= rows / 2) {
                price = price1
            }
        }
        println("Ticket price: $$price")
        val cinemamapped = cinema.mapIndexed{ i, cinemaRows ->
            if (i == desiredRow - 1){
                cinemaRows.mapIndexed { j, cinemaColumns ->
                    if (j == desiredSeat - 1){
                        "B"
                    } else {
                        cinemaColumns
                    }
                }
            } else{
                cinemaRows
            }
        }
        return price to cinemamapped
    } catch (e: IndexOutOfBoundsException) {
        println("Wrong input!")
        return buyATicket(totalSeat, rows, cinema)
    }
}
fun printC(list: List<List<String>>) {
    print("Cinema:\n ")
    for (s in 1..list[0].size) {
        print(" $s")
    }
    println()
    for (i in 1..list.size) {
        print("$i")
        for (j in 0 until list[0].size) {
            print(" ${list[i - 1][j]}")
        }
        println()
    }
}
fun main() {


    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    var cinema = List(rows){ List(seats){"S"} }
    val totalSeat = rows * seats
    var ticketSold = 0
    var price = 0
    var income = 0
    do{
        menu()
        val n = readln().toInt()
        println()
        when(n) {
            1 -> printC(cinema)
            2 -> {
                val result = buyATicket(totalSeat, rows, cinema)
                cinema = result.second
                price = result.first
                ticketSold++
                income += price
            }
            3 -> statistics(totalSeat,ticketSold, income, rows, seats)
            0 -> break
        }
    } while (n != 0)

}