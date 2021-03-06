package testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import decisions.AI;
import decisions.ActionSelector;

public class FinalTester {

	private final static double UNDER_THRESH_START = 0.35;
	private final static double UNDER_THRESH_END = 0.6;
	private final static double UNDER_THRESH_DELTA = 0.05;

	private final static double BUST_THRESH_START = 0.1;
	private final static double BUST_THRESH_END = 0.8;
	private final static double BUST_THRESH_DELTA = 0.05;

	private final static int GAMES = 50000;
	
	private final static String IP = "127.0.0.1";
	private final static int PORT = 1234;

	public static void main(String[] args) throws IOException {
		FileWriter fw = new FileWriter(new File("output.txt"));

		for (double underT = UNDER_THRESH_START; underT <= UNDER_THRESH_END; underT += UNDER_THRESH_DELTA) {
			ActionSelector.setUNDER_THRESH(underT);

			for (double bustT = BUST_THRESH_START; bustT <= BUST_THRESH_END; bustT += BUST_THRESH_DELTA) {
				ActionSelector.setBUST_THRESH(bustT);

				double winPercent = 0;
				double lossPercent = 0;
				for (int i = 0; i < GAMES; i++) {
					AI ai = new AI(IP, PORT, null);
					winPercent += ai.getWins();
					lossPercent += ai.getLosses();
				}

				int total = (int) (winPercent + lossPercent);
				winPercent /= total;
				lossPercent /= total;

				fw.write(underT + " " + bustT + " " + winPercent + " "
						+ lossPercent);
				System.out.printf("%.4f\t%.4f\t%.4f\t%.4f\n", underT, bustT, winPercent, lossPercent);
			}
		}
		fw.close();
	}
}
