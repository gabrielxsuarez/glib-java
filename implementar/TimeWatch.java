package ar.gabrielsuarez.glib.data;

import java.util.concurrent.TimeUnit;

public class TimeWatch {

	private Long nanoTime;

	public TimeWatch() {
		this.nanoTime = System.nanoTime();
	}

	public Long elapsedNanoseconds() {
		return System.nanoTime() - nanoTime;
	}

	public Long elapsedMilliseconds() {
		return TimeUnit.MILLISECONDS.convert(elapsedNanoseconds(), TimeUnit.NANOSECONDS);
	}

	public void printElapsedNanoseconds() {
		System.out.println(elapsedNanoseconds() + " ns");
	}

	public void printElapsedMilliseconds() {
		System.out.println(elapsedMilliseconds() + " ms");
	}
}
