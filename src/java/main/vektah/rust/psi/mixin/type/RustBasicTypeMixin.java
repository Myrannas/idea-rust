package vektah.rust.psi.mixin.type;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import vektah.rust.psi.RustTokens;
import vektah.rust.psi.RustTypeBasic;
import vektah.rust.psi.impl.RustTypeImpl;

import java.util.List;

public abstract class RustBasicTypeMixin extends RustTypeImpl implements RustTypeBasic{
    public RustBasicTypeMixin(ASTNode node) {
        super(node);
    }

    @Override
    public String[] getPath() {
        List<PsiElement> children = findChildrenByType(RustTokens.IDENTIFIER);

        if (children.size() == 0) return new String[0];

        String[] pathElements = new String[children.size()-1];

        for (int i = 0; i < children.size()-1; i++) {
            pathElements[i] = children.get(i).getText();
        }

        return pathElements;
    }
}
