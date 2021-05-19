import java.util.*;

public class RamblersSearch extends Search{
    private TerrainMap terr_map;
    private Coords goal;

    // init constrctor
    public RamblersSearch(TerrainMap tm, Coords coords){
        terr_map = tm;
        goal = coords;
    }

    public TerrainMap getMap(){
        return terr_map;
    }
    
    public Coords getGoal(){
        return goal;
    }
}
