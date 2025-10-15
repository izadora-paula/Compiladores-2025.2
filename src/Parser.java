public class Parser {
    private Scanner scan;
    private char currentToken;

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }
    private void nextToken () {
        currentToken = scan.nextToken();
    }

    public void parse () {
        expr();
    }

    private void match(char t) {
        if (currentToken == t) {
            nextToken();
        }else {
            throw new Error("syntax error");
        }
    }

    void expr() {
        digit();
        oper();
    }

    void digit () {
        if (Character.isDigit(currentToken)) {
            System.out.println("push " + currentToken);
            match(currentToken);
        } else {
            throw new Error("syntax error");
        }
    }

    void oper () {
        if (currentToken  == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (currentToken  == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        } else if(currentToken =='*'){
            match('*');
            digit();
            System.out.println("vezes");
            oper();
        } else if(currentToken =='/'){
            match('/');
            digit();
            System.out.println("divid");
            oper();
        }
    }

}