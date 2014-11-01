package vektah.rust.psi;

import com.google.common.base.Optional;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;

public interface TypeInformation {
    public String getRepresentation();
    public PsiElement getElement();
    public Iterable<TypeInstance> getMembers();
    Optional<TypeInstance> getMemberWithName(String name);
    public boolean isAssignable(TypeInformation typeInformation);
    public boolean isGeneric();
}
