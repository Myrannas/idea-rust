package vektah.rust;

import com.intellij.testFramework.ParsingTestCase;
import org.junit.Ignore;

public class ParserTest extends ParsingTestCase {
	public ParserTest() {
		super("", "rs", new RustParserDefinition());
	}

    public void testPass() {}

	public void ignore_testcomments() { doTest(true); }
	public void ignore_testliterals() { doTest(true); }
	public void ignore_testsimple() { doTest(true); }
	public void ignore_testtuple() { doTest(true); }
	public void ignore_teststruct() { doTest(true); }
	public void ignore_testclosure() { doTest(true); }
	public void ignore_testenum() { doTest(true); }
	public void ignore_testbox() { doTest(true); }
	public void ignore_testmatch() { doTest(true); }
	public void ignore_testfunctions() { doTest(true); }
	public void ignore_testlinked_list() { doTest(true); }
	public void ignore_testextern() { doTest(true); }
	public void ignore_testimpl() { doTest(true); }
	public void ignore_teststatic() { doTest(true); }
	public void ignore_testmacro_call() { doTest(true); }

	@Override
	protected String getTestDataPath() {
		return "./src/rust/";
	}

	@Override
	protected boolean skipSpaces() {
		return true;
	}

	@Override
	protected boolean includeRanges() {
		return false;
	}
}
