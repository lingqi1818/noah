package com.fangcloud.noah.ruleengine.expression;


import org.apache.commons.lang3.StringUtils;
import org.wltea.expression.datameta.Variable;

import com.fangcloud.noah.ruleengine.bean.AttributeExtractor;
import com.fangcloud.noah.ruleengine.bean.DefaultAttributeExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IKExpressionEvaluator implements ExpressionEvaluator {

    private AttributeExtractor attrExtractor = new DefaultAttributeExtractor();

    @Override
    public Object evaluate(String expression, Map<String, Object> paramValues) {
        if (StringUtils.isEmpty(expression)) {
            throw new IllegalArgumentException("expression is null .");
        }
        List<Variable> variables = new ArrayList<Variable>();
        Expr expr = formatExpression(expression);
        if (expr != null) {
            List<Var> args = expr.getArgs();
            if (args != null) {
                for (Var var : args) {
                    Variable v = Variable.createVariable(var.getVar(), attrExtractor
                            .extractAttribute(paramValues.get(var.root), var.root, var.var));
                    variables.add(v);
                }
            }
        }
        return org.wltea.expression.ExpressionEvaluator.evaluate(expr.getStr(), variables);
    }

    private static Expr formatExpression(String expression) {
        Expr result = new Expr();
        StringBuilder exp = new StringBuilder();
        Var tmp = new Var();
        StringBuilder tmpSb = new StringBuilder();
        boolean isVar = false;
        int root = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            switch (c) {
                case '$':
                    if (expression.charAt(i + 1) == '{') {
                        i++;
                        isVar = true;
                        root = 0;
                    } else {
                        exp.append(c);
                    }
                    break;
                case '}':
                    String v = tmpSb.toString();
                    tmp.setVar(v);
                    if (root == 0) {
                        tmp.setRoot(v);
                    }
                    result.getArgs().add(tmp);
                    exp.append(v);
                    tmpSb.setLength(0);
                    tmp = new Var();
                    isVar = false;
                    break;
                default:
                    if (isVar) {
                        if (c == '.') {
                            if (root++ == 0) {
                                tmp.setRoot(tmpSb.toString());
                            }
                            c = '_';
                        }
                        tmpSb.append(c);
                    } else {
                        exp.append(c);
                    }
                    break;
            }
        }

        result.setStr(exp.toString());
        return result;

    }

    private static class Expr {
        private String str;
        private List<Var> args = new ArrayList<Var>();

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public List<Var> getArgs() {
            return args;
        }

    }

    private static class Var {
        private String var;
        private String root;

        public String getVar() {
            return var;
        }

        public void setVar(String var) {
            this.var = var;
        }

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }
    }

    public static void main(String args[]) {
        System.out.println(formatExpression("${a}==1 && ${b.c}==3").getArgs().get(0).getRoot());
        System.out.println(formatExpression("${a}==1 && ${b.c}==3").getArgs().get(0).getVar());
        System.out.println(formatExpression("${a}==1 && ${b.c}==3").getArgs().get(1).getVar());
        System.out.println(formatExpression("${a}==1 && ${b.c}==3").getArgs().get(1).getRoot());
        System.out.println(formatExpression("${a}==1 && ${b.c}==3 && ${test.xx}==4").getStr());
        System.out.println(formatExpression("${a}==1 && ${b.c}==3 &&${test.xx}==4").getArgs().get(2)
                .getRoot());
        System.out.println(
                formatExpression("${a}==1 && ${b.c}==3 &&${test.xx}==4").getArgs().get(2).getVar());

    }
}
