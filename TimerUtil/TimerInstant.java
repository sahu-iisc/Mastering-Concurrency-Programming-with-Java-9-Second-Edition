package TimerUtil;

import java.time.Duration;
import java.time.Instant;

public class TimerInstant {
    private Instant startTime = null;
    private Instant endTime   = null;
  
    public void start(){
      this.startTime = Instant.now();
    }
  
    public void end() {
      this.endTime = Instant.now();
    }
  
    public Instant getStartTime() {
      return this.startTime;
    }
  
    public Instant getEndTime() {
      return this.endTime;
    }
  
    public Duration getTotalTime() {
      return Duration.between(startTime, endTime);
    }
}
