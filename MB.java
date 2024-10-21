/*
“I have neither given nor received unauthorized aid on this program.”

*/
import java.util.*;
import java.io.InputStream;
public class MB {
    static LastLocation lastlocationCPT;
    static Current_Location C;
    static MotionSensor motion1;
    static MotionSensor motion2;
    static SoundSensor soundSensor;
    static Integer row = 0;
    static Integer col = 0;
    static int debug; //1 = true | 0 is false

    public void main(String[] args) {
        Scanner term_scanner = new Scanner(System.in);

        System.out.println(" Would you like debuging? (1 for yes 0 for no)");
        debug = term_scanner.nextInt();

        System.out.println("Enter File: ");
        String file = term_scanner.next();


        InputStream is = MB.class.getResourceAsStream(file);
        if (is == null) {
            System.err.printf("Bad filename: %s%n", file);
            System.exit(1);
        }
        Scanner file_scan = new Scanner(is);
        String first_line = file_scan.nextLine();
        String[] dimen = first_line.split(" ");
        row = Integer.parseInt(dimen[0]);
        col = Integer.parseInt(dimen[1]);

        //initializing last location distribution
        lastlocationCPT = new LastLocation(row,col);
        //lastlocationCPT.print_last_location_distribution();

        //getting the current location distribtuion
        ArrayList<Loc> all_Locations = GetMoveOption.getAllLocations();
        C = new Current_Location(all_Locations);
        //C.printDistr();

        //setting distribution for M1 and M2
        motion1 = new MotionSensor(new Loc(0,0));
        motion2 = new MotionSensor(new Loc(row-1,col-1));

        //setting the sound sensor distribution
        soundSensor = new SoundSensor(all_Locations);

        if(debug == 1){print_debug();}

        //printing the Initial distribution in a formatted way
        System.out.println("Initial Distribution:");
        lastlocationCPT.print_formattedprobDistriution();
        int step = 0;

        //reading in the info for detecting
        while(file_scan.hasNextLine()){
            String line = file_scan.nextLine();
            String[] info = line.split(" ");
            //reading in the info
            boolean M1 = Integer.parseInt(info[0]) != 0; //true if = 0, flase if =  1
            boolean M2 = Integer.parseInt(info[1]) != 0;
            Loc S = new Loc(Integer.parseInt(info[2]), Integer.parseInt(info[3]));
            System.out.printf("Observation: Motion1: %b, Moation2: %b, Sound: %s \n", M1, M2, S.toString());

            System.out.println("Monkey's predicted current location at time step: " + step);
            LinkedHashMap<Loc, Double> newLastLocProb = new LinkedHashMap<>();
            Double allCurrprobs = 0.0; //will be used to normalize after
            for(Loc loc: all_Locations){ //for every current location
                if(debug == 1){System.out.println("  Calculating total prob for curr location: " + loc.toString());}
                Double sumOfCurr = probOfCurr(loc, M1, M2, S, all_Locations);
                allCurrprobs+=sumOfCurr;
                newLastLocProb.put(loc, sumOfCurr);
            }
            lastlocationCPT.updateLastLocationCpt(newLastLocProb);

            if(debug ==1) {
                System.out.println("Before normalization:");
                lastlocationCPT.print_formattedprobDistriution();
            }

            System.out.println("After normalization:");
            for(Loc loc: all_Locations){
                double normalizedProb = lastlocationCPT.get_prob(loc)/allCurrprobs;
                newLastLocProb.put(loc,normalizedProb);
            }
            lastlocationCPT.updateLastLocationCpt(newLastLocProb);
            lastlocationCPT.print_formattedprobDistriution();

            step++;


            }

    }

    public Double probOfCurr(Loc Curr, boolean M1, boolean M2, Loc Sound, ArrayList<Loc> allLocations){

        double sum = 0.0;
        //need a for loop for all past locations
        for(Loc loc: allLocations){
            double L = lastlocationCPT.get_prob(loc);
            double C_prob = (C.getProb(loc, Curr) == null)? 0: C.getProb(loc, Curr);
            double m1 = (M1)?motion1.getProbability(Curr): 1-motion1.getProbability(Curr);
            double m2 = (M2)?motion2.getProbability(Curr): 1- motion2.getProbability(Curr);
            double S = (soundSensor.getProb(Curr, Sound) ==null)? 0: soundSensor.getProb(Curr, Sound);


            if(debug == 1){System.out.printf("    Probs being multipled for last location: %s:" +
                    " %.8f %.8f %.8f %.8f %.8f \n",loc.toString(), L, C_prob, m1, m2, S);}

            sum+= (L * C_prob * m1 * m2 * S);
        }
        return sum;

    }

    public void print_debug(){
        lastlocationCPT.print_last_location_distribution();
        C.printDistr();
        motion1.printSensorDistribution(1);
        motion2.printSensorDistribution(2);
        soundSensor.printSoundSens();


    }

}





