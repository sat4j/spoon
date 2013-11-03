package spoon.test;

import java.util.List;

import spoon.Spoon;
import spoon.compiler.SpoonCompiler;
import spoon.compiler.SpoonFile;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.declaration.CtSimpleType;

public class TestUtils {

	public static <T extends CtSimpleType<?>> T build(String packageName,
			String className) throws Exception {
		SpoonCompiler comp = Spoon.createCompiler();
		List<SpoonFile> files = SpoonResourceHelper.files("./src/test/java/"
				+ packageName.replace('.', '/') + "/" + className + ".java");
		comp.build(files);
		return comp.getFactory().Package().get(packageName).getType(className);
	}
}
