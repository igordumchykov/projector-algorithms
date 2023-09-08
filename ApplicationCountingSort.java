import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class ApplicationCountingSort {
  private static final Random RAND = new Random();
  private static final StringBuilder RESULT = new StringBuilder();

  private static final int SIZE = 100_000;
  public static void main(String[] args) throws IOException {
    measure();
  }

  private static void measure() throws IOException {
    RESULT.append("k,time\n");
    for (int k = 100; k < 1_000_000_000; k += 10_000_000) {
      RESULT.append(String.format("%s,", k));
      int[] arr = generate(k);
      measure(() -> sort(arr));
      RESULT.append("\n");
    }
    PrintWriter writer = new PrintWriter(new FileWriter("docs/sort.csv"));
    writer.write(RESULT.toString());
    RESULT.setLength(0);
    writer.close();
  }

  private static void measure(Runnable function) {
    Instant now = Instant.now();
    function.run();
    RESULT.append(String.format("%s",
        Duration.between(now, Instant.now()).toNanos() / 1000))
    ;
  }

  private static int[] generate(int k) {
    int[] arr = new int[SIZE];
    for (int i = 0; i < SIZE; i++) {
      arr[i] = RAND.nextInt(k);
    }
    return arr;
  }


  private static void sort(int[] array) {
    int k = array[0];
    for (int i = 1; i < array.length; i++) {
      if (array[i] > k) {
        k = array[i];
      }
    }

    int[] tempArray = new int[k + 1];
    for (int value : array) {
      ++tempArray[value];
    }
    int b = 0;
    for (int i = 0; i < k + 1; ++i){
      for (int j = 0; j < tempArray[i]; ++j) {
        array[b++] = i;
      }
    }
  }
}
