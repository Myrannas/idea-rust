package vektah.rust.psi;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.parameterInfo.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RustParameterInformation implements ParameterInfoHandler<RustLetArgs, RustLetBind> {

    @Override
    public boolean couldShowInLookup() {
        return false;
    }

    @Nullable
    @Override
    public Object[] getParametersForLookup(LookupElement lookupElement, ParameterInfoContext parameterInfoContext) {
        return new Object[0];
    }

    @Nullable
    @Override
    public Object[] getParametersForDocumentation(RustLetBind rustLetBind, ParameterInfoContext parameterInfoContext) {
        return new Object[0];
    }

    @Nullable
    @Override
    public RustLetArgs findElementForParameterInfo(@NotNull CreateParameterInfoContext createParameterInfoContext) {
        return null;
    }

    @Override
    public void showParameterInfo(@NotNull RustLetArgs rustLetArgs, @NotNull CreateParameterInfoContext createParameterInfoContext) {

    }

    @Nullable
    @Override
    public RustLetArgs findElementForUpdatingParameterInfo(@NotNull UpdateParameterInfoContext updateParameterInfoContext) {
        return null;
    }

    @Override
    public void updateParameterInfo(@NotNull RustLetArgs rustLetArgs, @NotNull UpdateParameterInfoContext updateParameterInfoContext) {

    }

    @Nullable
    @Override
    public String getParameterCloseChars() {
        return null;
    }

    @Override
    public boolean tracksParameterIndex() {
        return false;
    }

    @Override
    public void updateUI(RustLetBind rustLetBind, @NotNull ParameterInfoUIContext parameterInfoUIContext) {

    }
}
