package vektah.rust.psi.mixin.item;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vektah.rust.ide.structure.HasStructureViewChildren;
import vektah.rust.psi.RustStructBody;
import vektah.rust.psi.RustStructBodyBlock;
import vektah.rust.psi.RustStructItem;
import vektah.rust.psi.impl.RustItemImpl;

import java.util.Collections;
import java.util.List;

public abstract class RustStructItemMixin extends RustItemImpl implements RustStructItem, PsiNameIdentifierOwner, HasStructureViewChildren {
    public RustStructItemMixin(ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String s) throws IncorrectOperationException {
        return null;
    }

    @Nullable
    @Override
    public String getName() {
        PsiElement nameIdentifier = getNameIdentifier();

        if (nameIdentifier != null) {
            return nameIdentifier.getText();
        }

        return null;
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        return getItemName();
    }

    public List<? extends com.intellij.psi.PsiNamedElement> getChildrenItems() {
        RustStructBody structBody = getStructBody();
        if (structBody instanceof RustStructBodyBlock) {
            return ((RustStructBodyBlock) structBody).getStructPropertyList();
        }

        return Collections.emptyList();
    }
}