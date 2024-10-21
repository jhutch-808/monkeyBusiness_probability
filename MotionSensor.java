import java.util.LinkedHashMap;

public class MotionSensor {
        private Loc sensorLocation;
        private static final double prob_if_present = 0.9;
        private static final double false_prob = 0.05;
        LinkedHashMap<Loc,Double> M1_CPT = new LinkedHashMap<>();

        public MotionSensor(Loc loc){
            this.sensorLocation = loc;
        }
        private int distance(Loc loc){
            return Math.abs(sensorLocation.getRow() - loc.getRow()) + Math.abs(sensorLocation.getCol() - loc.getCol());
        }

        public double getProbability(Loc loc){
            int disstance = distance(loc);
            if(sensorLocation.getRow() == loc.getRow() || sensorLocation.getCol() == loc.getCol()){
                double prob = prob_if_present - (0.1 *disstance);
                return Math.max(prob,0);
            }
            else{ // not in line of sight
                return false_prob;

            }
        }
        public void printSensorDistribution(int sensorNumber) {
            System.out.println("Motion sensor #" + sensorNumber + " at location " + sensorLocation + " distribution");

            // Loop through every location in the grid
            for (int r = 0; r < MB.row; r++) {
                for (int c = 0; c < MB.col; c++) {
                    Loc loc = new Loc(r, c);
                    double trueProb = getProbability(loc);
                    double falseProb = 1.0 - trueProb;

                    System.out.printf("     Current location: (%d, %d), true prob: %.8f, false prob: %.8f%n", r, c, trueProb, falseProb);
                }
            }
            System.out.println(" ");
        }

}


