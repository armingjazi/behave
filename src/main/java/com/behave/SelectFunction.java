package com.behave;

import java.util.function.Predicate;

public class SelectFunction implements ISelectFunction
{
    private ISelectable selectable_;
    private Predicate<ISignal> selectPredicate_;

    public SelectFunction(ISelectable selectable, Predicate<ISignal> selectPredicate)
    {
        selectable_ = selectable;
        selectPredicate_ = selectPredicate;
    }

    @Override
    public Boolean apply(ISignal signal)
    {
        if (!selectPredicate_.test(signal))
            return false;

        selectable_.ticked(signal);
        return true;

    }
}
