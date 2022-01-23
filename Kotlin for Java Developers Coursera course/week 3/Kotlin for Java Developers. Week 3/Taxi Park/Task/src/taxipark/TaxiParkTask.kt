package taxipark

fun TaxiPark.getPassengersToTripsList(): List<Pair<Passenger, List<Trip>>> =
        this.trips.flatMap {
            trip -> trip.passengers
                .map { passenger -> passenger to trip } }
                .groupBy { it.first }
                .map { (passenger, listOfPassWithTrip) ->
                    passenger to listOfPassWithTrip.map { it.second } }

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.minus(this.trips.map { it.driver })

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.map { passenger ->
            passenger to this.trips.count { trip ->
                passenger in trip.passengers
            }
        }.filter { it.second >= minTrips }.map { it.first }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    this.allPassengers.map { passenger ->
        passenger to this.trips.count { trip ->
            passenger in trip.passengers && trip.driver.name == driver.name
        }
    }.filter { it.second > 1 }.map { it.first }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    this.getPassengersToTripsList().map { (passenger, trips) ->
        passenger to trips.partition { trip ->
            trip.discount != null
        }
    }.filter { it.second.first.size > it.second.second.size }.map { it.first }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? =
    this.trips.map { trip ->
        (trip.duration / 10) * 10..((trip.duration / 10) * 10 + 9) to trip }
            .groupBy { it.first }.maxByOrNull { it.value.size }?.key

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) return false
    val driverTripEntries = this.allDrivers.map { driver ->
        driver to this.trips.filter { trip -> trip.driver == driver } }
            .groupBy { it.first }
            .map { driverTripEntry ->
                driverTripEntry.key to driverTripEntry.value.flatMap { it.second } }
            .sortedByDescending { driverTripEntry ->
                driverTripEntry.second.sumOf { it.cost } }
    val totalIncome = this.trips.sumByDouble { it.cost }
    val bestDriversTotalIncome = driverTripEntries
            .take((driverTripEntries.size * 0.2).toInt())
            .sumOf { driverTripEntry -> driverTripEntry.second.sumOf { it.cost } }
    return bestDriversTotalIncome >= (totalIncome * 0.8)
}