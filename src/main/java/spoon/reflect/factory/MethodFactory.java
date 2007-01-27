/* 
 * Spoon - http://spoon.gforge.inria.fr/
 * Copyright (C) 2006 INRIA Futurs <renaud.pawlak@inria.fr>
 * 
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify 
 * and/or redistribute the software under the terms of the CeCILL-C license as 
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *  
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */

package spoon.reflect.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import spoon.reflect.Factory;
import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtSimpleType;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.template.Substitution;

/**
 * The {@link CtMethod} sub-factory.
 */
public class MethodFactory extends ExecutableFactory {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new method sub-factory.
	 * 
	 * @param factory
	 *            the parent factory
	 */
	public MethodFactory(Factory factory) {
		super(factory);
	}

	/**
	 * Creates a method.
	 * 
	 * @param target
	 *            the class where the method is inserted
	 * @param modifiers
	 *            the modifiers
	 * @param returnType
	 *            the method's return type
	 * @param name
	 *            the method's name
	 * @param parameters
	 *            the parameters
	 * @param thrownTypes
	 *            the thrown types
	 * @param body
	 *            the method's body
	 */
	public <T> CtMethod<T> create(CtClass<?> target,
			Set<ModifierKind> modifiers, CtTypeReference<T> returnType,
			String name, List<CtParameter<?>> parameters,
			Set<CtTypeReference<? extends Throwable>> thrownTypes,
			CtBlock<T> body) {
		CtMethod<T> method = create(target, modifiers, returnType, name,
				parameters, thrownTypes);
		method.setBody(body);
		body.setParent(method);
		return method;
	}

	/**
	 * Creates a method by copying an existing method.
	 * 
	 * @param <T>
	 *            the type of the method
	 * @param target
	 *            the target type where the new method has to be inserted to
	 * @param source
	 *            the source method to be copied
	 * @param redirectReferences
	 *            tells if all the references to the owning type of the source
	 *            method should be redirected to the target type (true is
	 *            recommended for most uses)
	 * @return the newly created method
	 */
	public <T> CtMethod<T> create(CtType<?> target, CtMethod<T> source,
			boolean redirectReferences) {
		CtMethod<T> newMethod = factory.Core().clone(source);
		if (redirectReferences && source.getDeclaringType() != null) {
			Substitution.redirectTypeReferences(newMethod, source
					.getDeclaringType().getReference(), target.getReference());
		}
		target.getMethods().add(newMethod);
		newMethod.setParent(target);
		return newMethod;
	}

	/**
	 * Creates an empty method.
	 * 
	 * @param target
	 *            the class where the method is inserted
	 * @param modifiers
	 *            the modifiers
	 * @param returnType
	 *            the method's return type
	 * @param name
	 *            the method's name
	 * @param parameters
	 *            the parameters
	 * @param thrownTypes
	 *            the thrown types
	 */
	public <T> CtMethod<T> create(CtType<?> target,
			Set<ModifierKind> modifiers, CtTypeReference<T> returnType,
			String name, List<CtParameter<?>> parameters,
			Set<CtTypeReference<? extends Throwable>> thrownTypes) {
		CtMethod<T> method = factory.Core().createMethod();
		target.getMethods().add(method);
		method.setParent(target);
		if (modifiers != null)
			method.setModifiers(modifiers);
		method.setType(returnType);
		method.setSimpleName(name);
		if (parameters != null)
			method.setParameters(parameters);
		setParent(method, parameters);
		if (thrownTypes != null)
			method.setThrownTypes(thrownTypes);
		return method;
	}

	/**
	 * Creates a method reference.
	 */
	public <T> CtExecutableReference<T> createReference(CtMethod<T> m) {
		return factory.Executable().createReference(m);
	}

	/**
	 * Creates a method reference from an actual method.
	 */
	@SuppressWarnings("unchecked")
	public <T> CtExecutableReference<T> createReference(Method method) {
		return createReference(factory.Type().createReference(
				method.getDeclaringClass()), (CtTypeReference<T>) factory
				.Type().createReference(method.getReturnType()), method
				.getName(), factory.Type().createReferences(
				(List<Class<?>>) (List) Arrays.asList(method
						.getParameterTypes())).toArray(
				new CtTypeReference<?>[0]));
	}

	/**
	 * Gets all the main methods stored in this factory.
	 */
	public Collection<CtMethod> getMainMethods() {
		Collection<CtMethod> methods = new ArrayList<CtMethod>();
		for (CtSimpleType<?> t : factory.Type().getAll()) {
			if (t instanceof CtClass) {
				CtMethod<?> m = ((CtClass<?>) t).getMethod("main", factory
						.Type().createArrayReference(
								factory.Type().createReference(String.class)));
				if (m != null && m.getModifiers().contains(ModifierKind.STATIC)) {
					methods.add(m);
				}
			}
		}
		return methods;
	}

}
