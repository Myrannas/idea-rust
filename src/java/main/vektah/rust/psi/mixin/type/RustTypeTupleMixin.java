package vektah.rust.psi.mixin.type;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.intellij.lang.ASTNode;
import vektah.rust.psi.RustType;
import vektah.rust.psi.RustTypeTuple;
import vektah.rust.psi.TypeInformation;
import vektah.rust.psi.impl.RustTypeImpl;
import vektah.rust.psi.types.TupleType;

import java.util.List;

public abstract class RustTypeTupleMixin extends RustTypeImpl implements RustTypeTuple{
    public RustTypeTupleMixin(ASTNode node) {
        super(node);
    }

    @Override
    public TypeInformation getTypeInformation() {
        List<TypeInformation> types = Lists.transform(getTypeList(), new Function<RustType, TypeInformation>() {
            @Override
            public TypeInformation apply(RustType rustType) {
                return rustType.getTypeInformation();
            }
        });


        return new TupleType(types, this);
    }
}
