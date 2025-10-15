public class Main {
    public static void main(String[] args) {

        String input = """
            let a = 42 + 2;
            let b = 15 + 3;
            print a + b;
                """;

        Parser p = new Parser (input.getBytes());
        String output = p.parse();
        System.out.println("================= Resultado processamento parser =================");
        System.out.println(output);
        System.out.println("=========================================");
        Interpretador i = new Interpretador(output);

        i.run();

    }

}