package vektah.rust.autocomplete;

import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import vektah.rust.RustIcons;
import vektah.rust.psi.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LocalVariableProvider {
    public static List<LookupElement> findVariablesInScope(PsiElement psiElement) {
        return findVariablesInScope(psiElement, new HashSet<String>(), ImmutableList.<LookupElement>builder());
    }

    private static List<LookupElement> findVariablesInScope(PsiElement psiElement, Set<String> completions, ImmutableList.Builder<LookupElement> currentElements) {
        if (psiElement instanceof RustStatementBlock) {
            List<RustLet> letStatements = ((RustStatementBlock) psiElement).getLetList();

            for (RustLet letStatement : letStatements) {
                RustLetBind letBind = letStatement.getLetBind();

                for (LookupElementBuilder variable : letBind.getVariables()) {
                    if (! completions.contains(variable.getLookupString())) {
                        completions.add(variable.getLookupString());
                        currentElements.add(variable);
                    }
                }
            }
        }

        PsiElement parent = psiElement.getParent();
        if (parent == null) {
            return currentElements.build();
        }

        return findVariablesInScope(parent, completions, currentElements);
    }
}
