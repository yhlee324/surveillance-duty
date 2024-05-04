public class Soldier {
        private String soldierName;
        private int nightCount;
        private int dayCount;
        private int weekCount;
        private int min;
        private int max;
        private int rank;

        public Soldier(String soldierName, int dayCount, int nightCount, int min, int max, int rank) {
                this.soldierName = soldierName;
                this.dayCount = dayCount;
                this.nightCount = nightCount;
                this.weekCount = 0;
                this.min = min;
                this.max = max;
                this.rank = rank;
        }

        //Getters for info
        public String getName() {return soldierName;}
        public int getDayCount() {return dayCount;}
        public int getNightCount() {return nightCount;}
        public int getTotalCount() {return dayCount + nightCount;}
        public int getWeekCount() {return this.weekCount;}
        public int getMin() {return this.min;}
        public int getMax() {return this.max;}
        public int getRank() {return this.rank;}

        public void setName(String soldierName) {
                this.soldierName = soldierName;
        }
        public void setNightCount(int nightCount){
                this.nightCount = nightCount;
        }
        public void setDayCount(int dayCount){
                this.dayCount = dayCount;
        }
        public void addNightCount(){
                this.nightCount +=1;
        }
        public void addDayCount(){
                this.dayCount +=1;
        }
}
