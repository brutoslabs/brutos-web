package org.brandao.brutos;

public interface RequestParserStats {

    double getPercentComplete();

    long estimatedMillisecondsLeft();

    long getElapsedTimeInMilliseconds();
	
}
