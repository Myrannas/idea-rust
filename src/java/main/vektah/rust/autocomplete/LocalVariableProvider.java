package vektah.rust.autocomplete;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vektah.rust.RustIcons;
import vektah.rust.psi.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static vektah.rust.psi.TreeUtil.findAscendants;
import static vektah.rust.psi.TreeUtil.isOfType;

public class LocalVariableProvider {
    public static Iterable<LookupElement> findVariablesInScope(@NotNull PsiElement psiElement) {
        Set<String> completions = new HashSet<String>();
        ImmutableList.Builder<LookupElement> currentElements = ImmutableList.builder();

        for (RustLetBind letBind : findBindings(findAscendants(psiElement))) {
            for (LookupElementBuilder variable : letBind.getVariables()) {
                if (!completions.contains(variable.getLookupString())) {
                    completions.add(variable.getLookupString());
                    currentElements.add(variable.withIcon(RustIcons.ICON_VARIABLE));
                }
            }
        }

        return currentElements.build();
    }

    public static PsiReference findVariableInScope(@NotNull final PsiElement psiElement) {
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
                .filter(isOfType(RustStatementBlock.class, RustFnItem.class))
                .transformAndConcat(new Function<PsiElement, Iterable<? extends RustLetBind>>() {
                    @Override
                    public Iterable<? extends RustLetBind> apply(PsiElement psiElement) {
                        if (psiElement instanceof RustStatementBlock) {
                            return FluentIterable.from(((RustStatementBlock) psiElement).getLetList())
                                    .transform(new Function<RustLet, RustLetBind>() {
                                        @Override
                                        public RustLetBind apply(RustLet rustLet) {
                                            return rustLet.getLetBind();
                                        }
                                    });
                        } else {
                            RustFnArgs fnArgs = ((RustFnItem) psiElement).getFnDeclaration().getFnArgs();
                            if (fnArgs != null) {
                                return FluentIterable.from(fnArgs.getLetArgs().getLetBindList());
                            }
                            return Collections.emptyList();
                        }
                    }
                });
    }



}
