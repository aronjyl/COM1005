import java.util.*;

public class RamblersState extends SearchState{
    private static final int[][] DIRECTIONS = {{1,0},{0,1},{-1,0},{0,-1}};
    private Coords curr_coords;
    private int eval_flag = 0;
    // constructor
    public RamblersState(Coords coords, int localcost_, int rc, int ef){
        curr_coords = coords;
        localCost = localcost_;
        estRemCost = rc;
        // 0 for eucledian, 1 for manhatten, 2 for height and 3 for all
        eval_flag = ef;     
    }
    
    public Coords getCoords(){
        return curr_coords;
    }

    public boolean goalPredicate(Search searcher){
        RamblersSearch ramSearcher = (RamblersSearch) searcher;
        // taget coord
        Coords tar = ramSearcher.getGoal();
        if (curr_coords.getx() == tar.getx() && curr_coords.gety() == tar.gety()) {
            return true;
        }else{
            return false;
        }
    }

    public boolean sameState(SearchState s2){
        RamblersState rams2 = (RamblersState) s2;
        if ((this.getLocalCost() == s2.getLocalCost() && 
            (this.curr_coords.getx() == rams2.getCoords().getx()) && 
            (this.curr_coords.gety() == rams2.getCoords().gety()))){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<SearchState> getSuccessors(Search searcher){
        RamblersSearch ramSearcher = (RamblersSearch) searcher;
        TerrainMap terr_m = ramSearcher.getMap();
        int[][] tMap = terr_m.getTmap();

        int original_lc = tMap[curr_coords.getx()][curr_coords.gety()];

        ArrayList<SearchState> successors = new ArrayList<SearchState>();
        // iterate 4 around directions and collect all valid steps
        for (int i=0; i<4; ++i) {
            int x = curr_coords.getx() + DIRECTIONS[i][0];
            int y = curr_coords.gety() + DIRECTIONS[i][1];

            // ignore invalid steps
            if (x < 0 || y < 0 || x >= terr_m.getDepth() || y >= terr_m.getWidth()) continue;

            Coords nextCoords = new Coords(y, x);
            int temp_est;
            // 0 for eucledian, 1 for manhatten, 2 for height and 3 for all
            switch (eval_flag) {
                case 0:
                    temp_est = terr_m.eucledianEst( curr_coords, ramSearcher.getGoal() );
                    break;
                case 1:
                    temp_est = terr_m.manhattenEst( curr_coords, ramSearcher.getGoal() );
                    break;
                case 2:
                    temp_est = terr_m.heightEst( curr_coords, ramSearcher.getGoal() );
                    break;
                default:
                    temp_est = terr_m.eucledianEst( curr_coords, ramSearcher.getGoal() ) + 
                                terr_m.manhattenEst( curr_coords, ramSearcher.getGoal() ) + 
                                terr_m.heightEst( curr_coords, ramSearcher.getGoal() );
                    break;
            }
            // the equation for rambler's cost 
            // int next_lc = tMap[x][y];
            // // 1 or 1 + | h(y, x) - h(y, x) |
            // next_lc = next_lc <= original_lc ? 1 : 1 + (original_lc - next_lc);
            
            RamblersState nextSuccessor = new RamblersState(nextCoords, tMap[x][y], 
                                                        temp_est, eval_flag);
            successors.add(nextSuccessor);
        }
        return successors;
    }

    public String toString(){
        return ("Ramblers State: " + "(" + curr_coords.gety() + "," + curr_coords.getx() + ")");
    }
}
