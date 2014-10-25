package vektah.rust.autocomplete;

import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vektah.rust.RustIcons;
import vektah.rust.psi.*;
import vektah.rust.psi.mixin.expr.RustExprIdentifierMixin;

import java.util.*;

public class LocalVariableProvider {
    public static Iterable<LookupElement> findVariablesInScope(PsiElement psiElement) {
        return findVariablesInScope(psiElement, new HashSet<String>(), ImmutableList.<LookupElement>builder());
    }

    private static Iterable<LookupElement> findVariablesInScope(PsiElement psiElement, Set<String> completions, ImmutableList.Builder<LookupElement> currentElements) {

        for (RustLetBind letBind : findBindings(findAscendants(psiElement))) {
            for (LookupElementBuilder variable : letBind.getVariables()) {
                if (!completions.contains(variable.getLookupString())) {
                    completions.add(variable.getLookupString());
                    currentElements.add(variable.withIcon(RustIcons.ICON_VARIABLE));
                }
            }
        }

        PsiElement parent = psiElement.getParent();
        if (parent == null) {
            return currentElements.build();
        }

        return findVariablesInScope(parent, completions, currentElements);
    }

    public static PsiReference findVariableInScope(PsiElement psiElement) {
        return new PsiReferenceBase<PsiElement>(psiElement, TextRange.from(0, psiElement.getTextLength())) {
            @Nullable
            @Override
            public PsiElement resolve() {
                String text = psiElement.getText();

                for (RustLetBind letBind : findBindings(findAscendants(psiElement))) {
                    for (LookupElementBuilder variable : letBind.getVariables()) {
                        if (text.equals(variable.getLookupString())) {
                            PsiElement psiElement = variable.getPsiElement();

                            if (psiElement != null) {
                                return psiElement;
                            }
                        }
                    }
                }

                return null;
            }

            @NotNull
            @Override
            public Object[] getVariants() {
                return new Object[0];
            }
        };
    }

    private static FluentIterable<RustLetBind> findBindings(FluentIterable<PsiElement> psiElement) {
        return psiElement
                .filter(p -> p instanceof RustStatementBlock || p instanceof RustFnItem)
                .transformAndConcat(p -> {
                    if (p instanceof RustStatementBlock) {
                        return FluentIterable.from(((RustStatementBlock) p).getLetList())
                                .transform(RustLet::getLetBind);
                    } else {
                        RustFnArgs fnArgs = ((RustFnItem) p).getFnDeclaration().getFnArgs();
                        if (fnArgs != null) {
                            return FluentIterable.from(fnArgs.getLetArgs().getLetBindList());
                        }
                        return Collections.emptyList();
                    }
                });
    }

    private static FluentIterable<PsiElement> findAscendants(PsiElement psiElement) {
        return FluentIterable.from(() -> new Iterator<PsiElement>() {
            PsiElement element = psiElement;

            @Override
            public boolean hasNext() {
                return element.getParent() != null;
            }

            @Override
            public PsiElement next() {
                PsiElement next = element;

                element = element.getParent();

                return next;
            }
        });
    }

}
