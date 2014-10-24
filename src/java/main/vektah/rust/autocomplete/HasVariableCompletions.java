package vektah.rust.autocomplete;

import com.intellij.codeInsight.lookup.LookupElementBuilder;

public interface HasVariableCompletions {
    public Iterable<LookupElementBuilder> getVariables();
}
