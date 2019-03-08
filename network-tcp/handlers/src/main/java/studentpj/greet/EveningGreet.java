package studentpj.greet;

import studentpj.Greetable;

public class EveningGreet extends Greetable {
    @Override
    public String buildResponse(String userName) {
        return "Good evening " + userName;
    }
}
