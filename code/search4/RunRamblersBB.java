import java.util.*;

public class RunRamblersBB {    
    public static void main(String[] arg){
        // diablo.pgm  && tmc.pgm
        TerrainMap tm = new TerrainMap("tmc.pgm");

        int matrix_width = tm.getWidth();
        int matrix_depth = tm.getDepth();
        // evaluate N times
        int EXP_NUMs = 100;
        Random rand = new Random();

        float bb_accum_res = 0.0f;
        ArrayList<Float> bb_res_list = new ArrayList<Float>();

        for (int i=0; i<EXP_NUMs; ++i){
            int goal_d = rand.nextInt(matrix_depth); //ThreadLocalRandom.current().nextInt(0, matrix_depth);
            int goal_w = rand.nextInt(matrix_width); //ThreadLocalRandom.current().nextInt(0, matrix_width);
    
            int init_d = rand.nextInt(matrix_depth); //ThreadLocalRandom.current().nextInt(0, matrix_depth);
            int init_w = rand.nextInt(matrix_width); //ThreadLocalRandom.current().nextInt(0, matrix_width);
    
            // System.out.println("start x: " + init_d);
            // System.out.println("start y: " + init_w);
    
            // System.out.println("end x: " + goal_d);
            // System.out.println("end y: " + goal_w);
    
            /*
            1. init a number of start/end points on tmc map
            */ 
            Coords goal = new Coords(goal_w, goal_d);
            int tmap[][] = tm.getTmap();
            /*
            2. evaluating these points for branch_bound
            */
            RamblersSearch rambler_searcher = new RamblersSearch(tm, goal);
            SearchState initState = (SearchState) new RamblersState(new Coords(init_d, init_w), 
                                                                    tmap[init_d][init_w], 0, 0);  // 0 for eucledian 
    
            float res_branchbound = rambler_searcher.runSearchE(initState, "branchAndBound");
            bb_res_list.add(res_branchbound);

            bb_accum_res += res_branchbound;
            // System.out.println("branchAndBound : " + res_branchbound);
        }
        System.out.println("\nB&B's avg for " + EXP_NUMs + " times: " + bb_accum_res/EXP_NUMs + 
                    " Standard Deviation: " + calculateSD(bb_res_list));
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
