package exceptions;

/**
 * Created by andrey on 04.11.2017.
 */
public class ScenarioLogicalException extends RuntimeException {

    public ScenarioLogicalException () {
        super();
    }

    public ScenarioLogicalException (String msg) {
        super(msg);
    }

}
