package vektah.rust.psi;

import com.intellij.codeInsight.lookup.LookupElement;

public interface TypeInstance {
    public LookupElement getLookupElement();
    public String getName();
    public TypeInformation getInformation();
    public boolean isMutable();
    public boolean isPublic();
}
