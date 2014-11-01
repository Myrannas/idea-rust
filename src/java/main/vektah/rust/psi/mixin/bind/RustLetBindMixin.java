package vektah.rust.psi.mixin.bind;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import vektah.rust.psi.RustLetBind;
import vektah.rust.psi.TypeInformation;
import vektah.rust.psi.types.UnknownType;

import java.util.Collections;

public class RustLetBindMixin extends ASTWrapperPsiElement implements RustLetBind {
    public RustLetBindMixin(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public Iterable<LookupElementBuilder> getVariables() {
        return Collections.emptyList();
    }

    @Override
    public TypeInformation getTypeInformation() {
        return UnknownType.INSTANCE;
    }
}
