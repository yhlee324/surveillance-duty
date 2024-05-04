import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Organizer {

    public static boolean isSoldierInShift(String soldierName, int shiftIndex, String position, ArrayList<Partner> allShifts, int time) {
        switch (time) {
            case 2022:
                return allShifts.get(shiftIndex - 1).getPartner(position).equals(soldierName)
                        || allShifts.get(shiftIndex - 2).getPartner(position).equals(soldierName)
                        || allShifts.get(shiftIndex + 2).getPartner(position).equals(soldierName);
            case 2200:
                return allShifts.get(shiftIndex - 1).getPartner(position).equals(soldierName)
                        || allShifts.get(shiftIndex - 2).getPartner(position).equals(soldierName)
                        || allShifts.get(shiftIndex + 2).getPartner(position).equals(soldierName)
                        || allShifts.get(shiftIndex + 1).getPartner(position).equals(soldierName);
            case 2000:
                return allShifts.get(shiftIndex - 1).getPartner(position).equals(soldierName)
                        || allShifts.get(shiftIndex + 2).getPartner(position).equals(soldierName);
            default:
                return allShifts.get(shiftIndex - 1).getPartner(position).equals(soldierName)
                        || allShifts.get(shiftIndex - 2).getPartner(position).equals(soldierName);
        }
    }

    //Helper function to check if a soldier subbing in is found in the proximity of the shift
    public static boolean isSoldierNotInConflict(Soldier s, int shiftIndex, String position, ArrayList<Partner> allShifts, int time) {
        switch (time) {
            case 2022:
                return (!s.getName().equals(allShifts.get(shiftIndex - 1).getPartner(position)) &&
                !s.getName().equals(allShifts.get(shiftIndex - 2).getPartner(position))
                && !s.getName().equals(allShifts.get(shiftIndex + 2).getPartner(position)));
            case 2200:
                return (!s.getName().equals(allShifts.get(shiftIndex - 1).getPartner(position)) &&
                !s.getName().equals(allShifts.get(shiftIndex - 2).getPartner(position))
                && !s.getName().equals(allShifts.get(shiftIndex + 2).getPartner(position))
                && !s.getName().equals(allShifts.get(shiftIndex + 1).getPartner(position)));
            case 2000:
                return (!s.getName().equals(allShifts.get(shiftIndex - 1).getPartner(position)) &&
                !s.getName().equals(allShifts.get(shiftIndex + 2).getPartner(position)));
            default:
                return (!s.getName().equals(allShifts.get(shiftIndex - 1).getPartner(position)) &&
                !s.getName().equals(allShifts.get(shiftIndex - 2).getPartner(position)));
        }
    }

    private static void setSoldierForShift(List<Soldier> soldiers, int shiftIndex, String position, ArrayList<Partner> allShifts, int time) {
        String soldierName = soldiers.get(0).getName();
        if (isSoldierInShift(soldierName, shiftIndex, position, allShifts, time)) {
            for (Soldier s : soldiers) {
                if (isSoldierNotInConflict(s, shiftIndex, position, allShifts, time)) {
                    if (position.equals("P1")) {
                        allShifts.get(shiftIndex).setP1(s.getName());
                    } else {
                        allShifts.get(shiftIndex).setP2(s.getName());
                    }
                    s.addDayCount();
                    break;
                }
            }
        } else {
            if (position.equals("P1")) {
                allShifts.get(shiftIndex).setP1(soldierName);
            } else {
                allShifts.get(shiftIndex).setP2(soldierName);
            }
            soldiers.get(0).addDayCount();

        }
    }

    public static ArrayList<Partner> Organize(ArrayList<Soldier> soldiers){
        class NightComparator implements Comparator<Soldier>{
            public int compare(Soldier s1, Soldier s2) {
                return s1.getNightCount() - s2.getNightCount();
            }
        }
        class TotalComparator implements Comparator<Soldier>{
            public int compare(Soldier s1, Soldier s2) {return s1.getTotalCount() - s2.getTotalCount(); }
        }

        //Total Comparator
        Comparator<Soldier> totalCompare = new TotalComparator();

        //Night PQ
        Comparator<Soldier> nightCompare = new NightComparator(); //object for a night comparator
        PriorityQueue<Soldier> seniorNightPriority = new PriorityQueue<Soldier>(nightCompare);
        PriorityQueue<Soldier> juniorNightPriority = new PriorityQueue<Soldier>(nightCompare);

        for (Soldier s : soldiers){
            if (s.getRank() == 1){
                seniorNightPriority.add(s);
            }
            else {
                juniorNightPriority.add(s);
            }
        }

        ArrayList<Partner> nights = new ArrayList<Partner>();

        /**
         * Night shifts
         */
        for (int i =0; i < 8; i++){
            //Shifts is an arraylist to store names in shifts. Will move this later into nights

            //Temporary arraylist to store those that went already
            ArrayList<Soldier> temp1 = new ArrayList<Soldier>();
            ArrayList<Soldier> temp2 = new ArrayList<Soldier>();

            for (int s = 0; s < 4; s++) {
                //Initialize partner that'll take the two soldiers
                Partner p = new Partner();
                //calculate currShift with i and s
                int currShift = (i*12) + (s+4);

                //Check if the first person has a min/max conflict
                if ( seniorNightPriority.peek().getMin() > currShift
                        || seniorNightPriority.peek().getMax() < currShift){
                    temp1.add(seniorNightPriority.remove());
                }
                //setting p1 as the first
                p.setP1(seniorNightPriority.peek().getName());
                temp1.add(seniorNightPriority.remove());

                //Check if the second person has a min/max conflict
                if ( juniorNightPriority.peek().getMin() > currShift
                        || juniorNightPriority.peek().getMax() < currShift){
                    temp2.add(juniorNightPriority.remove());
                }
                //setting p2 as the second
                p.setP2(juniorNightPriority.peek().getName());
                temp2.add(juniorNightPriority.remove());
                //add it to the shift
                nights.add(p);
            }
            //Add night count to 1s then put them back to pq
            for (Soldier s : temp1){
                s.addNightCount();
                seniorNightPriority.add(s);
            }
            for (Soldier s : temp2){
                s.addNightCount();
                juniorNightPriority.add(s);
            }
        }

        //Arraylist for senior & junior
        ArrayList<Soldier> soldierDay1 = new ArrayList<>(); 
        ArrayList<Soldier> soldierDay2 = new ArrayList<>();

        //Move all the soldiers into their corresponding arrays
        soldierDay1.addAll(seniorNightPriority);
        soldierDay2.addAll(juniorNightPriority);

        //Sort the arrays using day shift
        soldierDay1.sort(totalCompare);
        soldierDay2.sort(totalCompare);

        //Make an array to store all the shifts
        ArrayList<Partner> allShifts = new ArrayList<Partner>();

        //Start adding all the night shifts into the array first
        int count = 0;
        for (int i = 0; i < 84; i++){
            //Fill the "night" shifts in
            if (i % 12 +1 == 4 || i % 12 +1 == 5 || i % 12 +1 == 6 || i % 12 +1 == 7) {
                allShifts.add(i, nights.get(count));
                count++;
            }
            else {
                allShifts.add(new Partner());
            }
        }

        /**
         * Adding day shifts
         */
        //Loop through all the shifts
        for (int j = 0; j < 84; j++) {
            //Skip all the night shifts
            if (j % 12 +1 != 4 && j % 12 +1 != 5 && j % 12 +1 != 6 && j % 12 +1 != 7) {

                //All shifts that's after the second but not 20-22 or 22-00
                if (j > 1 && j % 12 +1 != 2 && j % 12 +1 != 3) {
                    setSoldierForShift(soldierDay1, j, "P1", allShifts, 00);
                    setSoldierForShift(soldierDay2, j, "P2", allShifts, 00);
                    //Sort the arrays according to dayCompare
                    soldierDay1.sort(totalCompare);
                    soldierDay2.sort(totalCompare);
                }

                //Now check if the person has a shift 2 shifts after, if this is a 20-22 shift
                else if (j % 12 +1 == 2 && j > 1) {
                    setSoldierForShift(soldierDay1, j, "P1", allShifts, 2022);
                    setSoldierForShift(soldierDay2, j, "P2", allShifts, 2022);
                    //Sort the arrays according to dayCompare
                    soldierDay1.sort(totalCompare);
                    soldierDay2.sort(totalCompare);

                }

                //Check if this is a 22-00 shift (Remainder of 3 when divided by 12)
                else if (j % 12 +1 == 3) {
                    setSoldierForShift(soldierDay1, j, "P1", allShifts, 2200);
                    setSoldierForShift(soldierDay2, j, "P2", allShifts, 2200);
                    soldierDay1.sort(totalCompare);
                    soldierDay2.sort(totalCompare);
                }

                //This is a 2022 shift (but second shift of the week)
                else if (j == 1) {
                    setSoldierForShift(soldierDay1, j, "P1", allShifts, 2000);
                    setSoldierForShift(soldierDay2, j, "P2", allShifts, 2000);

                    //Sort the arrays according to dayCompare
                    soldierDay1.sort(totalCompare);
                    soldierDay2.sort(totalCompare);
                }

                //Case for the first shift
                else {
                    //Set the soldiers as the first on the list
                    allShifts.get(j).setP1(soldierDay1.get(0).getName());
                    soldierDay1.get(0).addDayCount();

                    allShifts.get(j).setP2(soldierDay2.get(0).getName());
                    soldierDay2.get(0).addDayCount();
                    //Sort
                    soldierDay1.sort(totalCompare);
                    soldierDay2.sort(totalCompare);
                }

            }
        }
        return allShifts;
    }
    public static void main(String[] args){ 
        Secretary secretary = new Secretary();
        secretary.readFile("soldiers.txt");
        ArrayList<Soldier> soldiers = secretary.soldiers;
        ArrayList<Partner> shifts = Organize(soldiers);
        secretary.produceShifts(shifts, "shifts.txt");
        secretary.produceFairnessReport("fairness.txt");
        secretary.writeSoldiersToFile("20230315.txt");
    }
}
