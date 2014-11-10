package vektah.rust.psi.types;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import vektah.rust.RustIcons;
import vektah.rust.psi.*;

import java.util.List;

public class ImplFn implements TypeInstance {
    private RustFnItem rustFnItem;

    public ImplFn(RustFnItem rustFnItem) {
        this.rustFnItem = rustFnItem;
    }

    @Override
    public LookupElement getLookupElement() {
        final RustFnDeclaration fnDeclaration = rustFnItem.getFnDeclaration();

        return LookupElementBuilder
                .create(fnDeclaration.getItemName())
                .withTypeText(getRepresentation())
                .withIcon(RustIcons.ICON_METHOD)
                .withInsertHandler(new InsertHandler<LookupElement>() {
                    @Override
                    public void handleInsert(InsertionContext insertionContext, LookupElement lookupElement) {
                        insertionContext.getDocument().insertString(insertionContext.getTailOffset(), "()");

                        boolean noParametersExpected = true;

                        if (fnDeclaration.getFnArgs().getLetArgs().getLetBindList().size() == 0) {
                            noParametersExpected = false;
                        } else if (fnDeclaration.getFnArgs().getLetArgs().getLetBindList().size() == 1) {
                            if (fnDeclaration.getFnArgs().getLetArgs().getLetBindList().get(0) instanceof RustLetSelfArgBind) {
                                noParametersExpected = false;
                            }
                        }

                        if (noParametersExpected) {
                            insertionContext.getEditor().getCaretModel().moveToOffset(insertionContext.getTailOffset() - 1);
                        } else {
                            insertionContext.getEditor().getCaretModel().moveToOffset(insertionContext.getTailOffset());
                        }
                    }
                });
    }

    private String getRepresentation() {
        RustFnDeclaration fnDeclaration = rustFnItem.getFnDeclaration();
        StringBuilder stringBuilder = new StringBuilder("(");
        RustFnArgs args = fnDeclaration.getFnArgs();
        if (args != null) {
            List<RustLetBind> binds = args.getLetArgs().getLetBindList();
            boolean first = true;
            for (RustLetBind fnArg : binds) {

                if (! (fnArg instanceof RustLetSelfArgBind)) {
                    if (first) {
                        first = false;
                    } else {
                        stringBuilder.append(",");
                    }

                    stringBuilder.append(fnArg.getTypeInformation().getRepresentation());
                }
            }
        }

        stringBuilder.append(") -> ");


        RustType type = fnDeclaration.getType();

        if (type != null) {
            stringBuilder.append(type.getText());
        } else {
            stringBuilder.append("()");
        }

        return stringBuilder.toString();
    }

    @Override
    public String getName() {
        RustItemName itemName = rustFnItem.getFnDeclaration().getItemName();
        if (itemName != null) {
            return itemName.getText();
        } else {
            return null;
        }
    }

    @Override
    public TypeInformation getInformation() {
        RustType type = rustFnItem.getFnDeclaration().getType();
        if (type != null) {
            return type.getTypeInformation();
        } else {
            return UnknownType.INSTANCE;
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public boolean isPublic() {
        RustVisibility visibility = rustFnItem.getFnDeclaration().getVisibility();
        return visibility != null && visibility.getPublic() != null;
    }

    @Override
    public boolean isStatic() {
        List<RustLetBind> fnArgs = rustFnItem.getFnDeclaration()
                .getFnArgs()
                .getLetArgs()
                .getLetBindList();

        return fnArgs.size() == 0 || ! (fnArgs.get(0) instanceof RustLetSelfArgBind);
    }
}
