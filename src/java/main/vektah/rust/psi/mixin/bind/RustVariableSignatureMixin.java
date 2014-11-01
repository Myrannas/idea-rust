package vektah.rust.psi.mixin.bind;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import vektah.rust.psi.RustTokens;
import vektah.rust.psi.RustVariableSignature;

public abstract class RustVariableSignatureMixin extends ASTWrapperPsiElement implements RustVariableSignature, RustVariableSignatureBase{
    public RustVariableSignatureMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public boolean isReference() {
        return getReference() != null;
    }

    @Override
    public boolean isMutable() {
        return findChildByType(RustTokens.KW_MUT) != null;
    }
}
