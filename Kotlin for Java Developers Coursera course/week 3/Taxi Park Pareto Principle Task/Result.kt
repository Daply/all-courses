// Complete the implementation of the function 'checkParetoPrinciple'.
// This function should check whether no more than 20% of the drivers contribute 80% of the income. 
// It should return true if the top 20% drivers (meaning the top 20% best performers) 
// represent 80% or more of all trips total income, or false if not. The drivers that have no trips 
// should be considered as contributing zero income. If the taxi park contains no trips, the result should be `false`.
// For example, if there're 39 drivers in the taxi park, 
// we need to check that no more than 20% of the most successful ones, 
// which is seven drivers (39 * 0.2 = 7.8), contribute at least 80% of the total income. 
// Note that eight drivers out of 39 is 20.51% which is more than 20%, so we check the income of seven the most successful drivers.
// To find the total income sum up all the trip costs. Note that the discount is already applied while calculating the cost.

fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false

    val totalIncome = trips.sumByDouble(Trip::cost)
    val sortedDriversIncome: List<Double> = trips
            .groupBy(Trip::driver)
            .map { (_, tripsByDriver) -> tripsByDriver.sumByDouble(Trip::cost) }
            .sortedDescending()

    val numberOfTopDrivers = (0.2 * allDrivers.size).toInt()
    val incomeByTopDrivers = sortedDriversIncome
            .take(numberOfTopDrivers)
            .sum()

    return incomeByTopDrivers >= 0.8 * totalIncome
}

fun main(args: Array<String>) {
    taxiPark(1..5, 1..4,
            trip(1, 1, 20, 20.0),
            trip(1, 2, 20, 20.0),
            trip(1, 3, 20, 20.0),
            trip(1, 4, 20, 20.0),
            trip(2, 1, 20, 19.0))
            .checkParetoPrinciple() eq true

    taxiPark(1..5, 1..4,
            trip(1, 1, 20, 20.0),
            trip(1, 2, 20, 20.0),
            trip(1, 3, 20, 20.0),
            trip(1, 4, 20, 20.0),
            trip(2, 1, 20, 21.0))
            .checkParetoPrinciple() eq false
}
