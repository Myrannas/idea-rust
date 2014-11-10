package vektah.rust.psi.mixin.type;

import com.intellij.psi.PsiNamedElement;
import vektah.rust.psi.TypeInformation;

public interface RustTypeBase extends PsiNamedElement {
    public TypeInformation getTypeInformation();
    public String[] getPath();
}
