package com.behave;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A node that can be used to attach further nodes to
 */
class Node implements ISelectable
{
    private final ISelectFunctionFactory selectFunctionFactory_;
    private ArrayList<ISelectFunction> selectFunctionList_;

    Node(ISelectFunctionFactory selectFunctionFactory)
    {
        selectFunctionFactory_ = selectFunctionFactory;
        selectFunctionList_ = new ArrayList<>();
    }

    void add(ISelectable selectable, Predicate selectPredicate)
    {
        selectFunctionList_.add(selectFunctionFactory_.create(selectable, selectPredicate));
    }

    void tick() throws Exception
    {
        for (ISelectFunction selectFunction: selectFunctionList_)
            if (selectFunction.call())
                return;
    }

    @Override
    public void ticked() throws Exception
    {
        tick();
    }
}
