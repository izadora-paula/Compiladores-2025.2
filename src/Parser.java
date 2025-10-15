public class Parser {
    private Scanner scan;
    private Token currentToken;

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }
    private void nextToken () {
        currentToken = scan.nextToken();
    }

    /*Foi feita uma grande alteração em parser para permitir que o retorno fosse uma string
    * para poder passar para o interpretador */
    public String parse() {
        return statements();
    }

    // statements passou a ser do tipo string

    String statements() {
        StringBuilder result = new StringBuilder();

        while (currentToken.type != TokenType.EOF) {
            result.append(statement());
        }

        return result.toString();
    }

    String statement() {
        StringBuilder result = new StringBuilder();

        // adiciona o token atual à string
        result.append(currentToken.toString()).append("\n");

        if (currentToken.type == TokenType.PRINT) {
            result.append(printStatement());
        } else if (currentToken.type == TokenType.LET) {
            result.append(letStatement());
        } else {
            throw new Error("syntax error");
        }

        return result.toString();
    }

    String letStatement() {
        StringBuilder result = new StringBuilder();

        match(TokenType.LET);
        var id = currentToken.lexeme;
        match(TokenType.IDENT);
        match(TokenType.EQ);

        // adiciona a expressão processada à string
        result.append(expr());

        // adiciona o comando de atribuição
        result.append("pop ").append(id).append("\n");
        match(TokenType.SEMICOLON);

        return result.toString();
    }

    String printStatement() {
        StringBuilder result = new StringBuilder();

        match(TokenType.PRINT);

        // adiciona a expressão processada
        result.append(expr());

        // adiciona o comando print
        result.append("print").append("\n");
        match(TokenType.SEMICOLON);

        return result.toString();
    }

    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        } else {
            throw new Error("syntax error");
        }
    }

    // expr agora retorna String
    String expr() {
        StringBuilder result = new StringBuilder();
        result.append(term());
        result.append(oper());
        return result.toString();
    }

    // term retorna String
    String term() {
        StringBuilder result = new StringBuilder();

        if (currentToken.type == TokenType.NUMBER) {
            result.append(number());
        } else if (currentToken.type == TokenType.IDENT) {
            result.append("push ").append(currentToken.lexeme).append("\n");
            match(TokenType.IDENT);
        } else {
            throw new Error("syntax error");
        }

        return result.toString();
    }

    String number() {
        String result = "push " + currentToken.lexeme + "\n";
        match(TokenType.NUMBER);
        return result;
    }

    String oper() {
        StringBuilder result = new StringBuilder();

        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            result.append(term());
            result.append("add\n");
            result.append(oper());
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            result.append(term());
            result.append("sub\n");
            result.append(oper());
        } else if (currentToken.type == TokenType.VEZES) {
            match(TokenType.VEZES);
            result.append(term());
            result.append("vezes\n");
            result.append(oper());
        } else if (currentToken.type == TokenType.DIV) {
            match(TokenType.DIV);
            result.append(term());
            result.append("divid\n");
            result.append(oper());
        }

        return result.toString();
    }


}