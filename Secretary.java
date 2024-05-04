import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
    
public class Secretary {
    public ArrayList<Soldier> soldiers;

    public Secretary() {
        this.soldiers = new ArrayList<Soldier>();
    }

    public Secretary(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public void update(List<Soldier> senior, List<Soldier> junior) {
        this.soldiers.addAll(junior);
        this.soldiers.addAll(senior);
    }
    
    public void readFile(String filename) {
        soldiers = new ArrayList<Soldier>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split("\\|");
                String name = parts[0];
                int dayCount = Integer.parseInt(parts[1]);
                int nightCount = Integer.parseInt(parts[2]);
                int min = Integer.parseInt(parts[3]);
                int max = Integer.parseInt(parts[4]);
                int rank = Integer.parseInt(parts[5]);
                Soldier soldier = new Soldier(name, dayCount, nightCount, min, max, rank);
                this.soldiers.add(soldier);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Method to write a file containing the shifts for the week
    public void produceShifts(List<Partner> shifts, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            int time = 18;
            int count = 0;
            for (Partner shift : shifts) {
                String line1 = String.format("%02d:00 - %02d:00", time, (time + 2));                
                writer.write(line1);
                writer.newLine();
                String line2 = shift.getPartner("P1") + "|" + shift.getPartner("P2");
                writer.write(line2);
                writer.newLine();
                if (time == 22) {
                    time = 00;
                } else {
                    time += 2;
                }
                if (count == 5) {
                    writer.write("-------------");
                    writer.newLine();
                    count = 0;
                } else {
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void produceFairnessReport(String filename) {
        int avgD = 56*2 / (soldiers.size());
        int avgN = 28*2 / (soldiers.size());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("근무공정성 보고서\n");
            writer.write("평균 주간근무 수: " + avgD + "\n");
            writer.write("평균 야간근무 수: " + avgN + "\n");
            writer.write("이름" + "  |" + "주간근무" + " |" + "야간근무" + "|" + "총근무" + "|" + "주간공정" + "|" +"야간공정" + "\n");
            for (Soldier soldier : soldiers) {
                int dayCount = soldier.getDayCount()-avgD;
                int nightCount = soldier.getNightCount()-avgN;
                writer.write(String.format("%-1s | %-4d | %-4d | %-3d | %-3d | %-5d%n", 
                soldier.getName(), soldier.getDayCount(), soldier.getNightCount(), soldier.getTotalCount(), dayCount, nightCount));
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void writeSoldiersToFile(String filename) {
        int avgD = 56*2 / (this.soldiers.size());
        int avgN = 28*2 / (this.soldiers.size());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Soldier soldier : this.soldiers) {
                int dayCount = soldier.getDayCount()-avgD;
                int nightCount = soldier.getNightCount()-avgN;
                String line = soldier.getName() + "|" + dayCount + "|" + nightCount + "|" + soldier.getMin() + "|" + soldier.getMax() + "|" + soldier.getRank();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
