package vektah.rust.psi.types;

import com.google.common.base.Optional;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import vektah.rust.psi.*;

public class ExpressionUtil {
    public static TypeInformation resolveTypeForExpression(RustExpr expr) {
        if (expr instanceof RustExprField) {
            RustExprField fieldExpression = (RustExprField) expr;
            TypeInformation typeInformation = resolveTypeForExpression(fieldExpression.getLeft());
            if (fieldExpression.getRight() != null) {
                Optional<TypeInstance> member = typeInformation.getMemberWithName(fieldExpression.getRight().getText());

                if (member.isPresent()) {
                    return member.get().getInformation();
                }

            } else {
                return typeInformation;
            }
        } else if (expr instanceof RustExprPath) {

            PsiReference reference = expr.getReference();

            if (reference != null) {
                PsiElement typeElement = reference.resolve();

                if (typeElement instanceof RustItemName) {
                    return TypeUtil.getTypeForIdentifier((RustItemName) typeElement);
                }
            }
        } else if (expr instanceof RustExprCall) {
            RustExpr expression = ((RustExprCall) expr).getExpr();

            if (expression instanceof RustExprField) {
                RustExprField fieldExpression = (RustExprField) expression;
                TypeInformation typeInformation = resolveTypeForExpression(fieldExpression.getLeft());

                if (fieldExpression.getRight() == null) {
                    return typeInformation;
                }

                Iterable<TypeInformation> implForType = TypeUtil.getImplForType(typeInformation);

                for (TypeInformation information : implForType) {
                    Optional<TypeInstance> member = information.getMemberWithName(fieldExpression.getRight().getText());
                    if (member.isPresent()) {
                        return member.get().getInformation();
                    }
                }
            }
        }
        return UnknownType.INSTANCE;
    }
}
