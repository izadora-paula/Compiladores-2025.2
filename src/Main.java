public class Main {
    public static void main(String[] args) {
        String input = "89 + 508 - 7 +   99";
        Parser p = new Parser (input.getBytes());
        p.parse();
    }
}
