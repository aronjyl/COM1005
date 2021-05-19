import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RunRamblersBB {
    
    public static void main(String[] arg){
        // diablo.pgm
        TerrainMap tm = new TerrainMap("tmc.pgm");

        int matrix_width = tm.getWidth();
        int matrix_depth = tm.getDepth();

        int goal_w = ThreadLocalRandom.current().nextInt(0, matrix_width);
        int goal_d = ThreadLocalRandom.current().nextInt(0, matrix_depth);

        int init_w = ThreadLocalRandom.current().nextInt(0, matrix_width);
        int init_d = ThreadLocalRandom.current().nextInt(0, matrix_depth);
        System.out.println(goal_w);
        System.out.println(goal_d);
        System.out.println(init_w);
        System.out.println(init_d);        

        /*
        1. init a number of start/end points on tmc map
        */ 
        Coords goal = new Coords(goal_w, goal_d);
        int tmap[][] = tm.getTmap();
        /*
        2. evaluating these points for branch_bound
        */
        RamblersSearch rambler_searcher = new RamblersSearch(tm, goal);
        SearchState initState = (SearchState) new RamblersState(new Coords(init_w, init_d), 
                                                                tmap[init_w][init_d], 0);

        float res_branchbound = rambler_searcher.runSearchE(initState, "breadthFirst");
        System.out.println(res_branchbound);
        
        /*
        3. evaluting A* search for 
            1) manhattan;
            2) euclidean;
            3) height diff;
            4) combination all
        */        
        float res_astar = rambler_searcher.runSearchE(initState, "AStar");
        System.out.println(res_astar);

        /*
        4. compare the efficiency of branch&bound and A*
        */

        /*
        5. report to a) describe B&B and A*; b) present results; 3) comparison results
        */
        
    }
}
