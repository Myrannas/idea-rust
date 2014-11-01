package vektah.rust.psi.mixin.bind;

import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import vektah.rust.RustIcons;
import vektah.rust.psi.RustItemName;
import vektah.rust.psi.RustLetStructBind;
import vektah.rust.psi.RustStructBindMember;
import vektah.rust.psi.RustVariableSignature;
import vektah.rust.psi.impl.RustLetBindImpl;
import vektah.rust.psi.impl.RustLetImpl;

import java.util.List;

public abstract class RustLetStructBindMixin extends RustLetBindImpl implements RustLetStructBind {
    public RustLetStructBindMixin(ASTNode node) {
        super(node);
    }

    @Override
    public Iterable<LookupElementBuilder> getVariables() {
        ImmutableList.Builder<LookupElementBuilder> builder = ImmutableList.builder();
        List<RustStructBindMember> structBindMembers = getStructBindMemberList();

        for (RustStructBindMember structBindMember : structBindMembers) {
            RustVariableSignature variableSignature = structBindMember.getVariableSignature();

            RustItemName itemNameElement = variableSignature.getItemName();
            String itemName = itemNameElement.getText();
            if (itemName == null || "_".equals(itemName)) {
                continue;
            }

            builder.add(LookupElementBuilder.create(itemNameElement).withIcon(RustIcons.ICON_VARIABLE));
        }

        return builder.build();
    }
}
