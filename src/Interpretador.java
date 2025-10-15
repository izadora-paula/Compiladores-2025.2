import java.util.*;
import java.util.stream.Collectors;

class Command {

    public enum Type {
        ADD,
        SUB,
        PUSH,
        POP,
        PRINT,
        VEZES,
        DIVID
    }

    public Command.Type type;
    public String arg = "";

    public Command(String[] command) {
        type = Command.Type.valueOf(command[0].toUpperCase());
        if (command.length > 1) {
            arg = command[1];
        }
    }

    public String toString() {
        return type.name() + " " + arg;
    }
}

public class Interpretador {

    List<String[]> commands;
    Stack<Integer> stack = new Stack<>();
    Map<String, Integer> variables = new HashMap<>();

    Interpretador(String input) {
        /* Código adicionado para dividir a entrada por qualquer quebra de linha
        * No código original era: final String eol = System.getProperty("line.separator");
        * O código original não conseguia dividir as linhas na entrada e entendia tudo como uma linha somente
        **/
        var output = input.split("\\R");

        /* O código de commands também foi alterado:
        * 1) foi substituido o s != "" por !s.isEmpty(), para tratar os espaços vazios de forma mais segura
        * 2) foi utilizado o filtro !s.matches("<.*?>.*?</.*?>") para evitar que comando de marcação entrem na execução. Como: <LET>let</LET>
        * 3) foi adicionado também  o comando split("\\s+") para dividir corretamente mesmo que tenha mais de um espaço entre palavras
        * */

        commands = Arrays.stream(output)
                .map(String::strip)
                .filter(s -> !s.startsWith("//") && !s.isEmpty())
                .filter(s -> !s.matches("<.*?>.*?</.*?>"))
                .map(s -> s.split("\\s+"))
                .collect(Collectors.toList());
    }

    public boolean hasMoreCommands() {
        return !commands.isEmpty();
    }

    public Command nextCommand() {
        return new Command(commands.remove(0));
    }

    public void run() {
        while (hasMoreCommands()) {
            var command = nextCommand();
            switch (command.type) {
                case ADD -> {
                    var arg2 = stack.pop();
                    var arg1 = stack.pop();
                    stack.push(arg1 + arg2);
                }
                case SUB -> {
                    var arg2 = stack.pop();
                    var arg1 = stack.pop();
                    stack.push(arg1 - arg2);
                }
                /*
                 *Foram adicionadas as operações de multiplicação e divisão, como proposto na atividade do passo 1
                 */
                case VEZES -> {
                    var arg2 = stack.pop();
                    var arg1 = stack.pop();
                    stack.push(arg1 * arg2);
                }
                case DIVID -> {
                    var arg2 = stack.pop();
                    var arg1 = stack.pop();
                    stack.push(arg1 / arg2);
                }
                case PUSH -> {
                    var value = variables.get(command.arg);
                    if (value != null) {
                        stack.push(value);
                    } else {
                        stack.push(Integer.parseInt(command.arg));
                    }
                }
                case POP -> {
                    var value = stack.pop();
                    variables.put(command.arg, value);
                }
                case PRINT -> {
                    var arg = stack.pop();
                    System.out.println(arg);
                }
            }
        }
    }
}
