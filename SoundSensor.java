import java.lang.reflect.Array;
import java.util.*;

public class SoundSensor {

    //Key: current location | val: {key: sensor location | val: probability}}
    private LinkedHashMap<Loc, HashMap<Loc, Double>> SoundMap;

    public SoundSensor(ArrayList<Loc> allLocations){
        this.SoundMap = new LinkedHashMap<>();

        for(Loc key: allLocations){
            LinkedHashMap<Loc, Double> reportlocs = calc_probs(key);
            SoundMap.put(key, reportlocs );

        }

    }

    private LinkedHashMap<Loc, Double> calc_probs(Loc currLoc){
        LinkedHashMap<Loc, Double> probs = new LinkedHashMap<>();

        Double correct_prob = 0.60;
        probs.put(currLoc, correct_prob);

        //calculating the adjacent ones
        ArrayList<Loc> oneManhattandlist = GetMoveOption.oneManhattan(currLoc);
        Double onManhattanAwayProb = 0.30;
        Double adjacentProb = onManhattanAwayProb /oneManhattandlist.size();
        for(Loc aloc: oneManhattandlist){
            probs.put(aloc, adjacentProb);
        }

        //calculatinf the two manhattan away
        ArrayList<Loc> twoManhattanList = GetMoveOption.twoManhattan(currLoc);
        Double twoManhattanawayProb = 0.1;
        Double twoAdjPRob = twoManhattanawayProb /twoManhattanList.size();
        for(Loc twoLoc: twoManhattanList){
            probs.put(twoLoc,twoAdjPRob);
        }
        return probs;
    }

    //returning the probbaility if given the current location, and what the sounds sensor thinkss the curr location is
    public Double getProb(Loc currLoc, Loc soundLoc ){
        return SoundMap.get(currLoc).get(soundLoc);
    }


    public void printSoundSens(){
        System.out.println("Sound distribution:");

        for (Map.Entry<Loc, HashMap<Loc, Double>> entry : SoundMap.entrySet()) {
            Loc currentLoc = entry.getKey();
            HashMap<Loc, Double> reportedLocs = entry.getValue();

            System.out.println("Current location: " + currentLoc);
            for (Map.Entry<Loc, Double> reportEntry : reportedLocs.entrySet()) {
                Loc reportedLoc = reportEntry.getKey();
                Double prob = reportEntry.getValue();
                System.out.printf("  Sound reported at: %s, prob: %.8f%n", reportedLoc, prob);
            }
        }

    }

}
