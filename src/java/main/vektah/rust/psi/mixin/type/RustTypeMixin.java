package vektah.rust.psi.mixin.type;

import com.google.common.collect.Iterables;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import vektah.rust.psi.RustTokens;
import vektah.rust.psi.RustType;
import vektah.rust.psi.TypeInformation;
import vektah.rust.psi.types.TypeUtil;

import java.util.List;

public abstract class RustTypeMixin extends ASTWrapperPsiElement implements RustType{
    public RustTypeMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public PsiReference getReference() {
        List<PsiElement> children = findChildrenByType(RustTokens.IDENTIFIER);
        PsiElement child = Iterables.getLast(children, null);
        return TypeUtil.getTypeReference(child == null ? getText() : child.getText(), this, getPath());
    }

    @Override
    public TypeInformation getTypeInformation() {
        return TypeUtil.resolveType(this);
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String s) throws IncorrectOperationException {
        return null;
    }

    @Override
    public String[] getPath() {
        return new String[0];
    }
}
