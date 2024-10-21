import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class LastLocation {
    private LinkedHashMap<Loc, Double> LastLocation_CPT;

    /*
    Initializing the last location evenly
     */
    public LastLocation(int row, int col){
        // key = locations | val = probability
        this.LastLocation_CPT = new LinkedHashMap<Loc, Double>();

        double init_prob =  1.0000000/(row*col);
        for(int i = 0; i< row; i++){
            for(int j = 0; j<row; j++){
                Loc loc = new Loc(i,j);
                LastLocation_CPT.put(loc, init_prob);
            }
        }


    }

    /*
    after the vals get computed lets update the lastLocations probability with the new caluclated ones
     */
    public void updateLastLocationCpt(LinkedHashMap<Loc, Double> newProbs){
        LastLocation_CPT = newProbs ;
    }


    public Double  get_prob(Loc loc){
        return LastLocation_CPT.getOrDefault(loc,0.0);
    }

    public void print_last_location_distribution(){
        Set<Loc> keys = LastLocation_CPT.keySet();
        System.out.println("Last location distribution:");
        for(Loc key: keys){
            System.out.println("Last location:" +key.toString() + ", prob: " + LastLocation_CPT.get(key));
        }
        System.out.println(" ");

    }

    public void print_formattedprobDistriution(){
        Set<Loc> keys = LastLocation_CPT.keySet();
        for(int i = 0; i<MB.row; i++){
            for(int j = 0; j<MB.col ; j++){
                Loc loc = new Loc(i,j);
                Double prob = LastLocation_CPT.get(loc);
                System.out.printf(" %.8f  ", prob);

            }
            System.out.println(" ");

        }
        System.out.println(" ");

    }




}
