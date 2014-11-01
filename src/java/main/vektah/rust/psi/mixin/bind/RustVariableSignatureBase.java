package vektah.rust.psi.mixin.bind;

import com.intellij.psi.PsiElement;

public interface RustVariableSignatureBase extends PsiElement {
    public boolean isMutable();
    public boolean isReference();
}
