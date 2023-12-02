import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class QuizGame {

    private static final int TIME_LIMIT = 15;

    public static void main(String[] args) {
        String filePath = "quiz_questions.txt";
        startQuiz(filePath);
    }

    private static void startQuiz(String filePath) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] q = line.split("\\|");

                String question = q[0];
                String[] options = {q[1], q[2], q[3], q[4]};
                String correctAnswer = q[5];

                Timer timer = new Timer(TIME_LIMIT);
                timer.start();

                System.out.println(question);
                for (String option : options) {
                    System.out.println(option);
                }

                System.out.print("Your answer: ");
                String userAnswer = scanner.nextLine().toUpperCase();

                timer.interrupt(); // Stop the timer when the user answers

                System.out.println("Your answer: " + userAnswer);

                if (userAnswer.equals(correctAnswer)) {
                    System.out.println("Correct!\n");
                    score++;
                } else {
                    System.out.println("Incorrect. The correct answer is " + correctAnswer + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Quiz completed! Your final score is: " + score);
        scanner.close();
    }

    private static class Timer extends Thread {
        private final int timeLimit;

        Timer(int timeLimit) {
            this.timeLimit = timeLimit;
        }
        public void run() {
            try {
                for (int i = timeLimit; i > 0; i--) {
                    System.out.print("\rTime remaining: " + i + " seconds");
                    Thread.sleep(1000);
                }
                System.out.println("\rTime's up!                "); 
            } catch (InterruptedException e) {
                // Thread interrupted, timer stopped by user
            }
        }
    }
}
