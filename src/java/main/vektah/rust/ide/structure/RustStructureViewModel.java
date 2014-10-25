package vektah.rust.ide.structure;

import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

class RustStructureViewModel extends StructureViewModelBase {
    public RustStructureViewModel(@NotNull PsiFile psiFile) {
        super(psiFile, new RustStructureViewNode(psiFile));
    }
}

