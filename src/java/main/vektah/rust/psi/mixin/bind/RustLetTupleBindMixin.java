package vektah.rust.psi.mixin.bind;

import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import vektah.rust.autocomplete.HasVariableCompletions;
import vektah.rust.psi.RustLetBind;
import vektah.rust.psi.RustLetTupleBind;
import vektah.rust.psi.RustLetVariableBind;
import vektah.rust.psi.impl.RustLetBindImpl;
import vektah.rust.psi.impl.RustLetImpl;

import java.util.List;

public abstract class RustLetTupleBindMixin extends RustLetBindImpl implements RustLetTupleBind, HasVariableCompletions{
    public RustLetTupleBindMixin(ASTNode node) {
        super(node);
    }

    @Override
    public Iterable<LookupElementBuilder> getVariables() {
        List<RustLetBind> bindings = getLetBindList();
        ImmutableList.Builder<LookupElementBuilder> builder = ImmutableList.builder();

        for (RustLetBind binding : bindings) {
            builder.addAll(binding.getVariables());
        }

        return builder.build();
    }
}
