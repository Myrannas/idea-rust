package vektah.rust.psi.types;

import com.google.common.base.Optional;
import com.intellij.psi.PsiElement;
import vektah.rust.psi.TypeInformation;
import vektah.rust.psi.TypeInstance;

import java.util.Collections;

public class UnknownType implements TypeInformation {
    private String name;

    @Override
    public String getRepresentation() {
        return name;
    }

    @Override
    public PsiElement getElement() {
        return null;
    }

    @Override
    public Iterable<TypeInstance> getMembers() {
        return Collections.emptyList();
    }

    @Override
    public Optional<TypeInstance> getMemberWithName(String name) {
        return Optional.absent();
    }

    @Override
    public boolean isAssignable(TypeInformation typeInformation) {
        return true;
    }

    @Override
    public boolean isGeneric() {
        return false;
    }

    private UnknownType(String name) {
        this.name = name;
    }

    public static UnknownType INSTANCE = new UnknownType("?");

    public static UnknownType withName(String name) {
        return new UnknownType(name);
    }
}
