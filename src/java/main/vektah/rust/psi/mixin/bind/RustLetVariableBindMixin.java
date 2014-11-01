package vektah.rust.psi.mixin.bind;

import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import vektah.rust.RustIcons;
import vektah.rust.psi.RustLetVariableBind;
import vektah.rust.psi.RustType;
import vektah.rust.psi.TypeInformation;
import vektah.rust.psi.impl.RustLetImpl;
import vektah.rust.psi.types.UnknownType;

public abstract class RustLetVariableBindMixin extends RustLetImpl implements RustLetVariableBind {
    public RustLetVariableBindMixin(ASTNode node) {
        super(node);
    }

    @Override
    public Iterable<LookupElementBuilder> getVariables() {
        LookupElementBuilder element = LookupElementBuilder.create(getVariableSignature().getItemName()).withIcon(RustIcons.ICON_VARIABLE);

        RustType type = getType();

        if (type != null) {
            element = element.withTypeText(type.getText());
        }

        return ImmutableList.of(element);
    }

    public TypeInformation getTypeInformation() {
        RustType type = getType();
        if (type != null) {
            return type.getTypeInformation();
        } else {
            return UnknownType.INSTANCE;
        }
    }
}
