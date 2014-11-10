package vektah.rust.autocomplete;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import vektah.rust.psi.RustExpr;
import vektah.rust.psi.RustExprField;
import vektah.rust.psi.TypeInformation;
import vektah.rust.psi.TypeInstance;
import vektah.rust.psi.types.ExpressionUtil;
import vektah.rust.psi.types.TypeUtil;

public class TypeMemberCompletionContributor extends CompletionContributor {
    @Override
    public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet result) {
        PsiElement position = parameters.getOriginalPosition();

        if (position == null) return;

        PsiElement prevSibling = position.getPrevSibling();
        if (prevSibling == null)
            prevSibling = position.getParent().getPrevSibling();

        while (prevSibling.getLastChild() != null)
            prevSibling = prevSibling.getLastChild();

        if (prevSibling instanceof PsiErrorElement)
            prevSibling = prevSibling.getPrevSibling();

            PsiElement parent = prevSibling.getParent();
        if (! ".".equals(prevSibling.getText())) return;


        if (! (parent instanceof RustExpr)) return;

        RustExpr expr = (RustExpr) parent;


//        RustExpr expression;
//        if (position == null) {
//            return;
//        } else {
//            PsiElement prevSibling = position.getPrevSibling();
//            if (prevSibling instanceof RustExprField) {
//                expression = ((RustExprField) prevSibling).getLeft();
//            } else if (prevSibling.getText().equals(".")) {
//                PsiElement prevExpression = prevSibling.getPrevSibling();
//
//                if (prevExpression instanceof RustExpr) {
//                    expression = (RustExpr) prevExpression;
//                } else {
//                    return;
//                }
//            } else if (prevSibling.getLastChild() != null) {
//                PsiElement child = prevSibling.getLastChild();
//
//                while ()
//            }
//        }

        TypeInformation typeInformation = ExpressionUtil.resolveTypeForExpression(expr);
        Iterable<TypeInformation> impl = TypeUtil.getImplForType(typeInformation);
        Iterable<TypeInstance> members = typeInformation.getMembers();

        for (TypeInstance member : members) {
            result.addElement(member.getLookupElement());
        }

        for (TypeInformation implementation : impl) {
            for (TypeInstance instance : implementation.getMembers()) {
                if (! instance.isStatic()) {
                    result.addElement(instance.getLookupElement());
                }
            }
        }

    }
}
