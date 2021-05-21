import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RunRamblersAstart {
    public static void main(String[] arg){
        // diablo.pgm  && tmc.pgm
        TerrainMap tm = new TerrainMap("diablo.pgm");

        int matrix_depth = tm.getDepth();
        int matrix_width = tm.getWidth();

        // evaluate N times
        int EXP_NUMs = 1000;

        float bb_accum_res = 0.0f;
        float as_accum_res = 0.0f;
        ArrayList<Float> bb_res_list = new ArrayList<Float>();
        ArrayList<Float> as_res_list = new ArrayList<Float>();
        for (int i=0; i<EXP_NUMs; ++i){
            int goal_d = ThreadLocalRandom.current().nextInt(0, matrix_depth);
            int goal_w = ThreadLocalRandom.current().nextInt(0, matrix_width);
    
            int init_d = ThreadLocalRandom.current().nextInt(0, matrix_depth);
            int init_w = ThreadLocalRandom.current().nextInt(0, matrix_width);
    
            // System.out.println("start x: " + init_d);
            // System.out.println("start y: " + init_w);
    
            // System.out.println("end x: " + goal_d);
            // System.out.println("end y: " + goal_w);
    
            /*
            3. evaluting A* search for 
                1) euclidean;
                2) manhattan;
                3) height diff;
                4) combination all
            */        
            Coords goal = new Coords(goal_d, goal_w);
            int tmap[][] = tm.getTmap();
    
            RamblersSearch rambler_searcher = new RamblersSearch(tm, goal);
            // iterate evaluate each distance (euclidean, manhattan, height diff, and combined all)
            String[] indictors = {"Euclidean", "Manhattan", "Height", "All"};
            float astar_diff_cost = 0.0f;
            float bb_diff_cost = 0.0f;
            for (int eval_idx=0; eval_idx<4; ++eval_idx){
                SearchState initState = (SearchState) new RamblersState(new Coords(init_d, init_w), 
                                                                        tmap[init_d][init_w], 0, eval_idx);  
    
                //System.out.println("The cost for " + indictors[eval_idx] + " distance");
                float res_branchbound = rambler_searcher.runSearchE(initState, "branchAndBound");
                bb_res_list.add(res_branchbound);

                bb_diff_cost += res_branchbound;
                // bb_accum_res += res_branchbound;
                //System.out.println("branchAndBound : " + res_branchbound);
    
                float res_astar = rambler_searcher.runSearchE(initState, "AStar");
                as_res_list.add(res_astar);
                astar_diff_cost += res_astar;
                //System.out.println("         AStar : "+ res_astar);
            }
            as_accum_res += astar_diff_cost/4;
            bb_accum_res += bb_diff_cost/4;
        }
        /*
        4. compare the efficiency of branch&bound and A*
        */
        System.out.println("B&B's avg for " + EXP_NUMs + " times: " + bb_accum_res/EXP_NUMs+ 
                            " Standard Deviation: " + calculateSD(bb_res_list));

        System.out.println("A star's avg for " + EXP_NUMs + " times: " + as_accum_res/EXP_NUMs+ 
                    " Standard Deviation: " + calculateSD(as_res_list));
        /*
        5. report to a) describe B&B and A*; b) present results; 3) comparison results
        */
    }
    public static double calculateSD(ArrayList<Float> numList){
        float sum = 0.0f, standardDeviation = 0.0f;
        int length = numList.size();
        for(float num : numList) {
            sum += num;
        }
        float mean = sum/length;
        for(float num: numList) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation/length);
    }    
}
