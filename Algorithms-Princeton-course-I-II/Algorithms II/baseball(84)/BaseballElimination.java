
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BaseballElimination {

    private Team [] teams;
    private HashMap<String, Integer> teamsMap;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        readFromFile(filename);
    }

    private void readFromFile(String filename) {
        In in = new In(filename);
        int numberOfTeams = -1;
        int teamIndex = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            if (numberOfTeams == -1) {
                numberOfTeams = Integer.parseInt(line);
                this.teams = new Team[numberOfTeams];
                this.teamsMap = new HashMap<>();
            }
            else {
                parseTeam(line, numberOfTeams, teamIndex);
                teamIndex++;
            }
        }
    }

    private void parseTeam(String line, int numberOfTeams, int teamIndex) {
        String [] teamData = line.trim().split("\\s+");
        String teamName = teamData[0];
        int wins = Integer.parseInt(teamData[1]);
        int losses = Integer.parseInt(teamData[2]);
        int remaining = Integer.parseInt(teamData[3]);
        int [] against = new int[numberOfTeams];
        for (int i = 4; i < teamData.length; i++) {
            against[i - 4] = Integer.parseInt(teamData[i]);
        }
        Team team = new Team();
        team.name = teamName;
        team.wins = wins;
        team.losses = losses;
        team.remaining = remaining;
        team.against = against;
        this.teams[teamIndex] = team;
        this.teamsMap.put(team.name, teamIndex);
    }

    private FlowNetwork createNetwork(Team team) {
        int n = teams.length - 1;
        int v = n + (n + 1) * (n + 1) + 2;
        FlowNetwork flowNetwork = new FlowNetwork(v);

        int s = v - 2;
        int t = v - 1;
        int maximumPossibleWins = team.wins + team.remaining;
        for (int i = 0; i < teams.length; i++) {
            String teamI = teams[i].name;
            if (teamI.equals(team.name)) {
                continue;
            }
            for (int j = i + 1; j < teams.length; j++) {
                String teamJ = teams[j].name;
                if (teamJ.equals(team.name)) {
                    continue;
                }
                int gameId = teams.length + (i + 1) * (j + 1) - 2;
                flowNetwork.addEdge(new FlowEdge(s, gameId, against(teamI, teamJ)));
                flowNetwork.addEdge(new FlowEdge(gameId, i, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(gameId, j, Double.POSITIVE_INFINITY));
            }
            flowNetwork.addEdge(new FlowEdge(i, t, maximumPossibleWins - wins(teamI)));
        }
        return flowNetwork;
    }

    // number of teams
    public int numberOfTeams() {
        return this.teams.length;
    }

    // all teams
    public Iterable<String> teams() {
        return this.teamsMap.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        validateTeam(team);

        return this.teams[this.teamsMap.get(team)].wins;
    }

    // number of losses for given team
    public int losses(String team) {
        validateTeam(team);

        return this.teams[this.teamsMap.get(team)].losses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validateTeam(team);

        return this.teams[this.teamsMap.get(team)].remaining;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateTeam(team1);
        validateTeam(team2);

        int teamIndex1 = this.teamsMap.get(team1);
        int teamIndex2 = this.teamsMap.get(team2);
        return this.teams[teamIndex1].against[teamIndex2];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validateTeam(team);

        return certificateOfElimination(team) != null;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);

        Team current = teams[teamsMap.get(team)];
        int maxWins = current.wins + current.remaining;
        for (Team t : teams) {
            if (maxWins < t.wins) {
                return Collections.singletonList(t.name);
            }
        }

        FlowNetwork flowNetwork = createNetwork(current);
        int n = teams.length - 1;
        int v = n + (n + 1) * (n + 1) + 2;
        int s = v - 2;
        int t = v - 1;
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, s, t);
        Set<String> teamsToEliminate = new HashSet<>();

        for (FlowEdge edge : flowNetwork.adj(s)) {
            if (edge.flow() != edge.capacity()) {
                for (FlowEdge edgeTarget : flowNetwork.adj(t)) {
                    if (fordFulkerson.inCut(edgeTarget.from())) {
                        teamsToEliminate.add(teams[edgeTarget.from()].name);
                    }
                }
                return teamsToEliminate;
            }
        }

        return null;
    }

    private class Team {
        String name;
        int wins;
        int losses;
        int remaining;
        int[] against;
    }

    private void validateTeam(String teamName) {
        if (teamName == null || !this.teamsMap.containsKey(teamName)) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        // test
        BaseballElimination b = new BaseballElimination("src//teams4.txt");
        System.out.println(b.against("Atlanta", "Philadelphia"));
    }

}
