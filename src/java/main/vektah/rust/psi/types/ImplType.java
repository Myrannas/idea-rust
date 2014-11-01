package vektah.rust.psi.types;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.intellij.psi.PsiElement;
import vektah.rust.psi.*;

import java.util.List;

public class ImplType implements TypeInformation{
    private RustImplItem rustImplItem;

    public ImplType(RustImplItem rustImplItem) {
        this.rustImplItem = rustImplItem;
    }


    @Override
    public String getRepresentation() {
        if (rustImplItem.getType() != null) {
            return rustImplItem.getType().getText();
        }

        return null;
    }

    @Override
    public PsiElement getElement() {
        return rustImplItem;
    }

    @Override
    public Iterable<TypeInstance> getMembers() {
        RustImplBlock implBlock = rustImplItem.getImplBlock();

        if (implBlock != null) {
            return Iterables.transform(implBlock.getImplBody().getFnItemList(), new Function<RustFnItem, TypeInstance>() {
                @Override
                public TypeInstance apply(RustFnItem rustFnItem) {
                    return new ImplFn(rustFnItem);
                }
            });
        }

        return ImmutableList.of();
    }

    @Override
    public Optional<TypeInstance> getMemberWithName(String name) {
        return Iterables.tryFind(getMembers(), TypeUtil.withName(name));
    }

    @Override
    public boolean isAssignable(TypeInformation typeInformation) {
        return false;
    }

    @Override
    public boolean isGeneric() {
        return false;
    }
}
