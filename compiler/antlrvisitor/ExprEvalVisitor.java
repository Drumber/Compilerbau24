package compiler.antlrvisitor;

import compiler.antlrcompiler.languageParser;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ExprEvalVisitor extends compiler.antlrcompiler.languageBaseVisitor<Integer> {

	  // questionMarkExpr: andOrExpr;

    // andOrExpr: cmpExpr;

    // cmpExpr: shiftExpr;

    // shiftExpr: bitAndOrExpr;

    // bitAndOrExpr: sumExpr;

    // sumExpr: mulDivExpr (SUMOP  mulDivExpr)*;
    public Integer visitSumExpr(compiler.antlrcompiler.languageParser.SumExprContext ctx) {
        int cnt = ctx.getChildCount();
        int curChildIdx = 0;
        int curNumberIdx = 0;
        int curOpIdx = 0;
        int curResult = visitMulDivExpr(ctx.mulDivExpr(0));
        curChildIdx++;
        curNumberIdx++;
        while (curChildIdx < cnt) {
          TerminalNode nextOp = ctx.SUMOP(curOpIdx);
          curOpIdx++;
          curChildIdx++;
          int nextNumber = visitMulDivExpr(ctx.mulDivExpr(curNumberIdx));
          if (nextOp.getText().equals("+")) {
            curResult += nextNumber;
          } else {
            curResult -= nextNumber;
          }
          curNumberIdx++;
          curChildIdx++;
        }
        return curResult;
    }

    // mulDivExpr: unaryExpr;

    // unaryExpr: dashExpr;

    // dashExpr: arrowExpr;
    public Integer visitDashExpr(languageParser.DashExprContext ctx) {
        int cnt = ctx.getChildCount();
        int curChildIdx = 0;
        int curNumberIdx = 0;
        double curResult = visitArrowExpr(ctx.arrowExpr(0));
        curChildIdx++;
        curNumberIdx++;
        while (curChildIdx < cnt) {
            curChildIdx++;
            int nextNumber = visitArrowExpr(ctx.arrowExpr(curNumberIdx));
            curResult = Math.pow(curResult, nextNumber);
            curNumberIdx++;
            curChildIdx++;
        }
        return (int) curResult;
    }

    // arrowExpr: parantheseExpr;

    // parantheseExpr: NUMBER;
    public Integer visitNumber(compiler.antlrcompiler.languageParser.NumberContext ctx) {
      return Integer.valueOf(ctx.NUMBER().getText());
  }
}
