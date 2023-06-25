import java.util.*;

class Question {
    int id;
    String questionText;
    List<String> options;
    int correctOption;

    Question(int id, String questionText, List<String> options, int correctOption) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }
}

class User {
    String name;
    int score;

    User(String name, int score) {
        this.name = name;
        this.score = score;
    }
}

public class QuizApp {
    List<Question> questions = new ArrayList<>();
    List<User> users = new ArrayList<>();

    void inputQuestions() {
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < 5; i++) {
            System.out.println("Enter question ID, text, options (comma-separated), and correct option index:");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline
            String questionText = sc.nextLine();
            String optionsStr = sc.nextLine();
            List<String> options = Arrays.asList(optionsStr.split(","));
            int correctOption = sc.nextInt();
            questions.add(new Question(id, questionText, options, correctOption));
        }
    }

    List<Question> randomizeQuestions() {
        List<Question> selectedQuestions = new ArrayList<>();
        Random rand = new Random();
        while(selectedQuestions.size() < 3) {
            int index = rand.nextInt(questions.size());
            Question selected = questions.get(index);
            if(!selectedQuestions.contains(selected)) {
                selectedQuestions.add(selected);
            }
        }
        return selectedQuestions;
    }

    Question shuffleOptions(Question q) {
        List<String> options = new ArrayList<>(q.options);
        Collections.shuffle(options);
        int correctOption = options.indexOf(q.options.get(q.correctOption));
        return new Question(q.id, q.questionText, options, correctOption);
    }

    void attemptQuiz() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name:");
        String userName = sc.nextLine();
        User user = new User(userName, 0);

        List<Question> quizQuestions = randomizeQuestions();
        for (Question question : quizQuestions) {
            question = shuffleOptions(question);
            System.out.println("Question: " + question.questionText);
            System.out.println("Options: ");
            for (int i = 0; i < question.options.size(); i++) {
                System.out.println((i + 1) + ". " + question.options.get(i));
            }
            int userOption = -1;
            while (userOption < 0 || userOption > 3) {
                System.out.println("Enter your option (1-4):");
                userOption = sc.nextInt() - 1;
                if (userOption < 0 || userOption > 3) {
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
                }
            }
            if (userOption == question.correctOption) {
                user.score++;
            }
        }

        users.add(user);
        System.out.println("Quiz completed. Your score: " + user.score);
    }

    void sortUsers() {
        users.sort((User u1, User u2) -> u2.score - u1.score);
        System.out.println("Scores (High to Low):");
        for (User user : users) {
            System.out.println(user.name + ": " + user.score);
        }
    }

    Question searchQuestion(int id) {
        for (Question question : questions) {
            if (question.id == id) {
                return question;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        QuizApp app = new QuizApp();
        app.inputQuestions();
        while(true) {
            System.out.println("Enter 1 to attempt quiz, 2 to view scores, 3 to search a question, 4 to exit.");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    app.attemptQuiz();
                    break;
                case 2:
                    app.sortUsers();
                    break;
                case 3:
                    System.out.println("Enter question ID to search:");
                    int id = sc.nextInt();
                    Question q = app.searchQuestion(id);
                    if(q != null) {
                        System.out.println("Found question: " + q.questionText);
                    } else {
                        System.out.println("Question not found");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}



