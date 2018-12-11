package com.behave;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Predicate;

import static org.mockito.Mockito.*;

public class NodeTestCase
{
    private Node node_;
    private ISelectFunctionFactory selectFunctionFactory_;
    private ISelectable selectable_;
    private Predicate selectPredicate_;
    private ISelectFunction selectFunction_;
    private ISignal signal_;

    @Before
    public void setUp()
    {
        selectFunctionFactory_ = mock(ISelectFunctionFactory.class);
        selectable_ = mock(ISelectable.class);
        selectPredicate_ = mock(Predicate.class);
        selectFunction_ = mock(ISelectFunction.class);
        signal_ = mock(ISignal.class);

        node_ = new Node(selectFunctionFactory_);
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void add_tick()
    {
        when(selectFunction_.apply(signal_)).thenReturn(true);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(selectFunction_);

        node_.add(selectable_, selectPredicate_);
        node_.tick(signal_);

        verify(selectFunction_, times(1)).apply(signal_);
    }

    @Test
    public void add_selectable_returningFalse_add_selectable_returningTrue_tick() throws Exception
    {
        when(selectFunction_.apply(signal_)).thenReturn(false);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(selectFunction_);

        node_.add(selectable_, selectPredicate_);

        ISelectFunction secondSelectFunction_ = mock(ISelectFunction.class);
        when(secondSelectFunction_.apply(signal_)).thenReturn(true);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(secondSelectFunction_);

        node_.add(selectable_, selectPredicate_);

        node_.tick(signal_);

        verify(selectFunction_, times(1)).apply(signal_);
        verify(secondSelectFunction_, times(1)).apply(signal_);
    }

    @Test
    public void add_selectable_returningTrue_add_selectable_tick() throws Exception
    {
        when(selectFunction_.apply(signal_)).thenReturn(true);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(selectFunction_);

        node_.add(selectable_, selectPredicate_);

        ISelectFunction secondSelectFunction_ = mock(ISelectFunction.class);
        when(secondSelectFunction_.apply(signal_)).thenReturn(false);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(secondSelectFunction_);

        node_.add(selectable_, selectPredicate_);

        node_.tick(signal_);

        verify(selectFunction_, times(1)).apply(any(ISignal.class));
        verifyZeroInteractions(secondSelectFunction_);
    }
}