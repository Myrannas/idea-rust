package vektah.rust.autocomplete;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import vektah.rust.psi.RustExprField;
import vektah.rust.psi.TypeInformation;
import vektah.rust.psi.TypeInstance;
import vektah.rust.psi.types.ExpressionUtil;
import vektah.rust.psi.types.TypeUtil;

public class TypeMemberCompletionContributor extends CompletionContributor {
    @Override
    public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet result) {
        PsiElement position = parameters.getOriginalPosition();
        if (position != null && (position.getPrevSibling() instanceof RustExprField)) {
            TypeInformation typeInformation = ExpressionUtil.resolveTypeForExpression(((RustExprField) position.getPrevSibling()).getLeft());
            Iterable<TypeInformation> impl = TypeUtil.getImplForType(typeInformation);
            Iterable<TypeInstance> members = typeInformation.getMembers();

            for (TypeInstance member : members) {
                result.addElement(member.getLookupElement());
            }

            for (TypeInformation implementation : impl) {
                for (TypeInstance instance : implementation.getMembers()) {
                    result.addElement(instance.getLookupElement());
                }
            }
        }
    }
}
