package week2;

import java.util.*;
import java.util.stream.Collectors;

public class Flights {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Flight> flights = new ArrayList<>();

        int numberOfFlights = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numberOfFlights; i++) {
            String[] line = sc.nextLine().trim().split(" ");
            String source = line[0];
            String dest = line[1];
            int price = Integer.parseInt(line[2].trim());
            flights.add(new Flight(source, dest, price));
        }

        int limit = Integer.parseInt(sc.nextLine());

        String[] sourceDestinationPair = sc.nextLine().trim().split(" ");
        String source = sourceDestinationPair[0];
        String destination = sourceDestinationPair[1];

        List<Route> connections = findConnections(flights, source, destination, limit);

        if (connections.isEmpty()) {
            System.out.println("<no solution>");
        }
        else {
            for (Route connection : connections) {
                System.out.println(String.join(" ", connection.getAirportsString()) + " " + connection.price);
            }
        }
    }

    private static int getRoadPrice(
            List<Flight> flights,
            String start,
            String end
    ) {
        for (Flight flight: flights) {
            if (flight.departs.equals(start) && flight.arrives.equals(end)) {
                return flight.price;
            }
        }
        return 0;
    }

    private static List<Route> findConnections(
            List<Flight> flights,
            String start,
            String end,
            int limit
    ) {
        // create graph
        Map<String, Node> nodesMap = new HashMap<>();
        for (Flight flight: flights) {
            String airportFrom = flight.departs;
            String airportTo = flight.arrives;
            Node from = null;
            Node to = null;
            if (nodesMap.containsKey(airportFrom)) {
                from = nodesMap.get(airportFrom);
            }
            else {
                from = new Node(airportFrom);
                nodesMap.put(airportFrom, from);
            }
            if (nodesMap.containsKey(airportTo)) {
                to = nodesMap.get(airportTo);
            }
            else {
                to = new Node(airportTo);
                nodesMap.put(airportTo, to);
            }
            from.connectedNodes.add(to);
        }

        List<Route> allRoutes = findRoutes(nodesMap, flights, start, end);
        List<Route> result = new ArrayList<>();
        for (int i = 0; i < limit && i < allRoutes.size(); i++) {
            result.add(allRoutes.get(i));
        }

        return result;
    }

    private static List<Route> findRoutes(
            Map<String, Node> nodesMap,
            List<Flight> flights,
            String start,
            String end
    ) {

        Node startNode = nodesMap.get(start);
        PriorityQueue<Route> queue = new PriorityQueue<>();
        List<Route> result = new ArrayList<>();
        Route startRoute = new Route();
        startRoute.airports.add(startNode.airport);
        startRoute.price = 0;
        queue.add(startRoute);
        Map<String, Integer> visited = new HashMap<>();
        while (!queue.isEmpty()) {
            Route currentRoute = queue.poll();

            if (currentRoute.getLastAirport().equals(end)) {
                result.add(currentRoute);
            }
            else {
                String lastAirport = currentRoute.getLastAirport();
                for (Node neighbor: nodesMap.get(lastAirport).connectedNodes) {
                    int currentPrice = currentRoute.price +
                            getRoadPrice(flights, lastAirport, neighbor.airport);
                    if (!currentRoute.airports.contains(neighbor.airport)) {
                        Route newRoute = new Route();
                        newRoute.airports.addAll(currentRoute.airports);
                        newRoute.airports.add(neighbor.airport);
                        newRoute.price = currentPrice;
                        queue.add(newRoute);
                    }
                    if (!visited.containsKey(neighbor.airport) ||
                            visited.get(neighbor.airport) > currentPrice) {
                        visited.put(neighbor.airport, currentPrice);
                    }
                }
            }

        }

        return result;
    }

    static class Route implements Comparable<Route> {
        public List<String> airports;
        public int price;

        public Route() {
            this.airports = new ArrayList<>();
        }

        public String getLastAirport() {
            return this.airports.get(this.airports.size() - 1);
        }

        public String getAirportsString() {
            StringBuilder sb = new StringBuilder();
            for (String airport: this.airports) {
                sb.append(airport).append(" ");
            }
            return sb.toString().trim();
        }

        @Override
        public int compareTo(Route o) {
            if (this.price == o.price) {
                if (this.airports.size() == o.airports.size()) {
                    return this.getAirportsString().compareTo(o.getAirportsString());
                }
                return this.airports.size() - o.airports.size();
            }
            return this.price - o.price;
        }

        @Override
        public String toString() {
            return "Route{" +
                    "airports=" + airports +
                    ", price=" + price +
                    '}';
        }
    }

    static class Flight {
        final String departs;
        final String arrives;
        final int price;

        Flight(String departs, String arrives, int price) {
            this.departs = departs;
            this.arrives = arrives;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.join(" ", new String[]{this.departs, this.arrives, String.valueOf(this.price)});
        }

    }

    static class Node {
        List<Node> connectedNodes;
        String airport;

        Node(String airport) {
            this.airport = airport;
            this.connectedNodes = new ArrayList<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(connectedNodes, node.connectedNodes) &&
                    Objects.equals(airport, node.airport);
        }

        @Override
        public int hashCode() {
            return airport.hashCode();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "connectedNodes=" + connectedNodes +
                    ", airport='" + airport + '\'' +
                    '}';
        }
    }

}

