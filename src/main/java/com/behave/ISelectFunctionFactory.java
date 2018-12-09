package com.behave;

import java.util.function.Predicate;

public interface ISelectFunctionFactory
{
    ISelectFunction create(ISelectable selectable, Predicate selectPredicate);
}
