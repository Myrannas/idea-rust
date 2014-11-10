package vektah.rust.psi.types;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import vektah.rust.RustIcons;
import vektah.rust.psi.*;

public class StructField implements TypeInstance {

    private RustStructProperty structProperty;

    public StructField(RustStructProperty structProperty) {
        this.structProperty = structProperty;
    }

    @Override
    public LookupElement getLookupElement() {
        LookupElementBuilder elementBuilder = LookupElementBuilder.create(
                structProperty.getLetVariableBind()
                        .getVariableSignature()
                        .getItemName()
        )
                .withIcon(RustIcons.ICON_PROPERTY);

        TypeInformation information = getInformation();
        if (information != UnknownType.INSTANCE) {
            elementBuilder = elementBuilder.withTypeText(information.getRepresentation());
        }

        return elementBuilder;
    }

    @Override
    public String getName() {
        return structProperty.getLetVariableBind()
                .getVariableSignature()
                .getItemName()
                .getText();
    }

    @Override
    public TypeInformation getInformation() {
        RustType type = structProperty.getLetVariableBind().getType();
        return TypeUtil.resolveType(type);
    }

    @Override
    public boolean isMutable() {
        return structProperty.getLetVariableBind()
                .getVariableSignature()
                .isMutable();
    }

    @Override
    public boolean isPublic() {
        RustVisibility visibility = structProperty.getVisibility();

        return visibility != null
                && visibility.getPublic() != null;

    }

    @Override
    public boolean isStatic() {
        return false;
    }
}
