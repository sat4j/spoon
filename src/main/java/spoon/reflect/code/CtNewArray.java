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

package spoon.reflect.code;

import java.util.List;

/**
 * This code element defines the creation of a new array.
 * 
 * @param <T>
 *            type of this array (should be a array...)
 */
public interface CtNewArray<T> extends CtExpression<T> {

	/**
	 * Returns true if this new array expression is a simplified initializer
	 * (without the new part).
	 */
	boolean isInitializer();

	/**
	 * Sets this new array expression to be an initializer or not.
	 */
	void setInitializer(boolean initializer);

	/**
	 * Gets the expressions that define the array's dimensions.
	 */
	List<CtExpression<Integer>> getDimensionExpressions();

	/**
	 * Sets the expressions that define the array's dimensions.
	 */
	void setDimensionExpressions(List<CtExpression<Integer>> dimensions);

	/**
	 * Adds a dimension expression.
	 */
	boolean addDimensionExpression(CtExpression<Integer> dimension);

	/**
	 * Removes a dimension expression.
	 */
	boolean removeDimensionExpression(CtExpression<Integer> dimension);

	/**
	 * Gets the initialization expressions.
	 */
	List<CtExpression<?>> getElements();

	/**
	 * Sets the initialization expressions.
	 */
	void setElements(List<CtExpression<?>> expression);

	/**
	 * Adds an element.
	 */
	boolean addElement(CtExpression<?> expression);

	/**
	 * Removes an element.
	 */
	boolean removeElement(CtExpression<?> expression);

}
