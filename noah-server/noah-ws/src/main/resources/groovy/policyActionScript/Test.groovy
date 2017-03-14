import com.helijia.risk.ruleengine.rule.action.Action

public class Test implements Action{

    @Override
    void execute(Map<String, Object> contextMap) {
        println "aaa";
        println contextMap;
    }

}