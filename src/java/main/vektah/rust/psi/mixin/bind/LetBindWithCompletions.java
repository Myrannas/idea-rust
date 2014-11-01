package vektah.rust.psi.mixin.bind;

import com.intellij.psi.PsiElement;
import vektah.rust.autocomplete.HasVariableCompletions;
import vektah.rust.psi.TypeInformation;

public interface LetBindWithCompletions extends PsiElement, HasVariableCompletions{
    public TypeInformation getTypeInformation();
}
