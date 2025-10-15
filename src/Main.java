public class Main {
    public static void main(String[] args) {
        String input = "8+5-7+9";
        Parser p = new Parser (input.getBytes());
        p.parse();

    }
}
