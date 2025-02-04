public class Workers {
    private String name;
    private int amount;
    private double tip;
    
    //
    public Workers(String typeofworker, int numberofworkers, double percenttip) {
        name = typeofworker;
        amount = numberofworkers;
        tip = percenttip;
    }

    public String getName() {
        return name;
    }
    
    public int getAmount() {
        return amount;
    }

    public double getTip() {
        return tip;
    }
}