import java.lang.annotation.Retention;
import java.util.*;

public class Current_Location {

    // Key: last location | val = {Key = curr location |val = probability}
    static LinkedHashMap<Loc, LinkedHashMap<Loc, Double>> Cur_Loc_Map;

    public Current_Location(ArrayList<Loc> allLastLoc) {
        Cur_Loc_Map = new LinkedHashMap<>();
        for (Loc loc : allLastLoc) {
            ArrayList<Loc> possible_moves = GetMoveOption.oneManhattan(loc);
            LinkedHashMap<Loc, Double> currTOprob = new LinkedHashMap<>();
            for (Loc move : possible_moves) {
                Double probability = 1.00000000 / possible_moves.size();
                currTOprob.put(move, probability);

            }
            Cur_Loc_Map.put(loc, currTOprob);
        }

    }

    /*
    returns the probability when we know that last location and possible curr location
     */
    public Double getProb(Loc lastLocation, Loc currLocation) {
        return Cur_Loc_Map.get(lastLocation).get(currLocation);

    }



    // Get current location probabilities for a specific last location
    public LinkedHashMap<Loc, Double> getProbabilitiesForLastLocation(Loc lastLoc) {
        return Cur_Loc_Map.get(lastLoc);
    }



    public void printDistr() {
        System.out.print("Current location distribution:\n");
        Set<Loc> Last_locations = Cur_Loc_Map.keySet();
        for (Loc last_key : Last_locations) {
            System.out.println("Last location:" + last_key.toString());
            Set<Loc> keys = Cur_Loc_Map.get(last_key).keySet();
            for (Loc key : keys) {
                System.out.println("    Current Location: " + key.toString() + ", prob: " + Cur_Loc_Map.get(last_key).get(key));
            }
        }
        System.out.println(" ");

    }

}
