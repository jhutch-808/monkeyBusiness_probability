import java.util.ArrayList;

public class GetMoveOption {

    public static ArrayList<Loc> getAllLocations(){
        ArrayList<Loc> allLocations = new ArrayList<>();
        for(int i = 0; i< MB.row; i++){
            for(int j = 0; j< MB.col; j++){
                Loc newLoc = new Loc(i,j);
                allLocations.add(newLoc);
            }
        }
        return  allLocations;
    }

    public static ArrayList<Loc> oneManhattan(Loc loc){
        ArrayList<Loc> allLocations = new ArrayList<>();
        //up
        if(loc.getRow()>0){
            allLocations.add(new Loc(loc.getRow()-1, loc.getCol() ) );
        }
        //down
        if(loc.getRow()<MB.row-1){
            allLocations.add(new Loc(loc.getRow()+1, loc.getCol() ) );
        }
        //left
        if(loc.getCol()>0){
            allLocations.add(new Loc(loc.getRow(), loc.getCol()-1));
        }
        //right
        if(loc.getCol()< MB.col-1){
            allLocations.add(new Loc(loc.getRow(), loc.getCol()+1));
        }
        return allLocations;

    }

    public static ArrayList<Loc> twoManhattan(Loc loc){
        ArrayList<Loc> allLocations = new ArrayList<>();
        //up
        int[][] pre_set_dirs = {
                {-2,0}, {2,0}, // 2 up , 2 down
                {0,-2}, {0,2}, //2 left, 2 right
                {1,1}, {-1,1 }, //1 down 1 right, 1 up 1 right
                {1,-1}, {-1,-1} //1 down 1 left, 1up 1 left
        };
        for( int[] dir : pre_set_dirs){
            int newR = loc.getRow()+ dir[0];
            int newC = loc.getCol() + dir[1];

            if(newR >=0 && newR < MB.row && newC>=0 && newC < MB.col){ //checking if its valid
                allLocations.add(new Loc(newR ,newC));
            }

        }
        return allLocations;


    }


}
