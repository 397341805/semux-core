/**
 * Copyright (c) 2017-2018 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.semux.core.Amount.ZERO;
import static org.semux.core.Amount.neg;
import static org.semux.core.Amount.sub;
import static org.semux.core.Amount.sum;
import static org.semux.core.Unit.KILO_SEM;
import static org.semux.core.Unit.MEGA_SEM;
import static org.semux.core.Unit.MICRO_SEM;
import static org.semux.core.Unit.MILLI_SEM;
import static org.semux.core.Unit.NANO_SEM;
import static org.semux.core.Unit.SEM;

import java.math.BigDecimal;

import org.junit.Test;

public class AmountTest {

    @Test
    public void testUnits() {
        assertEquals(ZERO, ZERO);
        assertEquals(Amount.of(1, NANO_SEM), Amount.of(1, NANO_SEM));
        assertEquals(Amount.of(1000, NANO_SEM), Amount.of(1, MICRO_SEM));
        assertEquals(Amount.of(1000, MICRO_SEM), Amount.of(1, MILLI_SEM));
        assertEquals(Amount.of(1000, MILLI_SEM), Amount.of(1, SEM));
        assertEquals(Amount.of(1000, SEM), Amount.of(1, KILO_SEM));
        assertEquals(Amount.of(1000, KILO_SEM), Amount.of(1, MEGA_SEM));
    }

    @Test
    public void testFromDecimal() {
        assertEquals(ZERO, Amount.of(BigDecimal.ZERO, SEM));
        assertEquals(Amount.of(10, SEM), Amount.of(new BigDecimal("10.000"), SEM));
        assertEquals(Amount.of(1000, SEM), Amount.of(BigDecimal.ONE, KILO_SEM));
        assertEquals(Amount.of(1, MILLI_SEM), Amount.of(new BigDecimal("0.001"), SEM));
    }

    @Test
    public void testFromSymbol() {
        assertEquals(NANO_SEM, Unit.fromSymbol("nSEM"));
        assertEquals(MICRO_SEM, Unit.fromSymbol("μSEM"));
        assertEquals(MILLI_SEM, Unit.fromSymbol("mSEM"));
        assertEquals(SEM, Unit.fromSymbol("SEM"));
        assertEquals(KILO_SEM, Unit.fromSymbol("kSEM"));
        assertEquals(MEGA_SEM, Unit.fromSymbol("MSEM"));
    }

    @Test
    public void testFromSymbolUnknown() {
        assertNull(Unit.fromSymbol("???"));
    }

    @Test
    public void testToDecimal() {
        assertEquals(new BigDecimal("0"), ZERO.toDecimal(0, SEM));
        assertEquals(new BigDecimal("0.000"), ZERO.toDecimal(3, SEM));

        Amount oneSem = Amount.of(1, SEM);
        assertEquals(new BigDecimal("1.000"), oneSem.toDecimal(3, SEM));
        assertEquals(new BigDecimal("1000.000"), oneSem.toDecimal(3, MILLI_SEM));
        assertEquals(new BigDecimal("0.001000"), oneSem.toDecimal(6, KILO_SEM));
    }

    @Test
    public void testCompareTo() {
        assertEquals(ZERO.compareTo(ZERO), 0);
        assertEquals(Amount.of(1000, NANO_SEM).compareTo(Amount.of(1, MICRO_SEM)), 0);

        assertEquals(Amount.of(10, NANO_SEM).compareTo(Amount.of(10, NANO_SEM)), 0);
        assertEquals(Amount.of(5, NANO_SEM).compareTo(Amount.of(10, NANO_SEM)), -1);
        assertEquals(Amount.of(10, NANO_SEM).compareTo(Amount.of(5, NANO_SEM)), 1);
    }

    @Test
    public void testHashCode() {
        assertEquals(ZERO.hashCode(), Amount.of(0, SEM).hashCode());
        assertEquals(Amount.of(999, SEM).hashCode(), Amount.of(999, SEM).hashCode());
        assertEquals(Amount.of(1000, SEM).hashCode(), Amount.of(1, KILO_SEM).hashCode());
        assertNotEquals(Amount.of(1, SEM).hashCode(), Amount.of(1, KILO_SEM).hashCode());
    }

    @Test
    public void testGtLtEtc() {
        assertTrue(Amount.of(19, SEM).gt0());
        assertTrue(Amount.of(-9, SEM).lt0());
        assertFalse(ZERO.gt0());
        assertFalse(ZERO.lt0());

        assertTrue(ZERO.gte0());
        assertTrue(ZERO.lte0());
        assertFalse(Amount.of(-9, SEM).gte0());
        assertFalse(Amount.of(99, SEM).lte0());

        assertTrue(Amount.of(999, SEM).gt(Amount.of(999, MILLI_SEM)));
        assertTrue(Amount.of(999, SEM).gte(Amount.of(999, MILLI_SEM)));
        assertFalse(Amount.of(999, SEM).lt(Amount.of(999, MILLI_SEM)));
        assertFalse(Amount.of(999, SEM).lte(Amount.of(999, MILLI_SEM)));
    }

    @Test
    public void testMath() {
        assertEquals(sum(Amount.of(1000, SEM), Amount.of(1, KILO_SEM)), Amount.of(2, KILO_SEM));
        assertEquals(sub(Amount.of(1000, SEM), Amount.of(1, KILO_SEM)), ZERO);
        assertEquals(neg(Amount.of(1000, SEM)), Amount.of(-1, KILO_SEM));
        assertEquals(neg(ZERO), ZERO);
    }
}
