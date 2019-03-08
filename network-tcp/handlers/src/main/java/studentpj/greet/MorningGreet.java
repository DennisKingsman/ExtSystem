package studentpj.greet;

import studentpj.Greetable;

public class MorningGreet extends Greetable
{

    @Override
    public String buildResponse(String userName) {
        return "Good morning " + userName;
    }
}
