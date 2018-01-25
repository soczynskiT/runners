import java.util.Random;

class Runner {
    private String name;
    private int strength;
    private int lostStrengthPerHour;
    private int distance;
    private int maxDailyRunningHours;
    private int averageSpeed;

    public Runner(String name, int strength, int lostStrengthPerHour, int distance, int maxDailyRunningHours, int averageSpeed) {
        this.name = name;
        this.strength = strength;
        this.lostStrengthPerHour = lostStrengthPerHour;
        this.distance = distance;
        this.maxDailyRunningHours = maxDailyRunningHours;
        this.averageSpeed = averageSpeed;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getLostStrengthPerHour() {
        return lostStrengthPerHour;
    }

    public int getDistance() {
        return distance;
    }

    public int getMaxDailyRunningHours() {
        return maxDailyRunningHours;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}

class RunProcessor {
    private Runner runner;

    public RunProcessor(Runner runner) {
        this.runner = runner;
    }

    public void updateStrength(int runHours) {
        if (this.runner.getStrength() - runHours * this.runner.getLostStrengthPerHour() > 0) {
            this.runner.setStrength(this.runner.getStrength() - runHours * this.runner.getLostStrengthPerHour());
        } else {
            this.runner.setStrength(0);
        }
    }

    public void updateDistance(int runHours) {
        if (this.runner.getStrength() != 0) {

            if (this.runner.getStrength() - runHours * this.runner.getLostStrengthPerHour() > 0) {
                this.runner.setDistance(runHours * this.runner.getAverageSpeed() + this.runner.getDistance());
            } else {
                this.runner.setDistance(this.runner.getDistance() + (this.runner.getStrength() / this.runner.getLostStrengthPerHour()) * this.runner.getAverageSpeed());
            }
        }
    }
}

class TheRunApp {
    public static void main(String[] args) {

        final Random random = new Random();

        final Runner coyote = new Runner("Wild. E. Coyote", 100, 1, 0, 12, 75);
        final RunProcessor coyoteRun = new RunProcessor(coyote);

        final Runner ostrich = new Runner("Road Runner", 100, 2, 1000, 6, 120);
        final RunProcessor ostrichRun = new RunProcessor(ostrich);

        int day = 0;
        while (coyote.getStrength() > 0 || ostrich.getStrength() > 0) {
            while (coyote.getDistance() < ostrich.getDistance()) {
                final int coyoteHoursRun = random.nextInt(coyote.getMaxDailyRunningHours());
                final int ostrichHoursRun = random.nextInt(ostrich.getMaxDailyRunningHours());
                day++;
                System.out.println("Day " + day + ":");

                if (coyote.getStrength() > 0) {
                    coyoteRun.updateDistance(coyoteHoursRun);
                    coyoteRun.updateStrength(coyoteHoursRun);
                    System.out.println(coyote.getName() + " run for " + coyoteHoursRun + "h making actual total = " + coyote.getDistance() + "km.");
                } else {
                    System.out.println(coyote.getName() + " is totally exhausted - no more running, distance: " + coyote.getDistance());
                }

                if (ostrich.getStrength() > 0) {
                    ostrichRun.updateDistance(ostrichHoursRun);
                    ostrichRun.updateStrength(ostrichHoursRun);
                    System.out.println(ostrich.getName() + " try run for " + ostrichHoursRun + "h with total distance = " + ostrich.getDistance() + "km.");
                } else {
                    System.out.println(ostrich.getName() + " is totally exhausted - no more running, distance: " + ostrich.getDistance());
                }
            }
            System.out.println("\nAt day " + day + " " + coyote.getName() + " ate " + ostrich.getName() + ".");
            coyote.setStrength(0);
            ostrich.setStrength(0);
        }
    }
}
