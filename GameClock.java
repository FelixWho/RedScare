// GameClock.java

// This kind of clock does a countdown for a specified
// number of seconds.
class GameClock
{
	private long millisLeft;   // remaining milliseconds
	private long startTime;
	public boolean timerTicking;

	// constructor.  Parameter is how long the countdown goes for.
	public GameClock(long seconds)
	{
		millisLeft = seconds * 1000;
		timerTicking = false;
	}

	// Let the sands start falling out of the hourglass.
	public void start()
	{
		if (!timerTicking)
		{
			startTime = System.currentTimeMillis();
			timerTicking = true;
		}
	}

	// Pause the clock. It can be restarted at the same point.
	public void pause()
	{
		if (timerTicking)
		{
			long elapsed = System.currentTimeMillis() - startTime;
			millisLeft -= elapsed;
			timerTicking = false;
		}
	}

	public long millisRemaining()
	{
		if (timerTicking)
		{
			long elapsed = System.currentTimeMillis() - startTime;
			return millisLeft - elapsed;
		}
		else
			return millisLeft;
	}
}
