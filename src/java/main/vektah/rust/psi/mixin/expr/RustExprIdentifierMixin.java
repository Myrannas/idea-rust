package vektah.rust.psi.mixin.expr;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchScope;
import org.jetbrains.annotations.NotNull;
import vektah.rust.autocomplete.LocalVariableProvider;
import vektah.rust.psi.RustExprIdentifier;
import vektah.rust.psi.impl.RustExprImpl;

public abstract class RustExprIdentifierMixin extends RustExprImpl implements RustExprIdentifier {
    public RustExprIdentifierMixin(ASTNode node) {
        super(node);
    }

    @Override
    public PsiReference getReference() {
        return LocalVariableProvider.findVariableInScope(this);
    }
}
