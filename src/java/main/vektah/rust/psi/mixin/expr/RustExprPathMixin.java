package vektah.rust.psi.mixin.expr;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import vektah.rust.autocomplete.LocalVariableProvider;
import vektah.rust.psi.RustExpr;
import vektah.rust.psi.RustExprPath;
import vektah.rust.psi.impl.RustExprImpl;

public abstract class RustExprPathMixin extends RustExprImpl implements RustExprPath {
    public RustExprPathMixin(ASTNode node) {
        super(node);
    }

    @Override
    public PsiReference getReference() {
        return LocalVariableProvider.findVariableInScope(this);
    }
}
