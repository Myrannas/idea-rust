package vektah.rust.psi;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class TreeUtil {
    public static Predicate<PsiElement> isOfType(final Class<?>... classes) {
        return new Predicate<PsiElement>() {
            @Override
            public boolean apply(PsiElement psiElement) {
                for (Class<?> aClass : classes) {
                    if (aClass.isInstance(psiElement)) return true;
                }

                return false;
            }
        };
    }

    @NotNull
    public static FluentIterable<PsiElement> findAscendants(@NotNull final PsiElement psiElement) {
        return FluentIterable.from(new Iterable<PsiElement>() {
            @Override
            public Iterator<PsiElement> iterator() {
                return new Iterator<PsiElement>() {
                    PsiElement element = psiElement;

                    @Override
                    public boolean hasNext() {
                        return element != null && ! (element instanceof VirtualFile);
                    }

                    @Override
                    public PsiElement next() {
                        PsiElement next = element;

                        element = element.getParent();

                        return next;
                    }
                };
            }
        });
    }

    public static Function<PsiElement, Iterable<PsiElement>> expandChildren() {
        return new Function<PsiElement, Iterable<PsiElement>>() {
            @Override
            public Iterable<PsiElement> apply(PsiElement psiElement) {
                return ImmutableList.copyOf(psiElement.getChildren());
            }
        };
    }

    @NotNull
    public static Predicate<PsiNamedElement> isNamed(@NotNull final String name) {
        Preconditions.checkNotNull("Name should not be null",name);

        return new Predicate<PsiNamedElement>() {
            @Override
            public boolean apply(PsiNamedElement psiNamedElement) {
                return name.equals(psiNamedElement.getName());
            }
        };
    }

}
