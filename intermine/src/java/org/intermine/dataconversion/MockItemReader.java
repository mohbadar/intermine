package org.intermine.dataconversion;

/*
 * Copyright (C) 2002-2004 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import org.intermine.model.fulldata.Item;
import org.intermine.objectstore.ObjectStoreException;

/**
 * An ItemReader backed by a Map of Items identified by id
 * @author Mark Woodbridge
 */
public class MockItemReader implements ItemReader
{
    Map storedItems;

    /**
     * Constructor
     * @param map Map from which items are read
     */
    public MockItemReader(Map map) {
        storedItems = map;
    }

    /**
     * @see ItemReader#getItemById
     */
    public Item getItemById(String objectId) throws ObjectStoreException {
        return (Item) storedItems.get(objectId);
    }

    /**
     * @see ItemReader#itemIterator
     */
    public Iterator itemIterator() throws ObjectStoreException {
        return storedItems.values().iterator();
    }

    /**
     * @see ItemReader#getItemsByDescription
     */
    public List getItemsByDescription(Set constraints) throws ObjectStoreException {
        List items = new ArrayList();

        Iterator i = itemIterator();
        while (i.hasNext()) {
            Item item = (Item) i.next();

            boolean matches = true;
            Iterator descIter = constraints.iterator();
            while (descIter.hasNext()) {
                FieldNameAndValue f = (FieldNameAndValue) descIter.next();
                if (!f.matches(item)) {
                    matches = false;
                }
            }
            if (matches) {
                items.add(item);
            }
        }
        return items;
    }
}
