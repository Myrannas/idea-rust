package vektah.rust.psi.mixin.bind;

import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import vektah.rust.RustIcons;
import vektah.rust.psi.RustLetSelfArg;
import vektah.rust.psi.impl.RustLetBindImpl;

public abstract class RustLetSelfBindMixin extends RustLetBindImpl implements RustLetSelfArg, PsiNamedElement {
    public RustLetSelfBindMixin(ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String s) throws IncorrectOperationException {
        return null;
    }

    @Override
    public String getName() {
        return "self";
    }

    @Override
    public Iterable<LookupElementBuilder> getVariables() {
        return ImmutableList.of(LookupElementBuilder.create(this).withIcon(RustIcons.ICON_VARIABLE));
    }
}
