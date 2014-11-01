package vektah.rust.autocomplete;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import vektah.rust.psi.RustExprField;

public class LocalVariableCompletionContributor extends CompletionContributor {
    @Override
    public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet result) {
        PsiElement position = parameters.getOriginalPosition();
        boolean shouldProvideCompletions = true;

        if (position == null) {
            shouldProvideCompletions = false;
        } else if (position.getPrevSibling() instanceof RustExprField) {
            shouldProvideCompletions = false;
        }

        if (shouldProvideCompletions) {
            result.addAllElements(LocalVariableProvider.findVariablesInScope(position.getParent()));
        }
    }
}
