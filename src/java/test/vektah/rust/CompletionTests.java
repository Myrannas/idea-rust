package vektah.rust;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.List;

public class CompletionTests extends LightCodeInsightFixtureTestCase{

    @Override
    protected String getTestDataPath() {
        return "./src/rust/completion";
    }

    public void testBasicCompletion() {
        myFixture.configureByFile("basic.rs");
        myFixture.complete(CompletionType.BASIC);

        List<String> lookupElements = myFixture.getLookupElementStrings();
        Assert.assertThat(lookupElements, Matchers.contains("a","b","c","d","e","f","g"));
        assertEquals(lookupElements.size(), 7);
    }

    public void testScopeCompletion() {
        myFixture.configureByFile("scopes.rs");
        myFixture.complete(CompletionType.BASIC);

        List<String> lookupElements = myFixture.getLookupElementStrings();
        Assert.assertThat(lookupElements, Matchers.contains("a","b","c","d","e","f","g"));
        assertEquals(lookupElements.size(), 7);
    }

    public void testScopeHiding() {
        myFixture.configureByFile("hiding.rs");
        myFixture.complete(CompletionType.BASIC);

        List<String> lookupElements = myFixture.getLookupElementStrings();
        Assert.assertThat(lookupElements, Matchers.contains("a"));
        assertEquals(lookupElements.size(), 1);
    }

    public void testScopeParameters() {
        myFixture.configureByFile("parameter.rs");
        myFixture.complete(CompletionType.BASIC);

        List<String> lookupElements = myFixture.getLookupElementStrings();
        Assert.assertThat(lookupElements, Matchers.contains("a","self"));
        assertEquals(lookupElements.size(), 2);
    }
}
