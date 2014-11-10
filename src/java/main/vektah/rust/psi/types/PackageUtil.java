package vektah.rust.psi.types;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import vektah.rust.psi.RustFile;

import java.util.Arrays;

public class PackageUtil {
    public static Optional<RustFile> findPackage(PsiElement context, String[] packageName) {
        Project project = context.getProject();
        ProjectRootManager root = ProjectRootManager.getInstance(project);
        VirtualFile[] sourceRoots = root.getContentSourceRoots();

        Iterable<VirtualFile> files = FluentIterable.from(ImmutableList.copyOf(sourceRoots))
                .transformAndConcat(new Function<VirtualFile, Iterable<VirtualFile>>() {
                    @Override
                    public Iterable<VirtualFile> apply(VirtualFile virtualFile) {
                        return ImmutableList.copyOf(virtualFile.getChildren());
                    }
                });

        return findPackage(project, files, packageName);
    }

    private static Optional<RustFile> findPackage(Project project, Iterable<VirtualFile> files, String[] packageName) {
        for (VirtualFile file : files) {
            String name = file.getNameWithoutExtension();
            if (name.equals(packageName[0])) {

                if (packageName.length == 1) {
                    PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
                    if (psiFile instanceof RustFile) {
                        return Optional.of((RustFile) psiFile);
                    } else {
                        return Optional.absent();
                    }
                }

                return findPackage(project, ImmutableList.copyOf(file.getChildren()), Arrays.copyOfRange(packageName,1,packageName.length));
            }

        }

        return Optional.absent();
    }
}
