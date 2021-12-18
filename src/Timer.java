public class Timer {
    public long start(){
        long startTime = System.currentTimeMillis()/1000;
        return startTime;
    }
    public long stop(){
        long stopTime = System.currentTimeMillis()/1000;
        return stopTime;
    }
}
