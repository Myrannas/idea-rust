package vektah.rust;

import com.intellij.openapi.fileEditor.impl.LoadTextUtil;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.testFramework.ParsingTestCase;

import java.io.File;
import java.io.IOException;

/**
 * Tests that cover parts of the rust compiler source tree. Will be expanded for complete coverage over time.
 *
 * There doesn't appear to be an easy way to do parametrized tests with junit 3 and the intellij test suites all
 * seem to extend TestCase. Woe is me.
 */
public class RustSourceTest extends ParsingTestCase {
	public RustSourceTest() {
		super("", "rs", new RustParserDefinition());
	}

	@Override
	protected String getTestDataPath() {
		return System.getProperty("rust.source");
	}

    public void testPass() {}

	public void ignore_CompileTest() { doAllTests("compiletest"); }
	public void ignore_LibArena() { doAllTests("libarena"); }
	public void ignore_LibCollections() { doAllTests("libcollections"); }
	public void ignore_LibCore() { doAllTests("libcore"); }
	public void ignore_LibFlate() { doAllTests("libflate"); }
	public void ignore_LibFourcc() { doAllTests("libfourcc"); }
	public void ignore_LibGetOpts() { doAllTests("libgetopts"); }
	public void ignore_LibGlob() { doAllTests("libglob"); }
	public void ignore_LibGraphviz() { doAllTests("libgraphviz"); }
	public void ignore_LibGreen() { doAllTests("libgreen"); }
	public void ignore_LibHexFloat() { doAllTests("libhexfloat"); }
	public void ignore_LibC() { doAllTests("liblibc"); }
	public void ignore_LibLog() { doAllTests("liblog"); }
	public void ignore_LibNative() { doAllTests("libnative"); }
	public void ignore_LibNum() { doAllTests("libnum"); }
	public void ignore_LibRand() { doAllTests("librand"); }
	public void ignore_LibRegex() { doAllTests("libregex"); }
	public void ignore_LibRegexMacros() { doAllTests("libregex_macros"); }
	public void ignore_LibRustDoc() { doAllTests("librustdoc"); }
	public void ignore_LibRustUV() { doAllTests("librustuv"); }
	public void ignore_Libsemver() { doAllTests("libsemver"); }

	public void ignore_LibSerialize() { doAllTests("libserialize"); }
	public void ignore_LibSync() { doAllTests("libsync"); }
	public void ignore_LibSyntax() { doAllTests("libsyntax"); }
	public void ignore_LibTerm() { doAllTests("libterm"); }
	public void ignore_LibTest() { doAllTests("libtest"); }
	public void ignore_LibTime() { doAllTests("libtime"); }
	public void ignore_LibUrl() { doAllTests("liburl"); }
	public void ignore_LibUuid() { doAllTests("libuuid"); }
	public void ignore_LibUV() { doAllTests("libuv"); }
	public void ignore_LibWorkCache() { doAllTests("libworkcache"); }

	public void ignore_LibRustC() {
		doAllTests("librustc", new String[] {
			"middle/typeck/infer/test.rs", // Not valid rust as of 574cbe5b07042c448c198af371803f977977b74f
		});
	}

	public void ignore_LibStd() {
		doAllTests("libstd", new String[] {
			"vec.rs" // TODO: Unusual syntax 'let mut count_x @ mut count_y = 0;'
		});
	}

	protected void doAllTests(String dir) {
		doAllTests(dir, new String[] {});
	}

	protected void doAllTests(String dir, String[] ignore) {
		doAllTests(new File(getTestDataPath() + dir), ignore);
	}

	protected void doAllTests(File dir_or_filename, String[] ignore) {
		if (dir_or_filename.isFile() && !dir_or_filename.getName().endsWith(".rs")) {
			return;
		}

		for (String ignore_file: ignore) {
			if (dir_or_filename.getAbsolutePath().endsWith(ignore_file)) {
				return;
			}
		}

		if (!dir_or_filename.isDirectory()) {
			doTest(dir_or_filename);
		}

		File[] files = dir_or_filename.listFiles();
		if (files != null) {
			for (File file : files) {
				doAllTests(file, ignore);
			}
		}
	}

	public void doTest(File file) {
		try {
			String text = FileUtil.loadFile(file, CharsetToolkit.UTF8, true).trim();
			myFile = createFile(file.getName(), text);
			ensureParsed(myFile);
			assertEquals("light virtual file text mismatch", text, ((LightVirtualFile)myFile.getVirtualFile()).getContent().toString());
			assertEquals("virtual file text mismatch", text, LoadTextUtil.loadText(myFile.getVirtualFile()));
			assertEquals("doc text mismatch", text, myFile.getViewProvider().getDocument().getText());
			assertEquals("psi text mismatch", text, myFile.getText());

			String parseTree = toParseTreeText(myFile, skipSpaces(), includeRanges());
			assertFalse("rust source tree file " + file.getName() + " contains errors!", parseTree.contains("PsiErrorElement"));
			assertFalse("rust source tree file " + file.getName() + " contains dummyblocks!", parseTree.contains("DummyBlock"));

		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
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
