package studentpj.greet;

import studentpj.Greetable;

public class DayGreet extends Greetable
{

    @Override
    public String buildResponse(String userName) {
        return "Good day " + userName;
    }
}
