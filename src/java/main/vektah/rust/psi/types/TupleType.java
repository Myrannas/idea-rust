package vektah.rust.psi.types;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import vektah.rust.RustIcons;
import vektah.rust.psi.TypeInformation;
import vektah.rust.psi.TypeInstance;

import java.util.List;

public class TupleType implements TypeInformation {

    private List<TypeInstance> types;
    private PsiElement psiElement;

    public TupleType(List<TypeInformation> types, PsiElement psiElement) {
        this.psiElement = psiElement;
        ImmutableList.Builder<TypeInstance> builder = ImmutableList.builder();
        for (int i = 0; i < types.size(); i++) {
            builder.add(new TypeField(types.get(i), i));
        }
        this.types = builder.build();
    }

    @Override
    public String getRepresentation() {
        StringBuilder stringBuilder = new StringBuilder("(");

        for (TypeInstance type : types) {
            stringBuilder.append(type.getInformation().getRepresentation());
        }

        return stringBuilder.append(")").toString();
    }

    @Override
    public PsiElement getElement() {
        return psiElement;
    }

    @Override
    public Iterable<TypeInstance> getMembers() {
        return types;
    }

    @Override
    public Optional<TypeInstance> getMemberWithName(String name) {
        return Iterables.tryFind(types, TypeUtil.withName(name));
    }

    @Override
    public boolean isAssignable(TypeInformation typeInformation) {
        return false;
    }

    @Override
    public boolean isGeneric() {
        return false;
    }

    private class TypeField implements TypeInstance {

        private final TypeInformation typeInformation;
        private final int position;

        private TypeField(TypeInformation typeInformation, int position) {
            this.typeInformation = typeInformation;
            this.position = position;
        }

        @Override
        public LookupElement getLookupElement() {
            return LookupElementBuilder.create(getName())
                    .withTailText("()")
                    .withIcon(RustIcons.ICON_METHOD)
                    .withTypeText(getInformation().getRepresentation());
        }

        @Override
        public String getName() {
            return "val" + position;
        }

        @Override
        public TypeInformation getInformation() {
            return typeInformation;
        }

        @Override
        public boolean isMutable() {
            return false;
        }

        @Override
        public boolean isPublic() {
            return true;
        }

        @Override
        public boolean isStatic() {
            return false;
        }
    }
}
