package vektah.rust.psi.types;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.intellij.psi.PsiElement;
import vektah.rust.psi.*;

import java.util.Collections;

import static vektah.rust.psi.types.TypeUtil.withName;

public class StructType implements TypeInformation {
    private RustStructItem rustStructItem;

    public StructType(RustStructItem rustStructItem) {
        this.rustStructItem = rustStructItem;
    }

    @Override
    public String getRepresentation() {
        return rustStructItem.getItemName().getText();
    }

    @Override
    public PsiElement getElement() {
        return rustStructItem;
    }

    @Override
    public Iterable<TypeInstance> getMembers() {
        RustStructBody body = rustStructItem.getStructBody();

        if (body instanceof RustStructBodyBlock) {
            return FluentIterable.from(((RustStructBodyBlock) body).getStructPropertyList())
                .transform(toField());
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<TypeInstance> getMemberWithName(String name) {
        return Iterables.tryFind(getMembers(), withName(name));
    }

    @Override
    public boolean isAssignable(TypeInformation typeInformation) {
        return equals(typeInformation);
    }

    @Override
    public boolean isGeneric() {
        return false;
    }

    private Function<RustStructProperty, TypeInstance> toField() {
        return new Function<RustStructProperty, TypeInstance>() {
            @Override
            public TypeInstance apply(RustStructProperty structProperty) {
                return new StructField(structProperty);
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StructType) {
            if (getRepresentation().equals(((StructType) obj).getRepresentation())) {
                return true;
            }
        }

        return false;
    }
}
