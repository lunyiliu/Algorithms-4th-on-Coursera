/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {
    private int TeamNum;
    private Map<String, Integer> teams2id;
    private Map<Integer, String> id2teams;
    private Map<Integer, String> vertexID2teams;
    private int[] wins;
    private int[] loses;
    private int[] remains;
    private int[][] games;
    private int total_game_nums;
    private int[] team_game_nums;
    private FlowNetwork FN;

    public BaseballElimination(String filename) {
        In file = new In(filename);
        TeamNum = file.readInt();
        teams2id = new HashMap<String, Integer>();
        id2teams = new HashMap<Integer, String>();
        total_game_nums = 0;
        wins = new int[TeamNum];
        team_game_nums = new int[TeamNum];
        loses = new int[TeamNum];
        remains = new int[TeamNum];
        games = new int[TeamNum][TeamNum];
        for (int i = 0; i < TeamNum; i++) {
            team_game_nums[i] = 0;
            String next_team = file.readString();
            teams2id.put(next_team, i);
            id2teams.put(i, next_team);
            wins[i] = file.readInt();
            loses[i] = file.readInt();
            remains[i] = file.readInt();
            for (int j = 0; j < TeamNum; j++) {
                games[i][j] = file.readInt();
                if (games[i][j] != 0) {
                    total_game_nums += games[i][j];
                    team_game_nums[i] += games[i][j];
                }
            }
        }
        total_game_nums /= 2;
    }              // create a baseball division from given filename in format specified below

    public int numberOfTeams() {
        return TeamNum;
    }                // number of teams

    public Iterable<String> teams() {
        return teams2id.keySet();
    }                           // all teams

    public int wins(String team) {
        if (!teams2id.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        return wins[teams2id.get(team)];
    }                    // number of wins for given team

    public int losses(String team) {
        if (!teams2id.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        return loses[teams2id.get(team)];
    }             // number of losses for given team

    public int remaining(String team) {
        if (!teams2id.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        return remains[teams2id.get(team)];
    }             // number of remaining games for given team

    public int against(String team1,
                       String team2) {
        if ((!teams2id.containsKey(team1)) || (!teams2id.containsKey(team2))) {
            throw new java.lang.IllegalArgumentException();
        }
        return games[teams2id.get(team1)][teams2id.get(team2)];
    }  // number of remaining games between team1 and team2

    private void construct_FN(String team) {
        if (!teams2id.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        int teamID = teams2id.get(team);
        int other_game_nums = TeamNum * TeamNum / 2 - 3 * TeamNum / 2 + 1;
        //StdOut.println(other_game_nums);
        FN = new FlowNetwork(other_game_nums + TeamNum + 1,
                             0);
        Map<Integer, Integer[]> ID2games = new HashMap<Integer, Integer[]>();
        Map<Integer, Integer> teams2ID = new HashMap<Integer, Integer>();
        vertexID2teams = new HashMap<Integer, String>();
        int ID = 1;
        for (int i = 0; i < TeamNum; i++) {
            for (int j = i + 1; j < TeamNum; j++) {
                if (!(i == teamID || j == teamID)) {
                    Integer[] two_teams = { i, j };
                    ID2games.put(ID++, two_teams);
                }
            }
        }
        //StdOut.println(ID);
        for (int i = 0; i < TeamNum; i++) {
            if (i != teamID) {
                vertexID2teams.put(ID, id2teams.get(i));
                teams2ID.put(i, ID++);
            }
        }
        //StdOut.println(ID);
        Integer[] two_teams;
        for (int i = 0; i < other_game_nums; i++) {
            two_teams = ID2games.get(i + 1);
            //constructing edges from source to games
             /*
            for (int j = 0; j < TeamNum; j++) {
                StdOut.println();
                for (int k = 0; k < TeamNum; k++) {
                    StdOut.print(games[j][k]);
                }
            }
            */
            FN.addEdge(new FlowEdge(0, i + 1, games[two_teams[0]][two_teams[1]]));
            // constructing edges from games to teams
            FN.addEdge(new FlowEdge(i + 1, teams2ID.get(two_teams[0]), Double.POSITIVE_INFINITY));
            FN.addEdge(new FlowEdge(i + 1, teams2ID.get(two_teams[1]), Double.POSITIVE_INFINITY));
        }

        for (int i = 0; i < TeamNum; i++) {
            if (i != teamID) {
                FN.addEdge(new FlowEdge(teams2ID.get(i), ID,
                                        wins[teamID] + remains[teamID] - wins[i]));
            }
        }
    }

    public boolean isEliminated(String team) {
        if (!teams2id.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        int teamID = teams2id.get(team);
        for (int i = 0; i < TeamNum; i++) {
            if ((wins[teamID] + remains[teamID] - wins[i]) < 0) {
                return true;
            }
        }
        //the first vertex is the source, following with game vetices and team vertices, and sink the last one
        construct_FN(team);
        FordFulkerson FF = new FordFulkerson(FN, 0, FN.V() - 1);
        // StdOut.println(team_game_nums[teamID]);
        if (FF.value() < total_game_nums - team_game_nums[teamID]) {
            return true;
        }
        else {
            return false;
        }

    }              // is given team eliminated?


    public Iterable<String> certificateOfElimination(
            String team) {
        if (!teams2id.containsKey(team)) {
            throw new java.lang.IllegalArgumentException();
        }
        Stack<String> R = new Stack<String>();
        int teamID = teams2id.get(team);
        for (int i = 0; i < TeamNum; i++) {
            if ((wins[teamID] + remains[teamID] - wins[i]) < 0) {
                R.push(id2teams.get(i));
                return R;
            }
        }
        //the first vertex is the source, following with game vetices and team vertices, and sink the last one
        construct_FN(team);
        FordFulkerson FF = new FordFulkerson(FN, 0, FN.V() - 1);
        // StdOut.println(team_game_nums[teamID]);
        if (FF.value() < total_game_nums - team_game_nums[teamID]) {
            for (int ID = TeamNum * TeamNum / 2 - 3 * TeamNum / 2 + 2; ID < FN.V() - 1; ID++) {
                if (FF.inCut(ID)) {
                    R.push(vertexID2teams.get(ID));
                }
            }
            return R;
        }
        else {
            return null;
        }
    }  // subset R of teams that eliminates given team; null if not eliminated

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
