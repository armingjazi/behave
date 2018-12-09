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

    @Before
    public void setUp() throws Exception
    {
        selectFunctionFactory_ = mock(ISelectFunctionFactory.class);
        node_ = new Node(selectFunctionFactory_);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void add_tick() throws Exception
    {
        ISelectable selectableMock = mock(ISelectable.class);
        Predicate selectPredicate = mock(Predicate.class);
        ISelectFunction selectFunction_ = mock(ISelectFunction.class);
        when(selectFunction_.call()).thenReturn(true);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(selectFunction_);

        node_.add(selectableMock, selectPredicate);

        node_.tick();

        verify(selectFunction_, times(1)).call();
    }

    @Test
    public void add_selectable_returningFalse_add_selectable_returningTrue_tick() throws Exception
    {
        ISelectable selectableMock = mock(ISelectable.class);
        Predicate selectPredicate = mock(Predicate.class);

        ISelectFunction selectFunction_ = mock(ISelectFunction.class);
        when(selectFunction_.call()).thenReturn(false);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(selectFunction_);

        node_.add(selectableMock, selectPredicate);

        ISelectFunction secondSelectFunction_ = mock(ISelectFunction.class);
        when(secondSelectFunction_.call()).thenReturn(true);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(secondSelectFunction_);

        node_.add(selectableMock, selectPredicate);

        node_.tick();

        verify(selectFunction_, times(1)).call();
        verify(secondSelectFunction_, times(1)).call();
    }

    @Test
    public void add_selectable_returningTrue_add_selectable_tick() throws Exception
    {
        ISelectable selectableMock = mock(ISelectable.class);
        Predicate selectPredicate = mock(Predicate.class);

        ISelectFunction selectFunction_ = mock(ISelectFunction.class);
        when(selectFunction_.call()).thenReturn(true);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(selectFunction_);

        node_.add(selectableMock, selectPredicate);

        ISelectFunction secondSelectFunction_ = mock(ISelectFunction.class);
        when(secondSelectFunction_.call()).thenReturn(true);
        when(selectFunctionFactory_.create(any(ISelectable.class),any(Predicate.class))).thenReturn(secondSelectFunction_);

        node_.add(selectableMock, selectPredicate);

        node_.tick();

        verify(selectFunction_, times(1)).call();
        verifyZeroInteractions(secondSelectFunction_);
    }
}