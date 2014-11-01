package vektah.rust.psi.types;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vektah.rust.psi.*;
import vektah.rust.psi.impl.RustImplItemImpl;

import static vektah.rust.psi.TreeUtil.findAscendants;
import static vektah.rust.psi.TreeUtil.isNamed;
import static vektah.rust.psi.TreeUtil.isOfType;

public class TypeUtil {
    public static TypeInformation resolveType(RustType type) {
        PsiReference reference = type.getReference();

        if (reference != null) {
            PsiElement element = reference.resolve();

            if (element != null) {
                return TypeFactory.getType(element);
            }
        }

        return UnknownType.withName(type.getText());
    }

    public static TypeInformation getTypeForIdentifier(RustItemName itemName) {
        PsiElement parent = itemName.getParent();
        RustType type = null;

        if (parent instanceof RustVariableSignature) {
            if (parent.getParent() instanceof RustLetVariableBind) {
                type = ((RustLetVariableBind) parent.getParent()).getType();
            }
        }

        if (type != null) {
            return type.getTypeInformation();
        }

        return UnknownType.INSTANCE;
    }

    public static Predicate<PsiElement> isTypeDeclaration() {
        return new Predicate<PsiElement>() {
            @Override
            public boolean apply(PsiElement psiElement) {
                return psiElement instanceof RustStructItem;
            }
        };
    }

    public static PsiReference getTypeReference(final String name, final PsiElement element) {
        return new PsiReferenceBase<PsiElement>(element, TextRange.from(0, element.getTextLength())) {

            @Nullable
            @Override
            public PsiElement resolve() {
                return findAscendants(element)
                        .filter(isOfType(RustFile.class, RustStatementBlock.class))
                        .transformAndConcat(findTypes())
                        .filter(isTypeDeclaration())
                        .filter(isNamed(name))
                        .first()
                        .orNull();
            }

            @NotNull
            @Override
            public Object[] getVariants() {
                return EMPTY_ARRAY;
            }
        };
    }

    public static Function<PsiElement, Iterable<? extends PsiNamedElement>> findTypes() {
        return new Function<PsiElement, Iterable<? extends PsiNamedElement>>() {
            @Override
            public Iterable<? extends PsiNamedElement> apply(PsiElement PsiElement) {
                if (PsiElement instanceof RustFile) {
                    return ((RustFile) PsiElement).getChildrenItems();
                } else {
                    return ((RustStatementBlock) PsiElement).getItemList();
                }
            }
        };
    }

    @NotNull
    public static Predicate<TypeInstance> withName(@NotNull final String name) {
        Preconditions.checkNotNull("Name must not be null", name);

        return new Predicate<TypeInstance>() {
            @Override
            public boolean apply(TypeInstance typeInformation) {
                return name.equals(typeInformation.getName());
            }
        };
    }

    public static Iterable<TypeInformation> getImplForType(final TypeInformation typeInformation) {
        if (typeInformation instanceof UnknownType) {
            return ImmutableList.of();
        }

        Function<PsiElement, Iterable<TypeInformation>> correctType = new Function<PsiElement, Iterable<TypeInformation>>() {
            @Override
            public Iterable<TypeInformation> apply(PsiElement element) {
                if (element instanceof RustImplItemImpl) {
                    TypeInformation implType = ((RustImplItemImpl)element).getTypeInformation();
                    if (implType.isAssignable(typeInformation)) {
                        return ImmutableList.of(TypeFactory.getType(element));
                    }
                }

                return ImmutableList.of();
            }
        };

        return TreeUtil.findAscendants(typeInformation.getElement())
                .transformAndConcat(TreeUtil.expandChildren())
                .transformAndConcat(correctType);
    }


}
