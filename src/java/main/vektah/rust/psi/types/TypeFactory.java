package vektah.rust.psi.types;

import com.intellij.psi.PsiElement;
import vektah.rust.psi.RustImplItem;
import vektah.rust.psi.RustStructItem;
import vektah.rust.psi.TypeInformation;

public class TypeFactory {
    public static TypeInformation getType(PsiElement element) {
        if (element instanceof RustStructItem) {
            return new StructType((RustStructItem) element);
        } else if (element instanceof RustImplItem) {
            return new ImplType((RustImplItem) element);
        }

        return UnknownType.INSTANCE;
    }
}
